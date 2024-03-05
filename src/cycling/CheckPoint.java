package cycling;
import java.time.LocalDateTime;
import cycling.CheckpointType;

public class CheckPoint {
    int ID;
    int pointsAwarded;
    String location;
    double length;
    CheckpointType type;
    public CheckPoint(int ID, int pointsAwarded, double length, CheckpointType type)
    {
        this.type = type;
    }
    
    public int getID(){ return ID; }
    public String getStageLocation(){ return location; }
    public double getLength(){ return length; }
    public CheckpointType getCheckpointType() { return type; }
    public int getPointsAwareded() { return pointsAwarded; }
}
