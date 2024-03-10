package cycling;
import java.util.ArrayList;
import cycling.StageType;
import java.time.LocalDateTime;
public class Stage {

    
    private int ID;
    private String name;
    private String description;
    private ArrayList<CheckPoint> checkPoints;
    private StageType stageType;
    private LocalDateTime startTime;
    private String location;
    private double length;


    public Stage(int id, String name, String description, StageType stageType, double length){
        ID = id;
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
