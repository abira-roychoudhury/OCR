package apiClass;

import java.util.LinkedHashMap;

import org.json.JSONArray;

import templates.*;

public class DocumentTemplating {

	public LinkedHashMap<String,Object> parseContent(JSONArray textAnnotationArray,String fileType ){
		 LinkedHashMap<String,Object> document=new LinkedHashMap<String,Object>();
		 LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
	     LinkedHashMap<String,int[][]> coordinates = new LinkedHashMap<String,int[][]>();
		 String content = "";
		 
		 if(fileType.equals("PanCard"))
		 {
			 PanCard pc = new ParsePanCard().parsePanCard(textAnnotationArray);
			 PanCardCoord pcc = pc.getCoordinates();
			 			 
			 displayDocument.put("Name", pc.getName());
			 displayDocument.put("Father's Name", pc.getFatherName());
			 displayDocument.put("DOB", pc.getDobDisplay());
			 displayDocument.put("PAN Number", pc.getPanNumber());
			 
			 coordinates.put("Name", pcc.getName());
			 coordinates.put("Father's Name", pcc.getFatherName());
			 coordinates.put("DOB", pcc.getDobDisplay());
			 coordinates.put("PAN Number", pcc.getPanNumber());
			 
		 }
		 else if(fileType.equals("AadharCardPage1"))
		 {
			 AadharCard ac = new ParseAadharCard().parseAadharCard(textAnnotationArray);
			 AadharCardCoord acc = ac.getCoordinates();
			 
			 displayDocument.put("Name", ac.getName());
			 coordinates.put("Name", acc.getName());
			 if(ac.getDobDisplay().equals("")){
				 displayDocument.put("Year of Birth", Integer.toString(ac.getYearOfBirth()));
			     coordinates.put("Year of Birth", acc.getYearOfBirth());}
			 else{
				 displayDocument.put("Date of Birth",ac.getDobDisplay());
				 coordinates.put("Date of Birth", acc.getDobDisplay());}
			 displayDocument.put("Gender", ac.getGender());
			 coordinates.put("Gender", acc.getGender());
			 displayDocument.put("Aadhar Number", ac.getAadharNumber());
			 coordinates.put("Aadhar Number", acc.getAadharNumber());
			 
			 
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
		 document.put("displayDocument", displayDocument);
		 document.put("coordinates", coordinates);	
		 return document;
	}
}
