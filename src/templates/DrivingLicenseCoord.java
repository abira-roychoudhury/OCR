package templates;

public class DrivingLicenseCoord {
	
	private int dobDisplay[][] = new int[2][4];
	private int name[][] = new int[2][4];
	private int middleName[][] = new int[2][4];
	private int address[][] = new int[2][4];
	private int pin[][] = new int[2][4];
	private int bloodGroup[][] = new int[2][4];
	
	
	public int[][] getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(int value, int r, int c) {
		this.dobDisplay[r][c] = value;
	}
	public int[][] getName() {
		return name;
	}
	public void setName(int value, int r, int c) {
		this.name[r][c] = value;
	}
	public int[][] getMiddleName() {
		return middleName;
	}
	public void setMiddleName(int value, int r, int c) {
		this.middleName[r][c] = value;
	}
	public int[][] getAddress() {
		return address;
	}
	public void setAddress(int value, int r, int c) {
		this.address[r][c] = value;
	}
	public int[][] getPin() {
		return pin;
	}
	public void setPin(int value, int r, int c) {
		this.pin[r][c] = value;
	}
	public int[][] getBloodGroup() {
		return bloodGroup;
	}
	public void setBloodGroup(int value, int r, int c) {
		this.bloodGroup[r][c] = value;
	}
	
	
	
	

}
