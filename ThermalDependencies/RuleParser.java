package ThermalDependencies;

class RuleParser
{
	public RuleParser(){}

	public int parseRule(int in)
	{
		if (in == 1){return 75;}
		if (in == 2){return 70;}
		if (in == 3){return 65;}
		if (in == 4){return 60;}
		if (in == 5){return 55;}

		return 50;
	}
}