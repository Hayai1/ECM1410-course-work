package cycling;
import java.util.ArrayList;
import cycling.StageType;
import java.time.LocalDateTime;
import java.util.LinkedList;
public class Stage {

    
    private int ID;
    private String name;
    private String description;
    private LinkedList<CheckPoint> checkPoints;
    private StageType stageType;
    private LocalDateTime startTime;
    private String location;
    private double length;
    private boolean waitingForResults;

    public Stage(int ID, String name, String description, StageType stageType, double length){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.stageType = stageType;
        if (stageType != StageType.TT){
            checkPoints = new LinkedList<CheckPoint>();
        }
        this.length = length;
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

}
