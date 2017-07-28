package templates;

public class VoterCard {
	
	
	private String dobDisplay = "";
	private String age = "";
	private String name = "";
	private String fatherName = "";
	private String sex = "";
	private String voterId = "";
	private String address = "";
	private VoterCardCoord coordinates = new VoterCardCoord();
	
	
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
