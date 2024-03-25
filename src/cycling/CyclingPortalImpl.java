package cycling;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;

/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortal interface.
 * 
 * @author Diogo Pacheco
 * @version 2.0
 *
 */
public class CyclingPortalImpl implements CyclingPortal {

	LinkedList<Team> teams;
	LinkedList<Race> races;
	private static int idCounter = 0;

	//constructor
	public CyclingPortalImpl() {
		// TODO Auto-generated constructor stub
		teams = new LinkedList<Team>();
	}

	@Override
	public int[] getRaceIds() {
		// TODO Auto-generated method stub\
		int[] raceIds = new int[races.size()];
		int i = 0;
		for(Race race: races){raceIds[i++] = race.getID();}
		return raceIds;
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getName() == name) {
				throw new IllegalNameException("illegal name, name already exists");
			}
		}
		if (name.isEmpty() || name == null || name.length() > 30 ||name.contains(" ")) {
			throw new InvalidNameException("Invalid name, name cannot be null, empty, have more than 30 characters, or have white spaces");
		}
		int id = ++idCounter;
		Race race = new Race(id, name, description);
		this.races.add(race);
		return id;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getDetails();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getID() == raceId) {
				races.remove(race);
				return;
			}
		}
		throw new IDNotRecognisedException("Race not found");

	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getNumberOfStages();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getID() == raceId) {
				return race.getStageIds();
			}
		}
		throw new IDNotRecognisedException("Race not found");
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		if (this.teams.contains(name)) {
			throw new IllegalNameException("illegal name, name already exists");
		}
		if (name.isEmpty()) {
			throw new InvalidNameException("Invalid name, name cannot be empty");
		}
		if (name == null){
			throw new IllegalArgumentException("invalid name, Name cannot be null");
		}
		if (name.contains(" ")){
			throw new IllegalArgumentException("invalid name, Name cannot contain spaces");
		}
		int id = this.teams.size();
		Team team = new Team(id, name, description);
		this.teams.add(team);
		return id;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Team team : teams) {
			if (team.getID() == teamId) {
				teams.remove(team);
				return;
			}
		}
		throw new IDNotRecognisedException("Team not found");
	}

	@Override
	public int[] getTeams() {
		// TODO Auto-generated method stub
		int[] teams = new int[this.teams.size()];
		for (int i = 0; i < this.teams.size(); i++) {
			teams[i] = this.teams.get(i).getID();
		}
		return teams;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Team team : teams) {
			if (team.getID() == teamId) {
				int[] riders = new int[team.getRiders().size()];
				for (int i = 0; i < team.getRiders().size(); i++) {
					riders[i] = team.getRiders().get(i).getID();
				}
				return riders;
			}
		}
		throw new IDNotRecognisedException("Team not found");
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// TODO Auto-generated method stub
		for (Team team : teams) {
			if (team.getID() == teamID) {
				int id = team.getRiders().size();
				if (name.isEmpty()) {
					throw new IllegalArgumentException("Invalid name, name cannot be empty");
				}
				else if (name == null){
					throw new IllegalArgumentException("invalid name, Name cannot be null");
				}
				else if (yearOfBirth < 1900){
					throw new IllegalArgumentException("Invalid year of birth, year of birth must be higher then 1900");
				}
				Rider rider = new Rider(id, teamID, name, yearOfBirth);
				team.addRider(rider);
				return id;
			}
		}
		throw new IDNotRecognisedException("Team not found");
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		for (Team team : teams) {
			for (Rider rider : team.getRiders()) {
				if (rider.getID() == riderId) {
					team.getRiders().remove(rider);
					return;
				}
			}
		}  
		throw new IDNotRecognisedException("Rider not found");
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException,
			InvalidStageStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub
		idCounter = 0;
		teams = null;
		races = null;
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub
		try(ObjectOutputStream file= new ObjectOutputStream(new FileOutputStream(filename)))
    	{
    	    file.writeObject(teams);
			file.writeObject(races);
			file.close();
    	}
    	catch(IOException IOerror)
    	{
    	    throw new IOException("Error while saving");
    	}

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method 
		teams = null;
		races = null;
		try(ObjectInputStream file= new ObjectInputStream(new FileInputStream(filename)))
    	{
    	    teams = (LinkedList<Team>) file.readObject();
			races = (LinkedList<Race>) file.readObject();
    	}
    	catch(IOException e)
    	{
    	    throw new IOException("Error while saving");
    	}
		catch(ClassNotFoundException e)
    	{
    	    throw new ClassNotFoundException("Required classes not found in file");
    	}

	}


	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub
		for (Race race : races) {
			if (race.getName() == name) {
				races.remove(race);
				return;
			}
		}
		throw new NameNotRecognisedException("Race not found");
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
