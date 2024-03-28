package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * CyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {

	ArrayList<Team> teams;
	ArrayList<Race> races;
	private static int idCounter = 0;//counter for a new id

	//constructor creates arrays for team and race
	public CyclingPortalImpl() {
		teams = new ArrayList<Team>();//create a new array list of teams
		races = new ArrayList<Race>();//create a new array list of races
	}

	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[races.size()];//create an array with the size of the race array that will store the ids
		int i = 0;
		for(Race race: races){raceIds[i++] = race.getID();}//iterate through each id and store in the array
		return raceIds;//return the array with the ids
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		for (Race race : races) {//iterate through each stored race
			if (race.getName() == name) {//if race exists with the same name throw exception
				throw new IllegalNameException("illegal name, name already exists");
			}
		}
		//exceptions for invalid names
		if (name == "") {
			throw new InvalidNameException("Invalid name, name cannot be empty, , or have white spaces");
		}
		else if (name == null) {
			throw new IllegalArgumentException("Invalid name,name cannot be null");
		}
		else if (name.length() > 30) {
			throw new IllegalArgumentException("Invalid name, name cannot have more than 30 characters");
		}
		else if (name.contains(" ")) {
			throw new IllegalArgumentException("Invalid name,name cannot have white spaces");
		}
		
		int id = ++idCounter;//create new id
		Race race = new Race(id, name, description);//create new race
		this.races.add(race);//add the new race to the array
		return id;//return the new race id
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {//iterate through each race
			if (race.getID() == raceId) {//if a new race is found with the same id
				return race.getDetails();//return the details of the race
			}
		}
		throw new IDNotRecognisedException("Race not found");//throw new race not found exception as no race with the id specified was found
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {//iterate through the race
			if (race.getID() == raceId) {//if the race id is found
				races.remove(race);//remove the the race
				return;//return
			}
		}
		throw new IDNotRecognisedException("Race not found");//throw error as the race was not found 

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {//iterate through the races
			if (race.getID() == raceId) {//find the correct race
				return race.getNumberOfStages();//return the number of stages
			}
		}
		throw new IDNotRecognisedException("Race not found");//throw error if race wasn't found
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
			if (length < 5)//if the length is less than 5 throw exception
			{ 
				throw new InvalidLengthException("Invalid length, length must be at least 5");
			}
			if (stageName == "") //if the stage name is empty throw exception
			{
				throw new InvalidNameException("Invalid name, name cannot empty");
			}
			else if (stageName == null) //if the stage name is null throw exception
			{
				throw new InvalidNameException("Invalid name, name cannot null");
			}
			else if (stageName.length() > 30) //if the stage name is longer than 30 characters throw exception
			{
				throw new InvalidNameException("Invalid name, name cannot be longer than 30 characters");
			}
			else if (stageName.contains(" ")) //if the stage name contains white spaces throw exception
			{
				throw new InvalidNameException("Invalid name, name cannot contain spaces");
			}
			for (Race race : races) {//for every race
				if (race.getID() == raceId) {//if it's the race we are looking for
					for (Stage stage : race.getStages()) {//for every stage in that race
						if (stage.getName() == stageName) {//if the stage name already exists throw exception
							throw new IllegalNameException("illegal name, stage name already exists in this race");
						}
					}
					//if the stage doesn't exist yet
					idCounter++;//increment the id counter for the next id
					int id = idCounter;
					Stage stage = new Stage(id, stageName, description, type, length, startTime);//create a new stage
					race.addStage(stage);//add the stage to the race
					return id;//return it's id
				}
			}
			throw new IDNotRecognisedException("race not found");//throw exception as at this point the race wouldn't have been found


	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {//for each race
			if (race.getID() == raceId) {// if it's the race we are looking for
				return race.getStageIds();//return the ids of the stages in that race
			}
		}
		throw new IDNotRecognisedException("Race not found");//throw exception if the race is not found
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		for (Race race : races){//for each length of the stage
			for (Stage stage : race.getStages()){//for each stage
				if (stage.getID() == stageId){//if it's the stage we are looking for
					return stage.getLength();//return the length of the stage
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		for (Race race : races){//for each race
			for (Stage stage : race.getStages()){//for each stage in that race
				if (stage.getID() == stageId){// if it's the stage we are looking for
					race.removeStage(stage);//remove the stage
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage in that race
				if (stage.getID() == stageId) {//if the stage is the one we are looking for
					double stageLength = stage.getLength();//get the length of the stage
					if (location > stageLength) {//if the location is outside the bounds of the stage throw exception
						throw new InvalidLocationException("Invalid location, outside the bounds of the stage");
					}
					else if (stage.getWaitingForResults()) {//if the stage is waiting for results throw exception
						throw new InvalidStageStateException("Invalid stage state, stage is waiting for rsults");
					}
					else if (stage.getStageType() == StageType.TT) {//if the stage is a time trial throw exception
						throw new InvalidStageTypeException("Invalid stage type, Time trials do not have checkpoints");
					}
					idCounter++;//increment the id counter
					stage.addCheckPoint(idCounter, type, length, averageGradient);//add the checkpoint to the stage
					return idCounter;//return the id of the checkpoint
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					double stageLength = stage.getLength();//get the length of the stage
					if (location > stageLength) {//if the location is outside the bounds of the stage throw exception
						throw new InvalidLocationException("Invalid location, outside the bounds of the stage");
					}
					else if (stage.getWaitingForResults()) {//if the stage is waiting for results throw exception
						throw new InvalidStageStateException("Invalid stage state, stage is waiting for results");
					}
					else if (stage.getStageType() == StageType.TT) {//if the stage is a time trial throw exception
						throw new InvalidStageTypeException("Invalid stage type, Time trials do not have checkpoints");
					}
					idCounter++;//increment the id counter
					stage.addCheckPoint(idCounter, CheckpointType.SPRINT, location);//add the checkpoint to the stage
					return idCounter;//return the id of the checkpoint
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}	

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage
				for (CheckPoint checkpoint : stage.getCheckPoints()) {//for each checkpoint
					if (checkpoint.getID() == checkpointId) {//if it's the checkpoint we are looking for
						if (stage.getWaitingForResults()) {//if the stage is waiting for results throw exception
							throw new InvalidStageStateException("Invalid stage state, stage is waiting for results");
						}
						stage.removeCheckPoint(checkpoint.getID());//remove the checkpoint
						return;//return
					}
				}
			}

		}
		throw new IDNotRecognisedException("Checkpoint not found");//throw exception if the checkpoint is not found
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					if (stage.getWaitingForResults()) {//if the stage is waiting for results throw exception
						throw new InvalidStageStateException("Invalid stage state, stage is waiting for results");
					}
					stage.setWaitingForResults(true);//set the stage to waiting for results
					return;//return
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					ArrayList<CheckPoint> checkpoints = stage.getCheckPoints();//get the checkpoints of the stage
					int[] checkpointIds = new int[checkpoints.size()];//create an array with the size of the checkpoints
					int i = 0;
					for (CheckPoint checkpoint : checkpoints) {//for each checkpoint
						checkpointIds[i++] = checkpoint.getID();//store the id in the array
					}
					return checkpointIds;//return the array with the ids
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		for (Team team : teams) {//for each team
			if (team.getName() == name) {//if the name already exists throw exception
				throw new IllegalNameException("illegal name, name already exists");
			}
		}
		if (name == "") {//if the name is empty throw exception
			throw new InvalidNameException("Invalid name, name cannot be empty");
		}
		if (name == null){//if the name is null throw exception
			throw new IllegalArgumentException("invalid name, Name cannot be null");
		}
		if (name.contains(" ")){//if the name contains white spaces throw exception
			throw new IllegalArgumentException("invalid name, Name cannot contain spaces");
		}
		idCounter++;//increment the id counter
		int id = idCounter;//create a new id
		Team team = new Team(id, name, description);//create a new team
		this.teams.add(team);//add the team to the array
		return id;//return the id of the team
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		for (Team team : teams) {//for each team
			if (team.getID() == teamId) {//if it's the team we are looking for
				teams.remove(team);//remove the team
				return;//return
			}
		}
		throw new IDNotRecognisedException("Team not found");//throw exception if the team is not found
	}

	@Override
	public int[] getTeams() {
		int[] teams = new int[this.teams.size()];//create an array with the size of the teams array
		for (int i = 0; i < this.teams.size(); i++) {//for each team
			teams[i] = this.teams.get(i).getID();//store the id in the array
		}
		return teams;//return the array with the ids
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		for (Team team : teams) {//for each team
			if (team.getID() == teamId) {//if it's the team we are looking for
				int[] riders = new int[team.getRiders().size()];//create an array with the size of the riders array
				for (int i = 0; i < team.getRiders().size(); i++) {//for each rider
					riders[i] = team.getRiders().get(i).getID();//store the id in the array
				}
				return riders;//return the array with the ids
			}
		}
		throw new IDNotRecognisedException("Team not found");//throw exception if the team is not found
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		for (Team team : teams) {//for each team
			if (team.getID() == teamID) {//if it's the team we are looking for
				idCounter++;//increment the id counter
				int id = idCounter;//create a new id
				if (name == "") {//if the name is empty throw exception
					throw new IllegalArgumentException("Invalid name, name cannot be empty");
				}
				else if (name == null){//if the name is null throw exception
					throw new IllegalArgumentException("invalid name, Name cannot be null");
				}
				else if (yearOfBirth < 1900){//if the year of birth is less than 1900 throw exception
					throw new IllegalArgumentException("Invalid year of birth, year of birth must be higher then 1900");
				}
				Rider rider = new Rider(id, teamID, name, yearOfBirth);//create a new rider
				team.addRider(rider);//add the rider to the team
				return id;//return the id of the rider
			}
		}
		throw new IDNotRecognisedException("Team not found");//throw exception if the team is not found
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		for (Team team : teams) {//for each team
			for (Rider rider : team.getRiders()) {//for each rider in that team
				if (rider.getID() == riderId) {//if it's the rider we are looking for
					team.getRiders().remove(rider);//remove the rider
					return;//return
				}
			}
		}  
		throw new IDNotRecognisedException("Rider not found");//throw exception if the rider is not found
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
				//<-------------find the rider-------------------->
				Boolean riderFound = false;
				for (Team team : teams) {//for each team
					for (Rider rider : team.getRiders()) {//for each rider in that team
						if (rider.getID() == riderId) {//if it's the rider we are looking for
							riderFound = true;//set the rider found to true
						}
					}
				}
				//<-------------throw exception if rider not found-------------------->
				if (!riderFound){//if the rider wasn't found throw exception
					throw new IDNotRecognisedException("Rider not found");
				}
				//<-------------find the stage---------------------------------------->
				for (Race race : races) {//for each race
					for (Stage stage : race.getStages()) {//for each stage in that race
						if (stage.getID() == stageId) {//if it's the stage we are looking for
							String error = stage.addRiderTimes(riderId, checkpoints);//add the rider times to the stage if there is an error it will return it if not it will return null
							switch (error){
								case "duplicate error"://if the rider already has times for that stage throw exception
									throw new DuplicatedResultException(error);
								case "checkpoint amount error"://if the amount of checkpoints is incorrect throw exception
									throw new InvalidCheckpointTimesException(error);
								case "not waiting for results"://if the stage is not waiting for results throw exception
									throw new InvalidStageStateException(error);
								}
							return;//return if no errors
						}
					}
				}
				//<-------------throw exception if stage not found-------------------->
				throw new IDNotRecognisedException("Stage not found");
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Boolean riderFound = false;//set the rider found to false
		for (Team team : teams) {//for each team
			for (Rider rider : team.getRiders()) {//for each rider in that team
				if (rider.getID() == riderId) {//if it's the rider we are looking for
					riderFound = true;//set the rider found to true
				}
			}
		}
		if (!riderFound){//if the rider wasn't found throw exception
			throw new IDNotRecognisedException("Rider not found");
		}
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage in that race
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					LocalTime[] times = stage.getRiderTimesAndEllapsed(riderId);//get the rider times and the elapsed time
					return times;//return the times
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Boolean riderFound = false;
		for (Team team : teams) {
			for (Rider rider : team.getRiders()) {
				if (rider.getID() == riderId) {
					riderFound = true;
				}
			}
		}
		if (!riderFound){
			throw new IDNotRecognisedException("Rider not found");
		}
		for (Race race : races) {
			for (Stage stage : race.getStages()) {
				if (stage.getID() == stageId) {
					LocalTime adjsutedElapsedTime = stage.getRiderAdjustedElapsed(riderId);
					return adjsutedElapsedTime;
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		Boolean riderFound = false;//set the rider found to false
		for (Team team : teams) {//for each team
			for (Rider rider : team.getRiders()) {//for each rider in that team
				if (rider.getID() == riderId) {//if it's the rider we are looking for
					riderFound = true;//set the rider found to true
				}
			}
		}
		if (!riderFound){//if the rider wasn't found throw exception
			throw new IDNotRecognisedException("Rider not found");
		}
		for (Race race : races) {//for each rider 
			for (Stage stage : race.getStages()) {//for each stage
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					stage.deleteRiderResultsInStage(riderId);//delete the rider results
					return;//return
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage in the race
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					return race.getStageLeaderBoard(stageId);//return the leaderboard of the stage
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
		
		
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		for (Race race : races) {
			for (Stage stage : race.getStages()) {
				if (stage.getID() == stageId) {
					return stage.getAdjustedTimesInTimeOrder();
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		for (Race race : races) {//for each race
			for (Stage stage : race.getStages()) {//for each stage
				if (stage.getID() == stageId) {//if it's the stage we are looking for
					return stage.getPointsInTimeOrder();//return the points in time order
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");//throw exception if the stage is not found
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		for (Race race : races) {
			for (Stage stage : race.getStages()) {
				if (stage.getID() == stageId) {
					return stage.getMountainPointsInTimeOrder();
				}
			}
		}
		throw new IDNotRecognisedException("Stage not found");
	}

	@Override
	public void eraseCyclingPortal() {
		idCounter = 0;//reset the id counter
		teams = null;//set the teams array to null
		races = null;//set the races array to null
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		//try to write to the file
		try(ObjectOutputStream file= new ObjectOutputStream(new FileOutputStream(filename))) 
    	{
    	    file.writeObject(teams);//write the teams array
			file.writeObject(races);//write the races array
			file.close();
    	}
    	catch(IOException IOerror)//catch any IO errors
    	{
    	    throw new IOException("Error while saving");
    	}

	}

	@SuppressWarnings("unchecked")//suppress the unchecked warning
	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		teams = null;//set the teams array to null
		races = null;//set the races array to null
		//try to read from the file
		try(ObjectInputStream file= new ObjectInputStream(new FileInputStream(filename))){
			Object teamData = file.readObject();//read the teams array
			Object raceData = file.readObject();//read the race array

			teams = (ArrayList<Team>) teamData;//convert into array list
			races = (ArrayList<Race>) raceData; //convert into array list
		}
    	catch(IOException e)//catch any IO errors
    	{
    	    throw new IOException("Error while saving");
    	}
		catch(ClassNotFoundException e)//catch any class not found errors
    	{
    	    throw new ClassNotFoundException("Required classes not found in file");
    	}

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		for (Race race : races) {//for each race
			if (race.getName() == name) {//if it's the race we are looking for
				races.remove(race);//remove the race
				return;
			}
		}
		throw new NameNotRecognisedException("Race not found");//throw exception if race wasn't found under the name specified
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getElapsedTimesSortedByAdjustedTimes();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getRidersPtsSortedByAdjustedTimes();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getRidersMountainPtsSortedByAdjustedTimes();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getStageAdjustedLeaderboard();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getPointLeaderboard();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getMountainPointLeaderboard();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

}
