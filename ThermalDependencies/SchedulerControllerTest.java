package ThermalDependencies;

import java.util.ArrayList;
import java.util.Random;
import thermalproject.CPU;
import org.junit.Test;

import junit.framework.TestCase;


public class SchedulerControllerTest extends TestCase {

	// variables
	SchedulerController controller;
	
	CPU[][] cpus;
	ArrayList<Job> jobs;
	Schedule prevSchedule;
	boolean[][] healthMap;
	double[][] thermalMap;
	int rule;

	ArrayList<Job> pJobs;
	Schedule pPrevSchedule;
	boolean[][] pHealthMap = null;
	double[][] pThermalMap = null;
	boolean hMapOK;
	boolean tMapOK;
	int pRule;

	Schedule ntSchedule;
	Schedule tSchedule;
	
	boolean cTerminated;

	public void setUp() throws Exception {
		jobs = new ArrayList<Job>();
		for (int i = 0; i < 10; i++) {
			Job job = new Job();
			job.setId(i);
			jobs.add(job);
		}
		
		boolean[][] healthMap = new boolean[4][10];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 10; j++){
				healthMap[i][j] = true;
//				System.out.println(i+": "+j+": "+healthMap[i][j]);
			}
		}
		
		double[][] thermalMap = new double[4][10];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 10; j++){
				thermalMap[i][j] = 50.00;
			}
		}
		// general temperatures
//		double[][] thermalMap = new double[][]{{}};
		
		CPU[][] cpus = new CPU[4][10];
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 10; j++){
				cpus[i][j] = new CPU();
			}
		}

		prevSchedule = new Schedule();
		prevSchedule.setCPUS(cpus);
		

		Random rand = new Random();
		rule = rand.nextInt(6)+1;
		System.out.println("rule: "+rule);

		pHealthMap = null;
		pThermalMap = null;
		pJobs = null;
		pPrevSchedule = null;
		
		ntSchedule = null;
		tSchedule = null;
		
		controller = new SchedulerController(jobs, prevSchedule, healthMap, thermalMap, rule);
		cTerminated = false;
	}

//	@Test
//	public void test() {
////		testRunIn10Secs();
////		testRunningTime();
//		
////		run();
//		
////		testParser();
////		testScheduler();
////		testTranslator();
//		
//		fail("Not yet implemented");
//	}

	public void testRunIn10Secs() {
		controller.runIn10Secs();

		System.out.println("getTimeStamp: "+controller.ntSchedule.getTimeStamp());
		CPU[][] tmpCpus = controller.ntSchedule.getCPUS();
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 10; j++){
				System.out.println("tmpCpus: "+i+": "+j+": "+tmpCpus[i][j].getJobs().size());
				ArrayList<Job> tmpJobs = tmpCpus[i][j].getJobs();
				for (Job job : tmpJobs) {
					System.out.println("    tmpJobs: id:"+job.getId()
							+" duration:"+job.getDuration()
							+"priority:"+job.getPriority()
							+"pRate:"+job.getProcessingRate()
							+"revenue:"+job.getRevenue());
				}
			}
		}

//		assertTrue(controller.pHealthMap == pHealthMap);
//		assertTrue(controller.pThermalMap == pThermalMap);
//		assertTrue(controller.pJobs == pJobs);
//		assertTrue(controller.pPrevSchedule == pPrevSchedule);
//
//		assertTrue(controller.ntSchedule == ntSchedule);
//		
//		assertTrue(controller.tSchedule == tSchedule);
	}

	/*
	@Test
	public void testRunningTime(){
//		Thread thread = new Thread() {
//			public void run() {
//				controller.runIn10Secs();
//				System.out.println(controller.parseFinished);
//				System.out.println(controller.scheduleFinished);
//				System.out.println(controller.translateFinished);
//				if (controller.terminated) {
//					cTerminated = true;
//					System.out.println("Controller already terminated.");
//				}
//			}
//		};
//		thread.start();
		
//		controller.runIn10Secs();

		try {
			controller.run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (controller.terminated) {
			cTerminated = true;
			System.out.println("Controller already terminated.");
		}
		long start = System.currentTimeMillis();
		long end = System.currentTimeMillis();
		long counter = start;
		while (!cTerminated) {
//		while (!controller.terminated) {
			end = System.currentTimeMillis();
			if (System.currentTimeMillis() - counter >= 1000) {
				counter = System.currentTimeMillis();
				System.out.println("end: "+end+" start: "+start+" end-start: "+(end-start));
				System.out.println("while loop: controller.terminated: "+controller.terminated);
				System.out.println("while loop: cTerminated: "+cTerminated);
			}
		}
		System.out.println("AFTER WHILE:");
		System.out.println("end: "+end+" start: "+start+" end-start: "+(end-start));
		System.out.println("end-start <= 10000 : "+ (end-start <= 10000));
		assertTrue(end-start <= 10000);
		System.out.println("========================================\n\n\n");
//		cTerminated = false;
	}
	*/

	/*
	@Test
	public void testParser() {
		controller.parse();

		assertTrue(controller.pHealthMap == pHealthMap);
		assertTrue(controller.pThermalMap == pThermalMap);
		assertTrue(controller.pJobs == pJobs);
		assertTrue(controller.pPrevSchedule == pPrevSchedule);
	}
	*/

	/*
	@Test
	public void testScheduler() {
		controller.schedule();

		assertTrue(controller.ntSchedule == ntSchedule);
	}
	*/

	/*
	@Test
	public void testTranslator() {
		controller.translate();

		assertTrue(controller.tSchedule == tSchedule);
	}
	*/

	/*
	@Test
	public void run() {
		controller.run();
		
		assertTrue(controller.pHealthMap == pHealthMap);
		assertTrue(controller.pThermalMap == pThermalMap);
		assertTrue(controller.pJobs == pJobs);
		assertTrue(controller.pPrevSchedule == pPrevSchedule);

		assertTrue(controller.ntSchedule == ntSchedule);
		
		assertTrue(controller.tSchedule == tSchedule);
	}
	*/


}
