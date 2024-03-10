package cycling;

import java.util.UUID;

public class Rider {
    private UUID ID;
    private UUID teamID;
    private String name;
    private int yearOfBirth;

    public Rider(UUID teamID, String name, int yearOfBirth) {
        ID = java.util.UUID.randomUUID();
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public UUID getID(){ return ID; }
    public UUID getTeamID() { return teamID; }
    public String getName() { return name; }
    public double getYearOfBirth() { return yearOfBirth; }

    public void setName(String name) { this.name = name; }
    public void setTeamID(UUID teamID) { this.teamID = teamID; }
    
}
