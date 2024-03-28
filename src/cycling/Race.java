package cycling;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 * This is the Race class to represent the Races within the system
 * 
 * @author Dylan Hough, Scott Van Wingerden
 * @version 1.0
 *
 */
public class Race implements java.io.Serializable{
    // instance variables
    private int ID;
    private String name;
    private String description;
    private ArrayList<Stage> stages;
    /** 
     * constructor for Race
     * @param id the unique identifier of the Race
     * @param name the races name
     * @param description the races description
     */
    public Race(int id, String name, String description) {
        ID = id;
        this.name = name;
        this.description = description;
    }
    // Getters
    /** 
     * Gets the ID of the Race
     * @return ID
     */
    public int getID(){ return ID; }
    /** 
     * Gets the Name of the Race
     * @return ID
     */
    public String getName() { return name; }
    /** 
     * Gets the Description
     * @return description
     */
    public String getDescription() { return description; }
    /** 
     * Gets the Stages
     * @return stages
     */
    public ArrayList<Stage> getStages() { return stages; }
    /** 
     * Gets the details of race : ID, name, description, number of stages, length
     * @return details 
     */
    public String getDetails(){ return ID + " " + name + " " + description + " " + stages.size() + " " + getLength(); }
    /** 
     * Gets the number of stages
     * @return number of stages
     */
    public int getNumberOfStages(){ return stages.size(); }
    /** 
     * Gets the stage IDs
     * @return stageIds
     */
    public int[] getStageIds(){
        int[] stageIds = new int[stages.size()];// array of stage IDs
        Collections.sort(stages, Comparator.comparing(Stage::getStartTime));// sort stages by start time
        int i = 0;// index for stageIds
        for(Stage stage: stages){stageIds[i++] = stage.getID();}// add stage ID to stageIds
        return stageIds;// return stageIds
    }
    /** 
     * Gets the stage leader board
     * @param stageID the ID of the stage
     * @return riderIds
     */
    public int[] getStageLeaderBoard(int stageID){
        int[] riderIds = null;// array of rider IDs
        for (Stage stage : stages){// iterate through stages
            if (stage.getID() == stageID){// if stage ID matches
                riderIds = stage.getLeaderBoard();// get the leader board
            }
        }
        return riderIds;// return riderIds
    }
    /**
     * Gets the length of the race
     * @return length
     */
    public double getLength(){ 
        double length = 0;// sum of all stage lengths
        for(Stage stage: stages){length =+ stage.getLength();} // sum of all stage lengths
        return length; 
    }

    // setters
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    /** 
     * adds a stage to the race
     * @param stage the stage to add to the race
     */
    public void addStage(Stage stage){
        stages.add(stage);
    }
    /** 
     * removes a stage to the race
     * @param stage the stage to remove from the race
     */
    public void removeStage(Stage stage){
        int i = stages.indexOf(stage);// index of stage
        stages.remove(i);// remove stage
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
                    riderPoints.set(index , riderIDs.get(index) + stagePoints[counter]);
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



}
