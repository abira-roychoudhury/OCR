package templates;

import java.util.Calendar;

public class AadharCard {
	
	private String name = "";
	private String fatherName = "";
	private int yearOfBirth = 0;
	private Calendar dob;
	private String dobDisplay = "";
	private String gender = "";
	private String aadharNumber = "";
	private Address address = new Address();
	private AadharCardCoord coordinates = new AadharCardCoord();
		
	

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public AadharCardCoord getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(AadharCardCoord coordinates) {
		this.coordinates = coordinates;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
    public int getYearOfBirth(){
		return this.yearOfBirth;
	}
	
	public void setYearOfBirth(int yearOfBirth){
		this.yearOfBirth = yearOfBirth;
	}

	public String getGender(){
		return this.gender;
	}
	
	public void setGender(String gender){
		this.gender = gender;
	}

	public String getAadharNumber(){
		return this.aadharNumber;
	}
	
	public void setAadharNumber(String aadharNumber){
		this.aadharNumber = aadharNumber;
	}

	public Calendar getDob() {
		return dob;
	}

	public void setDob(Calendar dob) {
		this.dob = dob;
	}
	
	public String getDobDisplay() {
		return dobDisplay;
	}

	public void setDobDisplay(String dobDisplay) {
		this.dobDisplay = dobDisplay;
	}
	
	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	
}
