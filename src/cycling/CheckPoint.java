package cycling;

/**
 * This is the Checkpoint class to represent the checkpoints within stages
 * 
 * @author Dylan Hough, Scott Van Wingerden
 * @version 1.0
 *
 */

public class CheckPoint implements java.io.Serializable{
    // instance variables
    private int ID;
    private int[] pointsAwarded;
    private double location;
    private double averageGradient;
    private CheckpointType type;
    /** 
     * constructor for CheckPoint
     * @param id the unique identifier of the checkpoint
     * @param type the type of checkpoint e.g. Sprint, C4, C3, C2, C1, Extreme
     * @param location the location of the checkpoint on the stage
     */
    public CheckPoint(int id,  CheckpointType type, double location)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
        this.pointsAwarded = setPointsAwarded(type);
    }
    /** 
     * constructor for CheckPoint with average gradient as param
     * @param id the unique identifier of the checkpoint
     * @param type the type of checkpoint e.g. Sprint, C4, C3, C2, C1, Extreme
     * @param location the location of the checkpoint on the stage
     * @param averageGradient the average gradient of the checkpoint
     */
    public CheckPoint(int id,  CheckpointType type, double location, double averageGradient)
    {
        this.ID = id;
        this.type = type;
        this.location = location;
        this.averageGradient = averageGradient;
        this.pointsAwarded = setPointsAwarded(type);
    }
    
    /** 
     * @param type the type of checkpoint e.g. Sprint, C4, C3, C2, C1, Extreme
     * @return int[] the points awarded for each rank in the checkpoint
     */
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
                int[] extremePoints = {20,15,12,10,8,6,4,2};
                return extremePoints;
        }

    }
    //getters
    /** 
     * Gets the ID of the Checkpoint
     * @return ID
     */
    public int getID(){ return ID; }
    /** 
     * Gets the location of the Checkpoint
     * @return location
     */
    public double getStageLocation(){ return location; }
    /** 
     * Gets the average gradient of the Checkpoint
     * @return averageGradient
     */
    public double getAverageGradient(){ return averageGradient; }
    /** 
     * Gets the type of the Checkpoint
     * @return type
     */
    public CheckpointType getCheckpointType() { return type; }
    /** 
     * Gets the points awarded for each rank in the Checkpoint
     * @return pointsAwarded
     */
    public int[] getPointsAwarded() { return pointsAwarded; }
}
