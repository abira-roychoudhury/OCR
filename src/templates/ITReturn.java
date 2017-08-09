package templates;

public class ITReturn {
	
	private String panNumber = "";
	private String aadharNumber = "";
	private String assessmentYear = "";
	private String name = "";
	private String status = "";
	private String designationOfAO = "";
	private String originalRevised = "";
	private String eFillingAckNumber = "";
	private String eFillingDate = "";
	private String grossTotalIncome = "";
	private ITReturnCoord coordinates = new ITReturnCoord();
	private Address address = new Address();
	
		
	
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public ITReturnCoord getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(ITReturnCoord coordinated) {
		this.coordinates = coordinated;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public String getAadharNumber() {
		return aadharNumber;
	}
	public void setAadharNumber(String aadharNumber) {
		this.aadharNumber = aadharNumber;
	}
	public String getAssessmentYear() {
		return assessmentYear;
	}
	public void setAssessmentYear(String assessmentYear) {
		this.assessmentYear = assessmentYear;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesignationOfAO() {
		return designationOfAO;
	}
	public void setDesignationOfAO(String designationOfAO) {
		this.designationOfAO = designationOfAO;
	}
	public String getOriginalRevised() {
		return originalRevised;
	}
	public void setOriginalRevised(String originalRevised) {
		this.originalRevised = originalRevised;
	}
	public String geteFillingAckNumber() {
		return eFillingAckNumber;
	}
	public void seteFillingAckNumber(String eFillingAckNumber) {
		this.eFillingAckNumber = eFillingAckNumber;
	}
	public String geteFillingDate() {
		return eFillingDate;
	}
	public void seteFillingDate(String eFillingDate) {
		this.eFillingDate = eFillingDate;
	}
	public String getGrossTotalIncome() {
		return grossTotalIncome;
	}
	public void setGrossTotalIncome(String grossTotalIncome) {
		this.grossTotalIncome = grossTotalIncome;
	}
	
	

}
