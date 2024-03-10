package cycling;
import cycling.Rider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
public class Race {
    private UUID ID;
    private String name;
    private String description;
    private HashMap<Rider, Integer> riders;//riders -> points
    private ArrayList<Stage> stages;
    private double length; 

    public Race(int id, String name, String description) {
        ID = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public UUID getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getLength() { return length; }
 
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLength(double length) { this.length = length; }


}
