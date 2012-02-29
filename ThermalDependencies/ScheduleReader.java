/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ThermalDependencies;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import thermalproject.CPU;
/**
 *
 * @author fsmm2
 */
public class ScheduleReader {
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


        public ScheduleReader()
    {}
        public void read()
    {

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
        try {
            controller.run();
        } catch (InterruptedException ex) {
            Logger.getLogger(ScheduleReader.class.getName()).log(Level.SEVERE, null, ex);
        }

                CPU[][] outcpu=controller.ntSchedule.getCPUS();
                CPU[] singlearray=new CPU[40];
                int count=0;
                for(int i=0; i<10; i++){
                    for(int j=0; j<4; j++)
        {
                        singlearray[count]=outcpu[j][i];
                        count++;
                    }}

                int[] joblist=new int[40];
                for(int k=0; k<40; k++)
                {
                    joblist[k]=singlearray[k].getJobs().size();
                }




                String outputFilename="schedule.txt";
                try{
       FileWriter writer=new FileWriter(outputFilename);
      //  FileWriter futurewriter=new FileWriter(futureOutputname);
        PrintWriter pw=new PrintWriter(writer);



        for(int a : joblist)
        {
            pw.println(a);


            }
        writer.close();
}
                catch(Exception e){}
}
}
