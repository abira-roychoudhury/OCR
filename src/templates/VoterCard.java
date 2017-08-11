package templates;

public class VoterCard {
	
	
	private String dobDisplay = "";
	private String age = "";
	private String name = "";
	private String firstName = "";
	private String middleName = "";
	private String lastName = "";
	private String fatherName = "";
	private String sex = "";
	private String voterId = "";
	private VoterCardCoord coordinates = new VoterCardCoord();
	private Address address = new Address();
	
	
	
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getVoterId() {
		return voterId;
	}
	public void setVoterId(String voterId) {
		this.voterId = voterId;
	}
	public VoterCardCoord getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(VoterCardCoord coordinates) {
		this.coordinates = coordinates;
	}
	public String getDobDisplay() {
		return dobDisplay;
	}
	public void setDobDisplay(String dobDisplay) {
		this.dobDisplay = dobDisplay;
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	

}
