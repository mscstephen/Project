package thermalproject;

/**
 *
 * @author njps2
 */
public class Air {

	double currentTemp;
	int position;
	double originalTemp = 299.16;

	public Air(int posInCpuArrayList, double temp) {
		position = posInCpuArrayList;
		currentTemp = temp;
	}

	public Air(int posInCpuArrayList) {
		position = posInCpuArrayList;
	}

	public double getTemp() {
		return currentTemp;
	}

	public void setTemp(double i) {
		currentTemp = i;
	}
}
