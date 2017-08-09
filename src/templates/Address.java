package templates;

public class Address {
	
	public Address(){
		
	}
	
	public Address(String address, String city, String state, String zipCode){
		this.addressLine = address;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
	}
	

	private String addressLine = "";
	private String zipCode = "";
	private String state = "";
	private String city = "";
	
	
	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getAddressLine() {
		return addressLine;
	}

	public void setAddressLine(String addressLine) {
		this.addressLine = addressLine;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	public String toString(){
		String address="";
		if(!this.addressLine.isEmpty())
			address = this.addressLine+",\n";
		
		if(!this.city.isEmpty())
			address = address+this.city+"\n";
		
		if(!this.state.isEmpty())
			address = address+this.state+"\n";
		
		if(!this.zipCode.isEmpty())
			address = address+this.zipCode;
		return address;
	}
	

	
}
