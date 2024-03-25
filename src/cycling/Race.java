package cycling;
import cycling.Rider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
public class Race implements java.io.Serializable{
    private int ID;
    private String name;
    private String description;
    private HashMap<Rider, Integer> riders;//riders -> points
    private ArrayList<Stage> stages;
    private double length; 

    public Race(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }

    public int getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getLength() { return length; }
 
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLength(double length) { this.length = length; }

    public String getDetails(){     
        return ID + " " + name + " " + description + " " + stages.size() + " " + length;
    }
    public int getNumberOfStages(){
        return stages.size();
    }
    public int[] getStageIds(){
        int[] stageIds = new int[stages.size()];
        int i = 0;
        for(Stage stage: stages){stageIds[i++] = stage.getID();}
        return stageIds;
    }


}
