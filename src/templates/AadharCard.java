package templates;

public class AadharCard {
	
	private String firstName;
	private String lastName;
	private String middleName;
	private int yearOfBirth;
	private boolean gender;
	private String aadharNumber;
	private String address;

	public String getFirstName(){
		return this.firstName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName(){
		return this.lastName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getMiddleName(){
		return this.middleName;
	}
	
	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public int getYearOfBirth(){
		return this.yearOfBirth;
	}
	
	public void setYearOfBirth(int yearOfBirth){
		this.yearOfBirth = yearOfBirth;
	}

	public boolean getGender(){
		return this.gender;
	}
	
	public void setGender(boolean gender){
		this.gender = gender;
	}

	public String getAadharNumber(){
		return this.aadharNumber;
	}
	
	public void setAadharNumber(String aadharNumber){
		this.aadharNumber = aadharNumber;
	}

	public String getAddress(){
		return this.address;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	

}
