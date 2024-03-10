package cycling;
import java.time.LocalDateTime;
import cycling.CheckpointType;
import java.util.UUID;
public class CheckPoint {
    private int ID;
    private int pointsAwarded;
    private String location;
    private double length;
    private CheckpointType type;
    public CheckPoint(int id, int pointsAwarded, double length, CheckpointType type)
    {
        this.ID = id;
        this.type = type;
    }
    
    public int getID(){ return ID; }
    public String getStageLocation(){ return location; }
    public double getLength(){ return length; }
    public CheckpointType getCheckpointType() { return type; }
    public int getPointsAwareded() { return pointsAwarded; }
}
