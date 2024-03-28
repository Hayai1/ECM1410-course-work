package cycling;
import java.util.ArrayList;

/**
 * This is the Team class to represent the Teams within the system
 * 
 * @author Dylan Hough, Scott Van Wingerden
 * @version 1.0
 *
 */
public class Team implements java.io.Serializable{
    // instance variables
    private int ID;
    private String name;
    private String description;
    private ArrayList<Rider> Riders;

    /** 
     * constructor for Team
     * @param id the unique identifier of the Team
     * @param name the teams name
     * @param description the teams description
     */
    public Team(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }
    // Getters
    /** 
     * Gets the ID of the Team
     * @return ID
     */
    public int getID(){ return ID; }
    /** 
     * Gets the Name of the Team
     * @return ID
     */
    public String getName() { return name; }
    /** 
     * Gets the Description
     * @return description
     */
    public String getDescription() { return description; }
    /** 
     * Gets the Riders
     * @return Riders
     */
    public ArrayList<Rider> getRiders() { return Riders; }
    // Setters
    /** 
     * Sets the ID of the Team
     * @param id the unique identifier of the Team
     */
    public void setName(String name) { this.name = name; }
    /** 
     * Sets the Name of the Team
     * @param name the teams name
     */
    public void setDescription(String description) { this.description = description; }
    /** 
     * Sets the Riders of the Team
     * @param Riders the riders in the team
     */
    public void addRider(Rider newRider){ Riders.add(newRider); }
    
}