package cycling;

public class Rider implements java.io.Serializable{
    private int ID;
    private int teamID;
    private String name;
    private int yearOfBirth;

    public Rider(int id,int teamID, String name, int yearOfBirth) {
        ID = id;
        this.teamID = teamID;
        this.name = name;
        this.yearOfBirth = yearOfBirth;
    }

    public int getID(){ return ID; }
    public int getTeamID() { return teamID; }
    public String getName() { return name; }
    public double getYearOfBirth() { return yearOfBirth; }

    public void setName(String name) { this.name = name; }
    public void setTeamID(int teamID) { this.teamID = teamID; }
    
}
