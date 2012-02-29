package ThermalDependencies;

import java.util.ArrayList;
import java.util.Date;

public class ParserController
{
	private HealthMapParser hMParser = new HealthMapParser();
	private ThermalMapParser tMParser = new ThermalMapParser();
	private JobParser jParser = new JobParser();
	private ScheduleParser sp = new ScheduleParser();
	private RuleParser rp = new RuleParser();
	private JobCarrier jc = new JobCarrier();

	public ParserController()
	{
	}
	public boolean[][] parseHealthMap(boolean hm[][])
	{
		return (hMParser.parseHealthMap(hm));
	}
	public double[][] parseThermalMap(double tm[][])
	{
		return (tMParser.parseThermalMap(tm));
	}
	public boolean parseJob(Job j)
	{
		//System.out.println("This really shouldn't be used, as I've set the Schedule Parser to go over all jobs in all CPUs");
		return (jParser.parseJob(j));
	}
	public boolean parseSchedule(Schedule s)
	{
		return (sp.parseSchedule(s));
	}
	public int parseRule(int i)
	{
		return (rp.parseRule(i));
	}
	public Schedule provideOldJobs(Schedule old)
	{
		return jc.provideOldJobs(old);
	}
}
