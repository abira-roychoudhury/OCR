package templates;

public class ITReturnCoord {
	
	private int panNumber[][] = new int[2][4];
	private int aadharNumber[][] = new int[2][4];
	private int assessmentYear[][] = new int[2][4];
	private int name[][] = new int[2][4];
	private int address[][] = new int[2][4];
	private int state[][] = new int[2][4];
	private int city[][] = new int[2][4];
	private int pin[][] = new int[2][4];
	private int status[][] = new int[2][4];
	private int designationOfAO[][] = new int[2][4];
	private int originalRevised[][] = new int[2][4];
	private int eFillingAckNumber[][] = new int[2][4];
	private int eFillingDate[][] = new int[2][4];
	private int grossTotalIncome[][] = new int[2][4];
	
	
	
	public int[][] getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(int value, int r, int c) {
		this.panNumber[r][c] = value;
	}
	public int[][] getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(int value, int r, int c) {
		this.aadharNumber[r][c] = value;
	}
	public int[][] getAssessmentYear() {
		return assessmentYear;
	}
	public void setAssessmentYear(int value, int r, int c) {
		this.assessmentYear[r][c] = value;
	}
	public int[][] getName() {
		return name;
	}
	public void setName(int value, int r, int c) {
		this.name[r][c] = value;
	}
	public int[][] getAddress() {
		return address;
	}
	public void setAddress(int value, int r, int c) {
		this.address[r][c] = value;
	}
	public int[][] getState() {
		return state;
	}
	public void setState(int value, int r, int c) {
		this.state[r][c] = value;
	}
	public int[][] getCity() {
		return city;
	}
	public void setCity(int value, int r, int c) {
		this.city[r][c] = value;
	}
	public int[][] getPin() {
		return pin;
	}
	public void setPin(int value, int r, int c) {
		this.pin[r][c] = value;
	}
	public int[][] getStatus() {
		return status;
	}
	public void setStatus(int value, int r, int c) {
		this.status[r][c] = value;
	}
	public int[][] getDesignationOfAO() {
		return designationOfAO;
	}
	public void setDesignationOfAO(int value, int r, int c) {
		this.designationOfAO[r][c] = value;
	}
	public int[][] getOriginalRevised() {
		return originalRevised;
	}
	public void setOriginalRevised(int value, int r, int c) {
		this.originalRevised[r][c] = value;
	}
	public int[][] geteFillingAckNumber() {
		return eFillingAckNumber;
	}
	public void seteFillingAckNumber(int value, int r, int c) {
		this.eFillingAckNumber[r][c] = value;
	}
	public int[][] geteFillingDate() {
		return eFillingDate;
	}
	public void seteFillingDate(int value, int r, int c) {
		this.eFillingDate[r][c] = value;
	}
	public int[][] getGrossTotalIncome() {
		return grossTotalIncome;
	}
	public void setGrossTotalIncome(int value, int r, int c) {
		this.grossTotalIncome[r][c] = value;
	}
	
	
}
