package ThermalDependencies;

import java.util.ArrayList;


public class SchedulerController {

	// some variable declarations
	// declare the Parser, Scheduler and Translator
	ParserController parser;
	ScheduleController iScheduler;
	Translator translator;
	
	// declare the fresh resources
	ArrayList<Job> jobs;
	Schedule prevSchedule;
	boolean[][] healthMap;
	double[][] thermalMap;
	int rule;

	// declare the parsed resources
	ArrayList<Job> pJobs;
	Schedule pPrevSchedule;
	boolean[][] pHealthMap = null;
	double[][] pThermalMap = null;
	boolean hMapOK;
	boolean tMapOK;
	int pRule;
	
	// declare the non-translated schedule and the translated schedule
	// TODO decide the schedule data type
	Schedule ntSchedule;
	Schedule tSchedule;
	
	boolean active = false;
	
	// different finish conditions
	boolean parseFinished = false;
	boolean scheduleFinished = false;
	boolean translateFinished = false;
	boolean terminated = false;
	
	
	public SchedulerController(ArrayList<Job> jobs, Schedule prevSchedule, boolean[][] healthMap,
			double[][] thermalMap, int rule) {
		// create the Parser, InnerScheduler and Translator
		parser = new ParserController();
		iScheduler = new ScheduleController(prevSchedule, healthMap, thermalMap);
		translator = new Translator();

		this.healthMap = healthMap;
		this.thermalMap = thermalMap;
	
		this.jobs = jobs;
		
		this.prevSchedule = prevSchedule;
//		CPU[][] tmpCpus = prevSchedule.getCPUS();
//		for(int i = 0; i < 4; i++){
//			for(int j = 0; j < 10; j++){
//				System.out.println("tmpCpus: "+i+": "+j+": "+tmpCpus[i][j].isLocked());
//			}
//		}
		
		this.rule = rule;
		
		this.pHealthMap = null;
		this.pThermalMap = null;
		
		hMapOK = false;
		tMapOK = false;
	}
	
	// load required data from the database
	public void loadDataFromDB() {
		healthMap = healthMap;
		thermalMap = thermalMap;
	
		jobs = jobs;
		prevSchedule = prevSchedule;
		rule = rule;
		
		pHealthMap = null;
		pThermalMap = null;
		
		hMapOK = false;
		tMapOK = false;
		
	}
	
	// call the parser to parse everything
	public void parse() {
		pHealthMap = parser.parseHealthMap(healthMap);
		pThermalMap = parser.parseThermalMap(thermalMap);
		
		//TODO go through the whole arraylist and check each job
		for (Job job : jobs) {
			parser.parseJob(job);
		}
		
		if (parser.parseSchedule(prevSchedule)) {
			pPrevSchedule = prevSchedule;
		}
		
		pPrevSchedule = parser.provideOldJobs(prevSchedule);
		pRule = parser.parseRule(rule);
		
		parseFinished = true;
	}
	
	// call the scheduler to schedule
	public void schedule() {
//		iScheduler.setSchedule(pPrevSchedule); // TODO should really pass the parsed previous schedule
		iScheduler.setSchedule(prevSchedule);
		iScheduler.setHealthMap(pHealthMap);
		iScheduler.setThermalMap(pThermalMap);
		
		ntSchedule = iScheduler.assignJobs(jobs);
		

//		System.out.println("getTimeStamp: "+ntSchedule.getTimeStamp());
//		CPU[][] tmpCpus = ntSchedule.getCPUS();
//		for(int i = 0; i < 4; i++){
//			for(int j = 0; j < 10; j++){
//				System.out.println("tmpCpus: "+i+": "+j+": "+tmpCpus[i][j].getJobs().size());
//				ArrayList<Job> tmpJobs = tmpCpus[i][j].getJobs();
//				for (Job job : tmpJobs) {
//					System.out.println("    tmpJobs: id:"+job.getId()
//							+" duration:"+job.getDuration()
//							+"priority:"+job.getPriority()
//							+"pRate:"+job.getProcessingRate()
//							+"revenue:"+job.getRevenue());
//				}
//			}
//		}
		
		scheduleFinished = true;
//		System.out.println("scheduleFinished? "+scheduleFinished);
	}
	
	// call the translator to translate
	public void translate() {
		tSchedule = translator.translate(ntSchedule);
		
		translateFinished = true;
		
		active = false;
	}
	
	// check the trigger for activation
	public void checkTrigger() {
		// TODO need to check real triggers
		boolean triggerOn = true;
		if (triggerOn) {
			active = true;
		}
	}

	public void run() throws InterruptedException {
//		while (true) {
			this.runIn10Secs();
//			this.runNormal();
//		}
	}
	
	public void runNormal() throws InterruptedException {
//		while (true && !active) {
//			long currTime = System.currentTimeMillis();
//			// check trigger every 5 seconds
//			if (System.currentTimeMillis() - currTime  >= 5000) {
//				checkTrigger();
//			}
//		}
		// TODO need to do more specific tasks rather than just call them. or maybe the parser can handle all the work by just call one method of it once
		this.parse();
		this.schedule();
//		this.translate();
//		Thread.sleep(5000); // test to see if the runIn10Secs will terminate with in 10 seconds
//		Thread.sleep(10000); // test to see if the runIn10Secs will terminate with in 10 seconds
//		if (parseFinished && scheduleFinished && translateFinished) {
//		if (parseFinished && translateFinished) {
//		if (scheduleFinished) {
			terminated = true;
			System.out.println("[DEBUG] runNormal TERMINATED");
//		}
		
		// TODO update/push the final translated schedule into the data base, or send to other teams
		// translator should do the update/push
		
	}
	
	public void runIn10Secs() {
		// should finish everything with in 10 seconds.
		Thread thread = new Thread(){
			public void run() {
				SchedulerController controller = new SchedulerController(jobs, prevSchedule, healthMap, thermalMap, rule);
				try {
					controller.runNormal();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (controller.terminated) {
					System.out.println("[DEBUG] Controller - runIn10Secs\n\tcontroller.terminated: " + controller.terminated);
					pHealthMap = controller.pHealthMap;
					pThermalMap = controller.pThermalMap;
					pJobs = controller.pJobs;
					pPrevSchedule = controller.pPrevSchedule;
					ntSchedule = controller.ntSchedule;
					tSchedule = controller.tSchedule;
					terminated = controller.terminated;
					System.out.println("CHECK if terminated: "+terminated);
				}
			}
		};
		thread.start();
		long start = System.currentTimeMillis();
		long end = start + (long)(8*1000); // leave few seconds to do something else (sending data, deal with database etc.)
		long counter = start;
		while (!terminated) {
			if (System.currentTimeMillis() - counter >= 1000) {
				counter = System.currentTimeMillis();
				System.out.println(counter);
			}
			if (System.currentTimeMillis() >= end) {
				terminated = true;
				System.out.println(System.currentTimeMillis());
				System.out.println("[DEBUG] Controller - runIn10Secs\n\tTime Exceeded! System Going to Stop.");
				thread.stop();
			}
		}
		System.out.println("[DEBUG] Controller - runIn10Secs\n\tSystem Terminated because 'terminated == true'\n");
		
	}

}