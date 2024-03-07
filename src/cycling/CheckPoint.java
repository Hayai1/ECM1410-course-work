package cycling;
import java.time.LocalDateTime;
import cycling.CheckpointType;
import java.util.UUID;

public class CheckPoint {
    /**
     * Unique identifier for the checkpoint.
     */
    private UUID ID;

    /**
     * Points awarded at this checkpoint.
     */
    private int pointsAwarded;

    /**
     * Location of the checkpoint.
     */
    private String location;

    /**
     * Length of the checkpoint.
     */
    private double length;

    /**
     * Type of the checkpoint.
     */
    private CheckpointType type;

    /**
     * Constructor for the CheckPoint class.
     *
     * @param ID Unique identifier for the checkpoint.
     * @param pointsAwarded Points awarded at this checkpoint.
     * @param length Length of the checkpoint.
     * @param type Type of the checkpoint.
     */
    public CheckPoint(int ID, int pointsAwarded, double length, CheckpointType type) {
        this.ID = java.util.UUID.randomUUID();
        this.type = type;
    }

    /**
     * Returns the unique identifier of the checkpoint.
     *
     * @return UUID of the checkpoint.
     */
    public UUID getID() {
        return ID;
    }

    /**
     * Returns the location of the checkpoint.
     *
     * @return Location of the checkpoint.
     */
    public String getStageLocation() {
        return location;
    }

    /**
     * Returns the length of the checkpoint.
     *
     * @return Length of the checkpoint.
     */
    public double getLength() {
        return length;
    }

    /**
     * Returns the type of the checkpoint.
     *
     * @return Type of the checkpoint.
     */
    public CheckpointType getCheckpointType() {
        return type;
    }

    /**
     * Returns the points awarded at the checkpoint.
     *
     * @return Points awarded at the checkpoint.
     */
    public int getPointsAwareded() {
        return pointsAwarded;
    }
}