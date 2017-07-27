package templates;

public class AadharCardCoord {
		
	private int name[][] = new int[2][4];
	private int fatherName[][] = new int[2][4];
	private int yearOfBirth[][] = new int[2][4];
	private int dobDisplay[][] = new int[2][4];
	private int gender[][] = new int[2][4];
	private int aadharNumber[][] = new int[2][4];
	private int address[][] = new int[2][4];
	
	
	public int[][] getName() {
		return name;
	}
	public void setName(int value, int r, int c) {
		this.name[r][c] = value;
	}
	public int[][] getFatherName() {
		return fatherName;
	}
	public void setFatherName(int value, int r, int c) {
		this.fatherName[r][c] = value;
	}
	public int[][] getYearOfBirth() {
		return yearOfBirth;
	}
	public void setYearOfBirth(int value, int r, int c) {
		this.yearOfBirth[r][c] = value;
	}
	public int[][] getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(int value, int r, int c) {
		this.dobDisplay[r][c] = value;
	}
	public int[][] getGender() {
		return gender;
	}
	public void setGender(int value, int r, int c) {
		this.gender[r][c] = value;
	}
	public int[][] getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(int value, int r, int c) {
		this.aadharNumber[r][c] = value;
	}
	public int[][] getAddress() {
		return address;
	}
	public void setAddress(int value, int r, int c) {
		this.address[r][c] = value;
	}
	

}
