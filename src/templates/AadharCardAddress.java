package templates;

public class AadharCardAddress {
	
	private String co = "";
	private String house = "";
	private String street = "";
	private String lm = "";
	private String loc = "";
	private String vtc = "";
	private String po = "";
	private String dist = "";
	private String subdist = "";
	private String state = "";
	private String pc = "";
	
	
	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLm() {
		return lm;
	}

	public void setLm(String lm) {
		this.lm = lm;
	}

	public String getVtc() {
		return vtc;
	}

	public void setVtc(String vtc) {
		this.vtc = vtc;
	}

	public String getSubdist() {
		return subdist;
	}

	public void setSubdist(String subdist) {
		this.subdist = subdist;
	}

	
	
	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}
	
	public String toString(){
		String addr = co + "\n"+house + "\n"+street + "\n"+lm + "\n"+loc + "\n"+vtc + "\n"+po + "\n"+dist + "\n"+subdist + "\n"+state + "\n"+pc;
		return addr;
	}

}
