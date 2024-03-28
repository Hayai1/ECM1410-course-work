package cycling;

import cycling.Rider;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
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

    public int[] getPointLeaderboard(){
        ArrayList<Integer> riderIDs = new ArrayList<Integer>();
        ArrayList<Integer> riderPoints = new ArrayList<Integer>();
        for (Stage stage: stages){
            int[] stagePoints = stage.getPointsInTimeOrder();
            int counter = 0;
            for (int ID: stage.getLeaderBoard()){
                if (riderIDs.contains(ID)){
                    int index = riderIDs.indexOf(ID);
                    riderPoints.set(index , riderPoints.get(index) + stagePoints[counter]);
                }else
                {
                    riderIDs.add(ID);
                    riderPoints.add(stagePoints[riderIDs.indexOf(ID)]);
                }
                counter++;
            }
        }
        //converts Arraylists into arrays to be sorted
        Integer[] arrayIDs = riderIDs.toArray(new Integer[riderIDs.size()]);
        Integer[] arrayPts = riderPoints.toArray(new Integer[riderPoints.size()]);
        //sorts arrayIDs to be in order of arrayPts desc
        Boolean sorting = true;
        while (sorting){
            sorting = false;
            for (int i =1; i <= arrayPts.length; i++){
                if (arrayPts[i] > arrayPts[i-1]){
                    //swap
                    Integer riderIDTemp = arrayIDs[i];
                    Integer riderPtsTemp = arrayPts[i];
                    arrayIDs[i] = arrayIDs[i-1];
                    arrayPts[i] = arrayPts[i-1];
                    arrayIDs[i-1] = riderIDTemp;
                    arrayPts[i-1] = riderPtsTemp;
                    sorting = true;
                }
            }
        }


        return Arrays.stream(arrayIDs).mapToInt(Integer::intValue).toArray();
    }
    public int[] getMountainPointLeaderboard(){
        ArrayList<Integer> riderIDs = new ArrayList<Integer>();
        ArrayList<Integer> riderPoints = new ArrayList<Integer>();
        for (Stage stage: stages){
            int[] stagePoints = stage.getMountainPointsInTimeOrder();
            int counter = 0;
            for (int ID: stage.getLeaderBoard()){
                if (riderIDs.contains(ID)){
                    int index = riderIDs.indexOf(ID);
                    riderPoints.set(index , riderPoints.get(index) + stagePoints[counter]);
                }else
                {
                    riderIDs.add(ID);
                    riderPoints.add(stagePoints[riderIDs.indexOf(ID)]);
                }
                counter++;
            }
        }
        //converts Arraylists into arrays to be sorted
        Integer[] arrayIDs = riderIDs.toArray(new Integer[riderIDs.size()]);
        Integer[] arrayPts = riderPoints.toArray(new Integer[riderPoints.size()]);
        //sorts arrayIDs to be in order of arrayPts desc
        Boolean sorting = true;
        while (sorting){
            sorting = false;
            for (int i =1; i <= arrayPts.length; i++){
                if (arrayPts[i] > arrayPts[i-1]){
                    //swap
                    Integer riderIDTemp = arrayIDs[i];
                    Integer riderPtsTemp = arrayPts[i];
                    arrayIDs[i] = arrayIDs[i-1];
                    arrayPts[i] = arrayPts[i-1];
                    arrayIDs[i-1] = riderIDTemp;
                    arrayPts[i-1] = riderPtsTemp;
                    sorting = true;
                }
            }
        }


        return Arrays.stream(arrayIDs).mapToInt(Integer::intValue).toArray();
    }
    public int[] getStageAdjustedLeaderboard(){
        ArrayList<Integer> riderIDs = new ArrayList<Integer>();
        ArrayList<LocalTime> riderTimes = new ArrayList<LocalTime>();
        for (Stage stage: stages){
            LocalTime[] stageTimes = stage.getAdjustedTimesInTimeOrder();
            int counter = 0;
            for (int ID: stage.getLeaderBoard()){
                if (riderIDs.contains(ID)){
                    int index = riderIDs.indexOf(ID);
                    riderTimes.set(index , riderTimes.get(index).plusSeconds(stageTimes[counter].toSecondOfDay()));
                }else
                {
                    riderIDs.add(ID);
                    riderTimes.add(stageTimes[riderIDs.indexOf(ID)]);
                }
                counter++;
            }
        }
        //converts Arraylists into arrays to be sorted
        Integer[] arrayIDs = riderIDs.toArray(new Integer[riderIDs.size()]);
        LocalTime[] arrayPts = riderTimes.toArray(new LocalTime[riderTimes.size()]);
        //sorts arrayIDs to be in order of arrayPts desc
        Boolean sorting = true;
        while (sorting){
            sorting = false;
            for (int i =1; i <= arrayPts.length; i++){
                if (arrayPts[i].compareTo(arrayPts[i-1]) > 1){
                    //swap
                    Integer riderIDTemp = arrayIDs[i];
                    LocalTime riderPtsTemp = arrayPts[i];
                    arrayIDs[i] = arrayIDs[i-1];
                    arrayPts[i] = arrayPts[i-1];
                    arrayIDs[i-1] = riderIDTemp;
                    arrayPts[i-1] = riderPtsTemp;
                    sorting = true;
                }
            }
        }

        return Arrays.stream(arrayIDs).mapToInt(Integer::intValue).toArray();
    }



}
