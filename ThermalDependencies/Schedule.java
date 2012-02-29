package ThermalDependencies;

import java.util.ArrayList;
import java.util.Date;
import java.io.*;
import thermalproject.CPU;
public class Schedule implements Serializable
{

	private CPU[][] cpus;
	private Date timeStamp;
	private Double revenue;

	public Schedule(){
		this.cpus = new CPU[4][10];
		this.timeStamp = new Date();
		this.revenue = 0.00;
	}


	public void setCPUS(CPU[][] cpus) {
		this.cpus = cpus;
	}

	public CPU[][] getCPUS() {
		return cpus;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setRevenue(Double revenue) {
		this.revenue = revenue;
	}

	public Double getRevenue() {
		return revenue;
	}

}
