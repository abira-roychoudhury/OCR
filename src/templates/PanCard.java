package templates;

import java.util.Calendar;

public class PanCard {
	
	private String name = "";
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
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
		name = name.replace(".", " ");
		String nameArray[] = name.split(" ", 3);
		
		if(nameArray.length == 1)
			this.setFirstName(nameArray[0]);
		
		else if(nameArray.length == 2){
			this.setFirstName(nameArray[0]);
			this.setLastName(nameArray[1]);
		}
		else{
			this.setFirstName(nameArray[0]);
			this.setMiddleName(nameArray[1]);
			this.setLastName(nameArray[2]);
		}		
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
