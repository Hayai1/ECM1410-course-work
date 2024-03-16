package cycling;
import java.util.HashMap;
import java.util.LinkedList;

public class Race {
    private int ID;
    private String name;
    private String description;
    private HashMap<Rider, Integer> riders;//riders -> points
    private LinkedList<Stage> stages;
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
    public LinkedList<Stage> getStages() { return stages; }
 
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setLength(double length) { this.length = length; }

    public void addStage(Stage stage){
        stages.add(stage);
    }
    public void removeStage(Stage stage){
        int i = stages.indexOf(stage);
        stages.remove(i);
    }
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
