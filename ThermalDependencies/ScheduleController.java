package ThermalDependencies;

import java.util.ArrayList;
import java.util.HashMap;


public class ScheduleController {
	
	private Schedule schedule;
	private Schedule schedulePrevious;
	private boolean[][] healthMap;
	private double[][] thermalMap;
	private HashMap<Integer, Boolean> jobLookUp;
	private ArrayList<Job> unassignedJobs;
	
	public ScheduleController(Schedule schedule, boolean[][] healthMap, double[][] thermalMap){
		this.schedule = schedule;
		this.healthMap = healthMap;
		this.thermalMap = thermalMap;
		jobLookUp = new HashMap<Integer, Boolean>();
		unassignedJobs = new ArrayList<Job>();
	}
	
	public Schedule assignJobs(ArrayList<Job> jobs){
		ScheduleMaker firstThread = new ScheduleMaker();
		firstThread.run(this.schedule, this.healthMap, this.thermalMap, jobs, jobLookUp);
		ScheduleMaker secondThread = new ScheduleMaker();
		secondThread.run(this.schedule, this.healthMap, this.thermalMap, jobs, jobLookUp);
		
//		while(firstThread.isRunning() | secondThread.isRunning()){}
		
		ScheduleMaker thirdThread = new ScheduleMaker();
		if(allJobsAreAssigned(jobs)){
			thirdThread.run(this.schedule, this.healthMap, this.thermalMap, unassignedJobs, jobLookUp);
		}
		
//		while(thirdThread.isRunning()){}

		return this.schedule;
	}
	
	public boolean allJobsAreAssigned(ArrayList<Job> jobs){
		
		boolean notAssigned = false;
		
		for(int k = 0; k < jobs.size(); k++){
			if(!jobLookUp.containsKey(jobs.get(k))){
				unassignedJobs.add(jobs.get(k));
				notAssigned = true;
			}
		}
		
		return notAssigned;
		
	}

	public void setSchedule(Schedule schedule) {
		this.schedule = schedule;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public void setHealthMap(boolean[][] healthMap) {
		this.healthMap = healthMap;
	}

	public boolean[][] getHealthMap() {
		return healthMap;
	}

	public void setThermalMap(double[][] thermalMap) {
		this.thermalMap = thermalMap;
	}

	public double[][] getThermalMap() {
		return thermalMap;
	}

}
