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
			 DrivingLicense dl = new ParseDrivingLicense().parseDrivingLicense(textAnnotationArray);
			 DrivingLicenseCoord dlc = dl.getCoordinates();
			 
			 displayDocument.put("Name", dl.getName());
			 displayDocument.put("S/D/W of", dl.getMiddleName());
			 displayDocument.put("Address", dl.getAddress());
			 displayDocument.put("PIN", dl.getPin());
			 displayDocument.put("Blood Group", dl.getBloodGroup());
			 displayDocument.put("DOB ", dl.getDobDisplay());	
			 
			 coordinates.put("Name", dlc.getName());
			 coordinates.put("S/D/W of", dlc.getMiddleName());
			 coordinates.put("Address", dlc.getAddress());
			 coordinates.put("PIN", dlc.getPin());
			 coordinates.put("Blood Group", dlc.getBloodGroup());
			 coordinates.put("DOB ", dlc.getDobDisplay());	
		 }	
		 
		 else if(fileType.equals("VoterCard"))
		 {
			 VoterCard vl = new ParseVoterCard().parseVoterCard(textAnnotationArray);
			 VoterCardCoord vlc = vl.getCoordinates();
			 
			 displayDocument.put("Elector's Name", vl.getName());
			 displayDocument.put("Father's Name", vl.getFatherName());
			 displayDocument.put("Sex", vl.getSex());
			 displayDocument.put("DOB ", vl.getDobDisplay());	
			 
			 
			 coordinates.put("Elector's Name", vlc.getName());
			 coordinates.put("Father's Name", vlc.getFatherName());
			 coordinates.put("Sex", vlc.getSex());
			 coordinates.put("DOB ", vlc.getDobDisplay());
			
		 }	
		 
		 
		 
		 document.put("displayDocument", displayDocument);
		 document.put("coordinates", coordinates);	
		 return document;
	}
}
