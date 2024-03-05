package cycling;

import java.util.ArrayList;
import java.util.UUID;

public class Team {
    private UUID ID;
    private String name;
    private String description;
    private ArrayList<Rider> Riders;

    public Team( String name, String description) {
        ID = java.util.UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public UUID getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<Rider> getRiders() { return Riders; }

    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    public void addRider(Rider newRider){ Riders.add(newRider); }
    
}