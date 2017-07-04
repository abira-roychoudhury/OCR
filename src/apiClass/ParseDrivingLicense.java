package apiClass;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import templates.DrivingLicense;

public class ParseDrivingLicense {
	
	public DrivingLicense parseDrivingLicense(String content){
		DrivingLicense obj = new DrivingLicense();
		String tokens[] = content.split("\n");
	
		for(int i=0; i < tokens.length; i++){
			String token = tokens[i];
			//Find Date Of Birth
			if(token.toLowerCase().contains("dob")){ 
				String dates[] = token.split(" "); 
				for(String date : dates){
					if(date.contains("-")){
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						Calendar cal = Calendar.getInstance();
						try{
							cal.setTime(sdf.parse(date));
							
							//System.out.println("date:"+cal.getTime().toString());
						}
						catch(Exception e)
						{
							System.err.println(e);
						}
						obj.setDOB(cal);
						break;
					}
				}				
				//Find BloodGroup
				String bloodGroup = token.substring(token.lastIndexOf(":")+1);
				obj.setBloodGroup(bloodGroup.replace(":", "").trim());
			}
			//Find Name
			else if(token.toLowerCase().contains("name")){
				String name = token.substring(token.toLowerCase().lastIndexOf("name")+4);
				obj.setName(name.toUpperCase().replace(":", "").trim());
				
				//Find MiddleName
				String middleName = tokens[i+1].substring(tokens[i+1].toLowerCase().lastIndexOf("of")+2);
				obj.setMiddleName(middleName.toUpperCase().replace(":", "").trim());
				i++;				
			}
			//Find Address
			else if(token.toLowerCase().contains("add")){
				String address = token.substring(token.toLowerCase().lastIndexOf("add")+3);
				if(!tokens[i+1].toLowerCase().contains("pin"))
					address = address.concat(tokens[i+1]);
				obj.setAddress(address.replace(":", ""));
				i++;
			}
			//Find PIN
			else if(token.toLowerCase().contains("pin")){
				String pin = token.substring(token.toLowerCase().lastIndexOf("pin")+3);
				obj.setPin(pin.replace(":", ""));
			}
		}
		return obj;
	}
	
	
	public static void main(String args[]) {
		ParseDrivingLicense pdl = new ParseDrivingLicense();
		
		DrivingLicense dl = pdl.parseDrivingLicense("THE UNION OF INDIA\nJud MAHARASHTRA STATE MOTOR DRIVING LICENCE VAN\nDL No MH03 20080006266 DOI: 18-01-2008\nTwente wead Valid Till : 17-01-2028 (NT) AED 04-02-2011 FORM 7\nAUTHORISATION TO DRIVE FOLLOWING CLASS RULE 16 (2)\nOF VEHICLES THROUGHOUT INDIA\nCOV DO/\nMCWG 20-01-2011\nLMV 18-01-2008\nDOB : 06-09-1984 BG : AB+\nName TRUPTI KAMBLE\nS/DMW ofMADHUKAR KAMBLE\nAdd 1093151, KANNAMWAR NAGAR NO-2\nVIKHROLI (E) MUMBAI\nPIN :400083\nSignature &amp; ID of\nTssuing Authority: MH03 2011369\nSignature/Thumb\nImpression of Holder\n");
		//DrivingLicense dl = pdl.parseDrivingLicense("THE UNION OF INDIA\nMAHARASHTRA STATE MOTOR DRIVING LICENCE\nDL No MH-1220050000188 ID 12-12-2005\nA Valid Till: 11-12-2025 (Non Trans)\n(Inv Crg) FORM 7\nRULE 16 (2)\nAUTHORISATION TO DRIVE FOLLOWING CLASS.\nOF VEHICLES THROUGHOUT INDIA\nCOV ID\nMCWG 12-12-2005\nBadge1:\nDOB 12-05-1986 BG : Not\nName POOJA M PALANDE\nS/DMW of MILIND PALANDE\nAdd S No. 3214A/3, AMBEGAON BK,\nBHARTI VIDYAPEETH BACKSIDE\nPUNE\nPIN 411046 a\nSignature &amp; ID of Signature Thumb\nissuing Authority MH-1220051 Impression of Holder\n");
		System.out.println(dl);
	}
}
