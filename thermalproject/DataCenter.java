package thermalproject;

/**
*
* @author fsmm2
*/
import java.util.*;

public class DataCenter {

	int count = 0;
	int[] coolingPlan = {0, 0, 0, 0};
	//cooling plan from optimization. Not sure of exact format
	double ambientRoomTemp=299.16;
	double aggregateTemp;
	int[] futureCooling;
        //int array for intensityPlan is most definately PLACEHOLDER
	int[] intensityPlan;
	String[] racks = {"a", "b", "c", "d"};
	//enum char[] better suited for racks. don't know how.
	ArrayList<ThermalCPU> list = new ArrayList<ThermalCPU>();
	double[] s;
        Air[] airArray=new Air[40];


	public DataCenter() {


                    for(int i=0; i<40; i++)
            {
            airArray[i]=new Air(i+1);
            }


    	int j = 0;
    	while (j < 4) {
        	int i = 1;
        	String current = racks[j];
        	while (i < 11) {
            	//TESTING purposes: one ThermalCPU with excessive heat PLACEHOLDER: testing methods
            	if (i == 3 && j == 1) {
                	ThermalCPU name = new ThermalCPU(i, current, 343.15, 0, true);
                	i++;
                	list.add(name);
            	} else {
                	ThermalCPU name = new ThermalCPU(i, current);
                	i++;
                	list.add(name);
            	}


        	}
        	j++;
    	}
	}

	public void showTempArray() //need to add useful getTempArray() PLACEHOLDER: stub
	{
    	for (ThermalCPU a : list) {
        	System.out.println(a.getName() + ": " + (a.getTemp() - 273.15));
    	}
	}

	public ArrayList getTempArray() {
    	ArrayList array = new ArrayList();
    	for (ThermalCPU a : list) {
        	float temp = (float) (a.getTemp() - 273.15);
        	array.add(temp);
    	}
    	return array;



	}

	public void showIntensityPlan() //need to add useful getIntensityArray() PLACEHOLDER: stub
	{
    	for (ThermalCPU a : list) {
        	System.out.println(a.getName() + ": " + a.getIntensity());
    	}
	}

        public void setIntensityPlan(int[] plan)
    {
            intensityPlan=plan;
        }


	public ArrayList<String> getHotspots() //should also timestamp array, as may runs will be taken
	{
    	ArrayList<String> stringlist = new ArrayList<String>();
    	for (ThermalCPU a : list) {
        	double temp = a.getTemp();
        	//temp 343.15 is a PLACEHOLDER: group decision
        	if (temp > 343.15) {
            	String hot = "!" + a.getName() + " : " + (a.getTemp() - 273.15) + "!";
            	stringlist.add(hot);
        	}

    	}
    	return stringlist;

	}

	public void showHotspots() {
    	ArrayList<String> secondstring = new ArrayList<String>();
    	for (ThermalCPU a : list) {
        	double temp = a.getTemp();
        	if (temp > 343.15) {
            	String hot = "!" + a.getName() + " : " + (a.getTemp() - 273.15) + "!";
            	secondstring.add(hot);
        	}

    	}
    	for (String a : secondstring) {
        	System.out.println(a);
    	}

	}

  public float getRoomTemp() {
   	for (int p = 0; p + 1 < list.size(); p++) {
       	Air airAtCpu = new Air(p);
       	double temp = airAtCpu.getTemp();
       	aggregateTemp = aggregateTemp + temp;
   	}
   	double cpuAirTemp = aggregateTemp/40.0;
   	ambientRoomTemp = (cpuAirTemp + ambientRoomTemp)/2.0;
   	ambientRoomTemp = ambientRoomTemp - 273.16;
   	float roomTemp = (float) ambientRoomTemp;
   	return roomTemp;
	}


	public void updateArray(int[] cooling) {
    	ThermalCPU current = new ThermalCPU();
    	ThermalCPU next = new ThermalCPU();

    	for (int p = 0; p + 1 < list.size(); p++) {
        	Air airAroundNext=airArray[p];
current=list.get(p);
current.setIntensity(intensityPlan[p]);
        	if (p < 11) {
            	if (current.getPos() != 10) {
                	//we will need middleware to get schedule's intensity plan from the database
                	//and calculate it into an average intensity. INTEGRATION PLACEHOLDER
                	//get the intensity from the intensityPlan[p].
                	next = list.get(p + 1);
                	current.run(cooling[0], next, airAroundNext);
                	list.set(p, current);
                	list.set(p + 1, next);
            	} else {
                	current.run(cooling[0]);
                	list.set(p, current);
            	}

        	}
        	if (10 < p && p < 21) {
            	if (current.getPos() != 20) {
                	next = list.get(p + 1);
                	current.run(cooling[1], next, airAroundNext);
                	list.set(p, current);
                	list.set(p + 1, next);
            	} else {
                	current.run(cooling[1]);
                	list.set(p, current);
            	}
        	}


        	if (20 < p && p < 31) {

            	if (current.getPos() != 30) {
                	next = list.get(p + 1);
                	current.run(cooling[2], next, airAroundNext);
                	list.set(p, current);
                	list.set(p + 1, next);
            	} else {
                	current.run(cooling[2]);
                	list.set(p, current);
            	}
        	}
        	if (30 < p) {
            	if (current.getPos() != 40) {
                	next = list.get(p + 1);
                	current.run(cooling[3], next, airAroundNext);
                	list.set(p, current);
                	list.set(p + 1, next);
            	} else {
                	current.run(cooling[3]);
                	list.set(p, current);
            	}
        	}
        	//run each CPUs run() function to update the array. Will need to be certain runs from logically
        	//lowest ThermalCPU to highest. Need to add rule for topmost CPUs and separate out racks PLACEHOLDER: improve
    	}
	}

	public void showArrayLength() {
    	System.out.println(list.size());
	}

	public void runArray(int i) {
    	for (int j = 0; j < i; j++) {
        	if (count <= 30) {
            	this.updateArray(coolingPlan);
            	count++;
        	} else {
            	coolingPlan = futureCooling;
            	this.updateArray(coolingPlan);
            	count = 1;
        	}

    	}
    	// this.showTempArray();

	}

	public void predictArray(int i)
	{
    	for(int j=0; j<i; j++)
    	this.updateArray(coolingPlan);
	}

	public void HotspotDisplay() {
    	ArrayList<String> hotspots = this.getHotspots();

    	for (String a : hotspots) {
        	System.out.println(a);
    	}
	}
//testing PLACEHOLDER. updateCooling should update futureCooling
	public void updateCooling(int[] a) {
    	coolingPlan = a;
	}

	public void showCooling()
	{
    	System.out.println(this.coolingPlan[0]);
    	System.out.println(this.coolingPlan[1]);
    	System.out.println(this.coolingPlan[2]);
    	System.out.println(this.coolingPlan[3]);
	}

//count defines the time in the datacenter. at 30, i.e. 5 minute mark, new cooling plan is implemented and count is reset.
	public int getCount()
	{
    	return count;
	}

}