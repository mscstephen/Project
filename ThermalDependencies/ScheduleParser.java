package ThermalDependencies;

import java.util.ArrayList;
import thermalproject.CPU;

public class ScheduleParser
{
	public ScheduleParser()
	{
	}
	JobParser jParse;

	//Perform some basic checking to ensure that the prime CPU array
	//is not null and that it has members, both should be true.
	public boolean parseSchedule(Schedule s)
	{
		jParse = new JobParser();
		//if anything is Null, return false
		if ((s.getCPUS() == null) || (s.getCPUS().length == 0))
		{
			return false;
		}
		//otherwise run the job parser on all jobs of all CPUs
		// in the schedule.
		int i1 = 0;
		CPU c[][] = s.getCPUS();
		if (c.length != 4) return false;
		while (i1 < 4) // EDITED by DAN
		{
			CPU temp[] = c[i1];
			if (temp.length != 10) return false;
			int i = 0;
			while (i < temp.length)
			{
				//Check the jobs list for errors.
				int j = 0;
				ArrayList<Job> aj = temp[i].getJobs();
				while (j < aj.size())
				{
					if (jParse.parseJob(aj.get(j)) == false) return false;
				}

				i++;
			}
			i1++;
		}

		return true;
	}
}