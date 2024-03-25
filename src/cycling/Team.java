package cycling;
import java.util.LinkedList;

public class Team {
    private int ID;
    private String name;
    private String description;
    private LinkedList<Rider> Riders;

    public Team(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }

    public int getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LinkedList<Rider> getRiders() { return Riders; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    public void addRider(Rider newRider){ Riders.add(newRider); }
    
}