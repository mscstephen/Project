package ThermalDependencies;

//import java.util.ArrayList;
import java.util.Date;

public class JobParser
{
	public JobParser(){}

	public boolean parseJob(Job j)//checks to make sure an incoming Job is not null.
	//Will also do some other checking
	//module will return false if an error is found.
	{
		if (j == null)
		{
			return false;
		}
		if (j.getId() == 0)
		{
			return false;
		}
		if (j.getTimeStamp() == null)
		{
			return false;
		}
		if (j.getDuration() == 0)
		{
			return false;
		}
		if ((j.getRevenue() == 0) || j.getRevenue() == null)
		{
			return false;
		}
		if ((j.getProcessingRate() == 0) || j.getProcessingRate() == null)
		{
			return false;
		}
		if (j.getPriority() == 0)
		{
			return false;
		}
		return true;
	}
}