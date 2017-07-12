package templates;

public class PanCardCoord {
	
	private int name[][] = new int[2][4];
	private int fatherName[][] = new int[2][4];
	private int dobDisplay[][] = new int[2][4];
	private int panNumber[][] = new int[2][4];
	
	
	
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
	public int[][] getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(int value, int r, int c) {
		this.dobDisplay[r][c] = value;
	}
	public int[][] getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(int value, int r, int c) {
		this.panNumber[r][c] = value;
	}
	
	
	

}
