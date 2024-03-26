package cycling;
import java.time.LocalDateTime;
import cycling.CheckpointType;
import java.util.UUID;
public class CheckPoint implements java.io.Serializable{
    private int ID;
    private int[] pointsAwarded;
    private double location;
    private double averageGradient;
    private CheckpointType type;
    public CheckPoint(int id,  CheckpointType type, double location)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
        this.pointsAwarded = setPointsAwarded(type);
    }

    public CheckPoint(int id,  CheckpointType type, double location, double averageGradient)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
        this.averageGradient = averageGradient;
        this.pointsAwarded = setPointsAwarded(type);
    }
    private int[] setPointsAwarded(CheckpointType type){
        switch (type) {
            case SPRINT:
                int[] sprintPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
                return sprintPoints;
            case C4:
                int[] cat4Points = {1};
                return cat4Points;
            case C3:
                int[] cat3Points = {2,1};
                return cat3Points;
            case C2:
                int[] cat2Points = {5,3,2,1};
                return cat2Points;
            case C1:
                int[] cat1Points = {10,8,6,4,2,1};
                return cat1Points;
            default:
                int[] eXtremePoints = {20,15,12,10,8,6,4,2};
                return eXtremePoints;
        }

    }

    public int getID(){ return ID; }
    public double getStageLocation(){ return location; }
    public double getAverageGradient(){ return averageGradient; }
    public CheckpointType getCheckpointType() { return type; }
    public int[] getPointsAwarded() { return pointsAwarded; }
}
