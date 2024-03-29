package thermalproject;

import java.util.*;
import java.io.*;
import ThermalDependencies.*;

public class Main {

	public static void main(String[] args) {
		DataCenter data = new DataCenter();
//STEP1: GET THE COOLING PLAN AND INTENSITY PLAN.generated_cooling_plan SHOULD BE THE FIRST COOLING PLAN.
		try {
			//will be replaced with drawing from database
			ScheduleReader reader = new ScheduleReader();
			System.out.println("c");
			reader.read();
			String inputFilename = "generated_cooling_plan.txt";
			FileInputStream fstream = new FileInputStream(inputFilename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			int i = 0;
			int[] array = new int[4];
			int[] intensityScheduling = new int[40];
			while ((strLine = br.readLine()) != null) {
				int parsedCooling = Integer.parseInt(strLine);
				array[i] = parsedCooling;
				i++;
			}

			//This code requires input from the scheduling team.
    /*        String inputSchedule = "schedule.txt";
			FileInputStream sstream = new FileInputStream(inputSchedule);
			DataInputStream sin = new DataInputStream(sstream);
			BufferedReader sr = new BufferedReader(new InputStreamReader(sin));
			String sLine;
			int j = 0;
			while ((sLine = sr.readLine()) != null) {
			int jobs = Integer.parseInt(sLine);
			//assume all jobs take 10% CPU processing power for the 10 secs of an epoch.
			intensityScheduling[j] = jobs * 10;
			j++;
			}
			
			for (int a : intensityScheduling) {
			System.out.println(a);
			}
			
			 */
			data.updateCooling(array);
//data.showCooling();
			data.setIntensityPlan(intensityScheduling);
//set received intensity plan to current intensity plan.
		} catch (Exception e) {
			System.out.println(e);
		}
		String outputFilename = "thermalmap.txt";
		String futureOutputname = "futurethermal.txt";
		try {
			//will be writing to the database.
			//IMPORTANT: will need to write temporary entries under 4 headings based on one of UP TO 4 schedules per system epoch.
			//based on trigger from optimization, commit to one of these four thermal maps. then and only then should the count be incremented.
			FileWriter writer = new FileWriter(outputFilename);
			FileWriter futurewriter = new FileWriter(futureOutputname);
			PrintWriter pw = new PrintWriter(writer);
			PrintWriter pwf = new PrintWriter(futurewriter);
			DataCenter future = new DataCenter();
//PLACEHOLDER. should update array with futureCooling, not yet getting to it.
			data.updateCooling(data.coolingPlan);

			data.runArray(1);
//"1" is the number of epochs the simulator runs for.
			data.showIntensityPlan();
			ArrayList f;
			f = data.getTempArray();
			for (Object q : f) {
				pw.println(q);
			}
			writer.close();
			future = data;
			future.predictArray(30);
			f = future.getTempArray();
			for (Object q : f) {
				pwf.println(q);
			}
			futurewriter.close();

		} catch (Exception i) {
			System.out.println(i);
		}
	}
}
