package templates;

public class VoterCardCoord {

	private int name[][] = new int[2][4];
	private int fatherName[][] = new int[2][4];
	private int sex[][] = new int[2][4];
	private int dobDisplay[][] = new int[2][4];
	private int age[][] = new int [2][4];
	private int voterId[][] = new int[2][4];
	private int address[][] = new int[2][4];
	
	public int[][] getVoterId() {
		return voterId;
	}
	public void setVoterId(int value, int r, int c) {
		this.voterId[r][c] = value;
	}
	public int[][] getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(int value, int r, int c) {
		this.dobDisplay[r][c] = value;
	}
	public int[][] getAge() {
		return age;
	}
	public void setAge(int value, int r, int c) {
		this.dobDisplay[r][c] = value;
	}
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
	public int[][] getSex() {
		return sex;
	}
	public void setSex(int value, int r, int c) {
		this.sex[r][c] = value;
	}
	public int[][] getAddress() {
		return address;
	}
	public void setAddress(int value, int r, int c) {
		this.address[r][c] = value;
	}
	
	
}
