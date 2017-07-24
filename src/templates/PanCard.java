package templates;

import java.util.Calendar;

public class PanCard {
	
	private String name = "";
	private String fatherName = "";
	private Calendar dob;
	private String dobDisplay = "";
	private String panNumber = "";
	private PanCardCoord coordinates = new PanCardCoord();
	
	public PanCardCoord getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(PanCardCoord coordinates) {
		this.coordinates = coordinates;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFatherName() {
		return fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	public Calendar getDob() {
		return dob;
	}
	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(String dobDisplay) {
		this.dobDisplay = dobDisplay;
	}
	
}
