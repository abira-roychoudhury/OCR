package templates;

import java.util.Calendar;

public class DrivingLicense {
	
	private Calendar DOB;
	private String name;
	private String middleName;
	private String address;
	private String pin;
	private String bloodGroup;
	
	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the dOB
	 */
	public Calendar getDOB() {
		return DOB;
	}

	/**
	 * @param dOB the dOB to set
	 */
	public void setDOB(Calendar dOB) {
		DOB = dOB;
	}
	
	public String toString(){
		
		String data = "DOB:"+this.getDOB().getTime()+"\n "
						+ "Name: "+this.getName()+"\n "
						+ "S/D/W of: "+this.getMiddleName()+"\n "
						+ "bloodGroup: "+this.getBloodGroup()+"\n "
						+ "Address :"+this.getAddress()+"\n "
						+ "PIN: "+this.getPin();
		
		return data;
	}
	

}
