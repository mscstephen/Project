package ThermalDependencies;

import java.util.ArrayList;
import thermalproject.CPU;

public class JobCarrier
{
	public JobCarrier(){}

	public Schedule provideOldJobs(Schedule old)
	{
		Schedule newSched = new Schedule();
		CPU cpulist[][] = old.getCPUS();
		for (int i=0; i < cpulist.length; i++)
		{
			for (int j=0; j < cpulist[i].length; j++)
			{
				ArrayList<Job> temp = cpulist[i][j].getJobs();
				for (int k=0; k < temp.size(); k++)
				{
					/*
					decide whether to carry an old job into the new schedule
					*/
				}
			}
		}


		return newSched;
	}
}