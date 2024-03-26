package cycling;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
public class Stage implements java.io.Serializable{
    private int ID;
    private String name;
    private String description;
    private ArrayList<CheckPoint> checkPoints;
    private StageType stageType;
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
        riderTimes[riderTimes.length + 1] = elapsedTime;
        return riderTimes;
    }

}
