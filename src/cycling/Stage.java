package cycling;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stage implements java.io.Serializable{
    private int ID;
    private String name;
    private String description;
    private ArrayList<CheckPoint> checkPoints;
    private StageType stageType;
    private int[] pointsAwarded;
    private LocalDateTime startTime;
    private String location;
    private Double length;
    private Boolean waitingForResults;
    private HashMap<Integer, LocalTime[]> times;

    public Stage(int ID, String name, String description, StageType stageType, double length, LocalDateTime startTime){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.stageType = stageType;
        switch (stageType) {
            case FLAT:
                int[] flatPoints = {50,30,20,15,16,16,14,12,10,8,7,6,5,4,3,2};
                pointsAwarded = flatPoints;
                break;
            case MEDIUM_MOUNTAIN:
                int[] mediumPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
                pointsAwarded = mediumPoints;
                break;
            case HIGH_MOUNTAIN:
                int[] highPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
                pointsAwarded = highPoints;
                break;
            case TT:
                int[] trialPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
                pointsAwarded = trialPoints;
                break;
            default:
                break;
        }
        if (stageType != StageType.TT){
            checkPoints = new ArrayList<CheckPoint>();
        }
        this.length = length;
        this.startTime = startTime;
        waitingForResults = true;
    }

    public int getID(){ return ID; }
    public String getName(){ return name; } 
    public String getDescription(){ return description; }
    public String getStageLocation(){ return location; }
    public LocalDateTime getStartTime(){ return startTime; }
    public double getLength(){ return length; }
    public boolean getWaitingForResults(){ return waitingForResults; }
    public StageType getStageType(){ return stageType; }
    
    public void setWaitingForResults(boolean waitingForResults){ this.waitingForResults = waitingForResults; }
    public void addCheckPoint(int ID, CheckpointType type, double length){ checkPoints.add(new CheckPoint(ID, type, length)); }
    public void addCheckPoint(int ID, CheckpointType type, double length, double averageGradient){ checkPoints.add(new CheckPoint(ID, type, length, averageGradient)); }
    public String addRiderTimes(int ID, LocalTime[] riderTimes){
        if(times.containsKey(ID))
        {
            return "duplicate error";
        } 
        else if (riderTimes.length != checkPoints.size() + 2){
            return "checkpoint amount error";
        }
        else if (!waitingForResults)
        {
            return "not waiting for results";
        }
        times.put(ID, riderTimes);
        return null;
    }
    public void deleteRiderResultsInStage(int ID){
        times.remove(ID);
    }
    public LocalTime[] getRiderTimes(int ID)
    {
        return times.get(ID);
    }
    public LocalTime[] getRiderTimesAndEllapsed(int ID)
    {
        LocalTime[] riderTimes = times.get(ID);
        Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String elapsedTimeSDF = df.format(elapsedTimeSeconds);
        LocalTime elapsedTime = LocalTime.parse(elapsedTimeSDF);
        riderTimes = Arrays.copyOf(riderTimes, riderTimes.length +1);
        riderTimes[riderTimes.length -1 ] = elapsedTime;
        return riderTimes;
    }    
    public int[] getLeaderBoard(){
        int[] riderIDs = new int[times.size()];
        int counter = 0;
        for (int ID : times.keySet()){
            riderIDs[counter++] = ID;
        }
        return sortBasedOnElapsedTime(riderIDs);
    }
    public LocalTime[] getTimesInTimeOrder(){
        //actively stupid but needed for method in race
        LocalTime[] stageTimes = new LocalTime[times.size()];
        int counter = 0;
        //creates list of elapsed times
        for (int ID : times.keySet()){
            LocalTime[] riderTimes = times.get(ID);
            Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            String elapsedTimeSDF = df.format(elapsedTimeSeconds);
            LocalTime elapsedTime = LocalTime.parse(elapsedTimeSDF);
            stageTimes[counter++] = elapsedTime;
        }
        //puts into elapsed time order
        stageTimes = sortBasedOnElapsedForTimes(stageTimes);
        return stageTimes;
    }
    public LocalTime[] getAdjustedTimesInTimeOrder(){
        LocalTime[] stageTimes = getTimesInTimeOrder();
        //adjusts times to take into account riders finishing close together
        //if time between time and faster time is less than 1 second, makes same value
        //repeats until adjusted time is met/no changes
        Boolean changeHappens = true;
        while (changeHappens){
            changeHappens = false;
            for (int i = stageTimes.length; i >= 1; i++){
                if (stageTimes[i-1].until(stageTimes[i], ChronoUnit.SECONDS) < 1 && stageTimes[i-1].until(stageTimes[i], ChronoUnit.SECONDS) != 0){
                    stageTimes[i] = stageTimes[i-1];
                    changeHappens = true;
                }
            }
        }
        return stageTimes;
    }
    public LocalTime getRiderAdjustedElapsed(int inputID){
        //find position on regular leaderboard that is sorted by elapsed time
        int position = 0;
        int[] leaderboard = getLeaderBoard();
        for (int i = 0; i < leaderboard.length; i++){
            int riderID = leaderboard[i];
            if (riderID == inputID){
                position = i;
            }
        }
        //use position to return elapsed time
        return getAdjustedTimesInTimeOrder()[position];
    }
    public int[] getPointsInTimeOrder(){
        int[] points = new int[times.size()];
        Arrays.fill(points, 0);
        int[] currentCheckPointPoints;
        for (int checkPointPos = 0; checkPointPos <= checkPoints.size(); checkPointPos++){
            //get the points needed e.g. [50, 40, 30, 20] 1st 2nd 3rd
            currentCheckPointPoints = checkPoints.get(checkPointPos).getPointsAwarded();
            //make currentcheckpoint times into an array
            long[] checkPointTimes = new long[times.size()];
            int counter =0;
            for (int ID : times.keySet()){
                checkPointTimes[counter++] =  times.get(ID)[0].until(times.get(ID)[checkPointPos], ChronoUnit.SECONDS);
            }
            //find who's first put in appropriate position in points array and repeat
            for (int placePoints: currentCheckPointPoints){
                long earliestFinish = Arrays.stream(checkPointTimes).min().getAsLong();
                if (earliestFinish != 0) {
                    for (int i = 0; i < checkPointTimes.length; i++){
                        points[i] += placePoints;
                        checkPointTimes[i] = 0;
                    }
                }
            }
        }
        //for last checkpoint/end of stage
        long[] checkPointTimes = new long[times.size()];
        int counter = 0;
        for (int ID : times.keySet()){
            checkPointTimes[counter++] =  times.get(ID)[0].until(times.get(ID)[times.get(ID).length], ChronoUnit.SECONDS);
        }
        for (int placePoints: pointsAwarded){
            long earliestFinish = Arrays.stream(checkPointTimes).min().getAsLong();
            if (earliestFinish != 0) {
                for (int i = 0; i < checkPointTimes.length; i++){
                    points[i] += placePoints;
                    checkPointTimes[i] = 0;
                }
            }
        }
        //sort points by elapsed time and return
        return sortBasedOnElapsedTime(points);
    }
    
    public int[] getMountainPointsInTimeOrder(){
        int[] points = new int[times.size()];
        Arrays.fill(points, 0);
        int[] currentCheckPoint;
        for (int checkPointPos = 1; checkPointPos <= checkPoints.size(); checkPointPos++){
            if (checkPoints.get(checkPointPos).getCheckpointType() != CheckpointType.SPRINT ) {
                //get the points needed e.g. [50, 40, 30, 20] 1st 2nd 3rd
                currentCheckPoint = checkPoints.get(checkPointPos).getPointsAwarded();
                //make currentcheckpoint times into an array
                long[] checkPointTimes = new long[times.size()];
                int counter =0;
                for (int ID : times.keySet()){
                    checkPointTimes[counter++] =  times.get(ID)[0].until(times.get(ID)[checkPointPos], ChronoUnit.SECONDS);
                }
                //find who's first put in appropriate position in points array and repeat
                for (int placePoints: currentCheckPoint){
                    long earliestFinish = Arrays.stream(checkPointTimes).min().getAsLong();
                    if (earliestFinish != 0) {
                        for (int i = 0; i < checkPointTimes.length; i++){
                        points[i] += placePoints;
                        checkPointTimes[i] = 0;
                        }
                    }
                }
            }   
        }
            
        //sort points by elapsed time and return
        return sortBasedOnElapsedTime(points);
    }

    private int[] sortBasedOnElapsedTime(int[] inputArray){
        int[] elapsedTimeSecondsList = new int[times.size()];
        int counter = 0;
        for (int ID : times.keySet()){
            LocalTime[] riderTimes = times.get(ID);
            Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);
            elapsedTimeSecondsList[counter++] = elapsedTimeSeconds.intValue();
        }
        Boolean sorting = true;
        while (sorting){
            sorting = false;
            for (int i =1; i <= elapsedTimeSecondsList.length; i++){
                if (elapsedTimeSecondsList[i] < elapsedTimeSecondsList[i-1]){
                    //swap
                    int elapsedTimeTemp = elapsedTimeSecondsList[i];
                    int riderIDTemp = inputArray[i];
                    elapsedTimeSecondsList[i] = elapsedTimeSecondsList[i-1];
                    inputArray[i] = inputArray[i-1];
                    elapsedTimeSecondsList[i-1] = elapsedTimeTemp;
                    inputArray[i-1] = riderIDTemp;
                    sorting = true;
                }
            }
        }

        return inputArray;
    }

    private LocalTime[] sortBasedOnElapsedForTimes(LocalTime[] inputArray){
        int[] elapsedTimeSecondsList = new int[times.size()];
        int counter = 0;
        for (int ID : times.keySet()){
            LocalTime[] riderTimes = times.get(ID);
            Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);
            elapsedTimeSecondsList[counter++] = elapsedTimeSeconds.intValue();
        }
        Boolean sorting = true;
        while (sorting){
            sorting = false;
            for (int i =1; i <= elapsedTimeSecondsList.length; i++){
                if (elapsedTimeSecondsList[i] < elapsedTimeSecondsList[i-1]){
                    //swap
                    int elapsedTimeTemp = elapsedTimeSecondsList[i];
                    LocalTime riderIDTemp = inputArray[i];
                    elapsedTimeSecondsList[i] = elapsedTimeSecondsList[i-1];
                    inputArray[i] = inputArray[i-1];
                    elapsedTimeSecondsList[i-1] = elapsedTimeTemp;
                    inputArray[i-1] = riderIDTemp;
                    sorting = true;
                }
            }
        }

        return inputArray;
    }

}
