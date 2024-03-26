package cycling;
import java.time.LocalDateTime;
import cycling.CheckpointType;
import java.util.UUID;
public class CheckPoint implements java.io.Serializable{
    private int ID;
    private int pointsAwarded;
    private double location;
    private double averageGradient;
    private CheckpointType type;
    public CheckPoint(int id,  CheckpointType type, double location)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
    }

    public CheckPoint(int id,  CheckpointType type, double location, double averageGradient)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
        this.averageGradient = averageGradient;
    }
    
    public int getID(){ return ID; }
    public double getStageLocation(){ return location; }
    public double getAverageGradient(){ return averageGradient; }
    public CheckpointType getCheckpointType() { return type; }
    public int getPointsAwareded() { return pointsAwarded; }
}
