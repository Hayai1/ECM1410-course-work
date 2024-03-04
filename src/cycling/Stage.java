package cycling;
import java.util.ArrayList;
import cycling.StageType;
import java.time.LocalDateTime;
public class Stage {
    int ID;
    String name;
    String description;
    ArrayList<CheckPoint> checkPoints;
    StageType stageType;
    LocalDateTime startTime;
    String location;
    double length;
    public Stage(int ID, String name, String description, StageType stageType, double length){
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.stageType = stageType;
        this.length = length;
    }

    public int getID(){ return ID; }
    public String getName(){ return name; } 
    public String getDescription(){ return description; }
    public String getStageLocation(){ return location; }
    public LocalDateTime getStartTime(){ return startTime; }
    public double getLength(){ return length; }
}
