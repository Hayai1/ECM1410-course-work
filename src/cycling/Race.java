package cycling;

import cycling.Rider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
public class Race implements java.io.Serializable{
    private int ID;
    private String name;
    private String description;
    private ArrayList<Stage> stages;

    public Race(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }

    public int getID(){ return ID; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ArrayList<Stage> getStages() { return stages; }
 
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }

    public void addStage(Stage stage){
        stages.add(stage);
    }
    public void removeStage(Stage stage){
        int i = stages.indexOf(stage);
        stages.remove(i);
    }
    public double getLength(){ 
        double length = 0;
        for(Stage stage: stages){length =+ stage.getLength();} 
        return length; 
    }
    public String getDetails(){     
        return ID + " " + name + " " + description + " " + stages.size() + " " + getLength();
    }
    public int getNumberOfStages(){
        return stages.size();
    }
    public int[] getStageIds(){
        int[] stageIds = new int[stages.size()];
        Collections.sort(stages, Comparator.comparing(Stage::getStartTime));
        int i = 0;
        for(Stage stage: stages){stageIds[i++] = stage.getID();}
        return stageIds;
    }

    public int[] getStageLeaderBoard(int stageID){
        int[] riderIds = null;
        for (Stage stage : stages){
            if (stage.getID() == stageID){
                riderIds = stage.getLeaderBoard();
            }
        }
        return riderIds;
    }



}
