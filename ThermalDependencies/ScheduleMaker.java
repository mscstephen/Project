package ThermalDependencies;

import java.util.ArrayList;
import java.util.HashMap;
import thermalproject.CPU;

public class ScheduleMaker implements Runnable{
	
	private boolean running;

	public ScheduleMaker(){
		this.running = false;
	}

	public void run(Schedule schedule, boolean[][] healthMap, double[][] thermalMap, ArrayList<Job> jobs, HashMap<Integer, Boolean> jobLookUp) {
		this.running = true;
//		do{
//			assignResources(schedule, healthMap, thermalMap, jobs, jobLookUp);
//		}while(this.running = true);
		if (this.running) {
			assignResources(schedule, healthMap, thermalMap, jobs, jobLookUp);
		}

	}
	
	public void assignResources(Schedule schedule, boolean[][] healthMap, double[][] thermalMap, ArrayList<Job> jobs, HashMap<Integer, Boolean> jobLookUp){
		for(int k = 0; k < jobs.size(); k++){
			if(!jobs.get(k).isLocked() && !jobLookUp.containsKey(jobs.get(k))){	
			
				jobs.get(k).lockJob();
				
				for(int i = 0; i < 4; i++){
					for(int j = 0; j < 10; j++){
						
						CPU cpu = schedule.getCPUS()[i][j];
						
						if(!cpu.isLocked() && healthMap[i][j] == true){
							cpu.lockCPU();
							//check if the job won't burn out the computer
							//check if the job will finish on time
							cpu.addAJob(jobs.get(k));
							jobLookUp.put(jobs.get(k).getId(), true);
							cpu.unlockCPU();
							jobs.get(k).unlockJob();
							return;
						}
					}
				}
				jobs.get(k).unlockJob();
			}
		}
		this.running = false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	public boolean isRunning() {
		return running;
	}
	
}
