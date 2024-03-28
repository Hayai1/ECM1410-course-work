package cycling;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This is the Stage class to represent the Stage within Races
 * 
 * @author Dylan Hough, Scott Van Wingerden
 * @version 1.0
 *
 */

public class Stage implements java.io.Serializable{
    // instance variables
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

    /** 
     * constructor for Stage
     * @param ID the unique identifier of the Stage
     * @param name the name of the Stage
     * @param description the description of the Stage
     * @param stageType the type of stage e.g. Flat, Medium Mountain, High Mountain, Time Trial
     * @param length the length of the stage
     * @param startTime the start time of the stage
     */
    public Stage(int ID, String name, String description, StageType stageType, double length, LocalDateTime startTime){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.stageType = stageType;
        //set points awarded based on stage type
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
    
    //getters
    /** 
     * Gets the ID of the Stage
     * @return ID
     */
    public int getID(){ return ID; }
    /** 
     * Gets the Name of the Stage
     * @return name
     */
    public String getName(){ return name; }
    /** 
     * Gets the Description of the Stage
     * @return description
     */
    public String getDescription(){ return description; }
    /** 
     * Gets the Location of the Stage
     * @return location
     */
    public String getStageLocation(){ return location; }
    /** 
     * Gets the Start Time of the Stage
     * @return startTime
     */
    public LocalDateTime getStartTime(){ return startTime; }
    /** 
     * Gets the Length of the Stage
     * @return length
     */
    public double getLength(){ return length; }
    /** 
     * Gets the getWaitingForResults of the Stage
     * @return true if stage is wating for results
     */
    public boolean getWaitingForResults(){ return waitingForResults; }
    /** 
     * Gets the stage type
     * @return stage type
     */
    public StageType getStageType(){ return stageType; }
    /** 
     * Gets the CheckPoints in the stage
     * @return arraylist of CheckPoints
     */
    public ArrayList<CheckPoint> getCheckPoints(){ return checkPoints; }
    /** 
     * Gets a specified riders checkpoint and finishline times fot the stage
     * @param ID the unique identifier of the rider
     * @return pointsAwarded
     */
    public LocalTime[] getRiderTimes(int ID) { return times.get(ID); }
    /** 
     * Gets the time that a specified rider took to complete the stage
     * @param ID the unique identifier of the rider
     * @return pointsAwarded
     */
    public LocalTime[] getRiderTimesAndEllapsed(int ID)
    {
        LocalTime[] riderTimes = times.get(ID);//get rider times
        Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);//find the diffence between start and finish
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");//format the time
        String elapsedTimeSDF = df.format(elapsedTimeSeconds);
        LocalTime elapsedTime = LocalTime.parse(elapsedTimeSDF);//convert to local time
        riderTimes[riderTimes.length + 1] = elapsedTime;//add to array
        return riderTimes;//return array
    }   
    /** 
     * Gets the LeaderBoard awarded for the stage
     * @return pointsAwarded
     */   
    public int[] getLeaderBoard(){
        int[] riderIDs = new int[times.size()];//create array of rider IDs
        int counter = 0;//counter for array
        for (int ID : times.keySet()){//for each rider
            riderIDs[counter++] = ID;//add to array
        }
        return sortBasedOnElapsedTime(riderIDs);//sort array based on elapsed time
    }
    /** 
     * Gets the Points of all the riders In Time Order for the stage
     * @return pointsAwarded
     */
    public int[] getPointsInTimeOrder(){
        long[] checkPointTimes;//this will store the times each rider took to get to a specific checkpoint
        int[] points = new int[times.size()];//create array of points for each rider
        Arrays.fill(points, 0);//fill array with 0s
        int[] currentCheckPointPoints;//this will store the amount of points awarded for the current checkpoint
        for (int checkPointPos = 0; checkPointPos <= checkPoints.size(); checkPointPos++){//for each checkpoint
            //get the points needed e.g. [50, 40, 30, 20] 1st 2nd 3rd
            currentCheckPointPoints = checkPoints.get(checkPointPos).getPointsAwarded();
            //make currentcheckpoint times into an array
            checkPointTimes = new long[times.size()];
            int counter =0;
            //for each rider get the time they took to get to the checkpoint
            for (int ID : times.keySet()){
                checkPointTimes[counter++] =  times.get(ID)[0].until(times.get(ID)[checkPointPos], ChronoUnit.SECONDS);
            }
            //find who's first put in appropriate position in points array and repeat
            for (int pointsAwarded: currentCheckPointPoints){//for each Points awarded
                long earliestFinish = Arrays.stream(checkPointTimes).min().getAsLong();//find the next best time
                if (earliestFinish != 0) {//if there is a finish time
                    for (int i = 0; i < checkPointTimes.length; i++){//adds the same points to all riders who finished at the same time
                        if (checkPointTimes[i] == earliestFinish){//if rider finished at the same time
                            points[i] += pointsAwarded;//add points to rider
                            checkPointTimes[i] = 0;//set time to 0
                        }
                    }
                }
            }
        }
        //for last checkpoint/end of stage
        checkPointTimes = new long[times.size()];//create array of times
        int counter = 0;
        for (int ID : times.keySet()){//for each rider
            checkPointTimes[counter++] =  times.get(ID)[0].until(times.get(ID)[times.get(ID).length], ChronoUnit.SECONDS);//get time to finish
        }
        for (int placePoints: pointsAwarded){//for each place
            long earliestFinish = Arrays.stream(checkPointTimes).min().getAsLong();//find the next best time
            if (earliestFinish != 0) {//if there is a finish time
                for (int i = 0; i < checkPointTimes.length; i++){
                    if (checkPointTimes[i] == earliestFinish){//if rider finished at the same time
                        points[i] += placePoints;
                        checkPointTimes[i] = 0;
                    }
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

    //setters
    /** 
     * Sets the WaitingForResults field of the Stage
     * @param WaitingForResults the boolien value to set WaitingForResults of the Stage
     */
    public void setWaitingForResults(boolean waitingForResults){ this.waitingForResults = waitingForResults; }
    /** 
     * adds a checkpoint to the stage
     * @param ID ID of the stage
     * @param type the type of checkpoint
     * @param length the length of the checkpoint
     */
    public void addCheckPoint(int ID, CheckpointType type, double length){ checkPoints.add(new CheckPoint(ID, type, length)); }
    /** 
     * adds a checkpoint to the stage
     * @param ID ID of the stage
     * @param type the type of checkpoint
     * @param length the length of the checkpoint
     * @param averageGradient the average gradient of the checkpoint
     */
    public void addCheckPoint(int ID, CheckpointType type, double length, double averageGradient){ checkPoints.add(new CheckPoint(ID, type, length, averageGradient)); }
    /** 
     * adds a checkpoint to the stage
     * @param ID ID of the stage
     * @param riderTimes array of rider times
     * @return null if no errors, otherwise returns error message
     */
    public String addRiderTimes(int ID, LocalTime[] riderTimes){
        if(times.containsKey(ID))//if rider already has times
        {
            return "duplicate error";
        } 
        else if (riderTimes.length != checkPoints.size() + 2){//if rider times and checkpoints amount don't match
            return "checkpoint amount error";
        }
        else if (!waitingForResults)//if not waiting for results
        {
            return "not waiting for results";
        }
        times.put(ID, riderTimes);//add rider times
        return null;//return null as no errors
    }
    /** 
     * deletes a riders results in the stage
     * @param ID the unique identifier of the rider to remove
     */
    public void deleteRiderResultsInStage(int ID){
        times.remove(ID);
    }
    /** 
     * removes a checkpoint from the stage
     * @param ID the unique identifier of the checkpoint to remove
     */
    public void removeCheckPoint(int ID){
        for (CheckPoint checkPoint : checkPoints){
            if (checkPoint.getID() == ID){
                checkPoints.remove(checkPoint);
            }
        }
    }
    /** 
     * bubble sorts the array of rider points or rider ids on the elapsed time
     * @param inputArray the array to sort
     * @return the sorted array
     */
    private int[] sortBasedOnElapsedTime(int[] inputArray){
        int[] elapsedTimeSecondsList = new int[times.size()];//create array of elapsed times
        int counter = 0;//counter for array
        for (int ID : times.keySet()){//for each rider
            LocalTime[] riderTimes = times.get(ID);//get rider times
            Long elapsedTimeSeconds = riderTimes[0].until(riderTimes[riderTimes.length], ChronoUnit.SECONDS);//find the diffence between start and finish
            elapsedTimeSecondsList[counter++] = elapsedTimeSeconds.intValue();//add to array
        }
        //bubble sort
        Boolean sorting = true;//set sorting to true
        while (sorting){//while sorting is true
            sorting = false;//set sorting to false
            for (int i =1; i <= elapsedTimeSecondsList.length; i++){//for each elapsed time
                if (elapsedTimeSecondsList[i] < elapsedTimeSecondsList[i-1]){//if the current time is less than the previous time
                    //swap
                    int elapsedTimeTemp = elapsedTimeSecondsList[i];//store current time
                    int riderIDTemp = inputArray[i];//store current rider id
                    elapsedTimeSecondsList[i] = elapsedTimeSecondsList[i-1];//set current time to previous time
                    inputArray[i] = inputArray[i-1];//set current rider id to previous rider id
                    elapsedTimeSecondsList[i-1] = elapsedTimeTemp;//set previous time to current time
                    inputArray[i-1] = riderIDTemp;//set previous rider id to current rider id
                    sorting = true;//set sorting to true
                }
            }
        }
        return inputArray;//return sorted array
    }
}
