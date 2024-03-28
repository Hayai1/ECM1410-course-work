package cycling;
/**
 * This is the Rider class to represent the Riders within teams
 * 
 * @author Dylan Hough, Scott Van Wingerden
 * @version 1.0
 *
 */

public class Rider implements java.io.Serializable{
    // instance variables
    private int ID;
    private int teamID;
    private String name;
    private int yearOfBirth;

    /** 
     * constructor for Rider
     * @param id the unique identifier of the Rider
     * @param teamID the unique identifier of the team the rider is in
     * @param name the riders name
     * @param yearOfBirth the riders year of birth
     */
    public Rider(int id,int teamID, String name, int yearOfBirth) {
        ID = id;
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }
    // Getters
    /** 
     * Gets the ID of the Rider
     * @return ID
     */
    public int getID(){ return ID; }
    /** 
     * Gets the Team ID of the Rider
     * @return teamID
     */
    public int getTeamID() { return teamID; }
    /** 
     * Gets the Name of the Rider
     * @return name
     */
    public String getName() { return name; }
    /** 
     * Gets the Year of Birth of the Rider
     * @return yearOfBirth
     */
    public double getYearOfBirth() { return yearOfBirth; }
    // Setters
    /** 
     * Sets the ID of the Rider
     * @param id the unique identifier of the Rider
     */
    public void setName(String name) { this.name = name; }
    /** 
     * Sets the Team ID of the Rider
     * @param teamID the unique identifier of the team the rider is in
     */
    public void setTeamID(int teamID) { this.teamID = teamID; }
    
}
