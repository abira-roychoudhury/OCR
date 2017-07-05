package apiClass;

import java.util.LinkedHashMap;
import templates.*;

public class DocumentTemplating {

	public LinkedHashMap<String,String> parseContent(String content,String fileType ){
		 LinkedHashMap<String,String> document=new LinkedHashMap<String,String>(); 
		 
		 if(fileType.equals("PanCard"))
		 {
			 PanCard pc = new ParsePanCard().parsePanCard(content);
			 document.put("Name", pc.getName());
			 document.put("Father's Name", pc.getFatherName());
			 document.put("DOB", pc.getDobDisplay());
			 document.put("PAN Number", pc.getPanNumber());
		 }
		 else if(fileType.equals("AadharCardPage1"))
		 {
			 AadharCard ac = new ParseAadharCard().parseAadharCard(content);
			 document.put("Name", ac.getName());
			 if(ac.getDobDisplay().equals(""))
				 document.put("Year of Birth", Integer.toString(ac.getYearOfBirth()));
			 else
				 document.put("Date of Birth",ac.getDobDisplay());
			 document.put("Gender", ac.getGender());
			 document.put("Aadhar Number", ac.getAadharNumber());
		 }
		 else if(fileType.equals("DrivingLicense"))
		 {
			 DrivingLicense dl = new ParseDrivingLicense().parseDrivingLicense(content);
			 document.put("Name", dl.getName());
			 document.put("S/D/W of", dl.getMiddleName());
			 document.put("Address", dl.getAddress());
			 document.put("PIN", dl.getPin());
			 document.put("Blood Group", dl.getBloodGroup());
			 document.put("DOB ", dl.getDobDisplay());			 
		 }	
		return document;
	}
}
