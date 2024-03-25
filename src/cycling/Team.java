package cycling;

import java.util.ArrayList;
import java.util.UUID;

public class Team implements java.io.Serializable{
    private int ID;
    private String name;
    private String description;
    private ArrayList<Rider> Riders;

    public Team(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }

    public int getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<Rider> getRiders() { return Riders; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    public void addRider(Rider newRider){ Riders.add(newRider); }
    
}