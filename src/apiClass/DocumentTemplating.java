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
			 
			 try{
				 coordinates.put("Name", pcc.getName());
			 }
			 catch(Exception e){
				 coordinates.put("Name", new int[2][4]);
			 }
			 
			 try{
				 coordinates.put("Father's Name", pcc.getFatherName()); 
			 }
			 catch(Exception e){
				 coordinates.put("Father's Name", new int[2][4]); 
			 }
			 
			 try{
				 coordinates.put("DOB", pcc.getDobDisplay());
			 }
			 catch(Exception e){
				 coordinates.put("DOB", new int[2][4]);
			 }
			 
			 try{
				 coordinates.put("PAN Number", pcc.getPanNumber());
			 }
			 catch(Exception e){
				 coordinates.put("PAN Number", new int[2][4]);
			 }
		 }
		 else if(fileType.equals("AadharCardPage1"))
		 {
			 AadharCard ac = new ParseAadharCard().parseAadharCard(textAnnotationArray);
			 AadharCardCoord acc = ac.getCoordinates();
			 
			 displayDocument.put("Name", ac.getName());
			 
			 try {
				 coordinates.put("Name", acc.getName());
			 } catch (Exception e) {
				 coordinates.put("Name", new int[2][4] );
			 }
			 
			 if(ac.getDobDisplay().equals("")){	 
				 displayDocument.put("Year of Birth", Integer.toString(ac.getYearOfBirth()));
			 
				 try {
					 coordinates.put("Year of Birth", acc.getYearOfBirth());
				 } catch (Exception e) {
					 coordinates.put("Year of Birth", new int[2][4] );
				 }
			 
			 }
			 else
			 {
				 displayDocument.put("Date of Birth",ac.getDobDisplay());
			
				 try {
					 coordinates.put("Date of Birth", acc.getDobDisplay());
				 } catch (Exception e) {
					 coordinates.put("Date of Birth", new int[2][4] );
				 }
			 
			 }
			
			 displayDocument.put("Gender", ac.getGender());
			
			 try {
				 coordinates.put("Gender", acc.getGender());
			 } catch (Exception e) {
				 coordinates.put("Gender", new int[2][4] );
			 }
			
			 displayDocument.put("Aadhar Number", ac.getAadharNumber());
			 
			 try {
				 coordinates.put("Aadhar Number", acc.getAadharNumber());
			 } catch (Exception e) {
				 coordinates.put("Aadhar Number", new int[2][4] );
			 }
			 
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
			 
			 try {
				 coordinates.put("Name", dlc.getName());
			 } catch (Exception e) {
				 coordinates.put("Name", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("S/D/W of", dlc.getMiddleName());
			 } catch (Exception e) {
				 coordinates.put("S/D/W of", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("Address", dlc.getAddress());
			 } catch (Exception e) {
				 coordinates.put("Address", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("PIN", dlc.getPin());
			 } catch (Exception e) {
				 coordinates.put("PIN", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("Blood Group", dlc.getBloodGroup());
			 } catch (Exception e) {
				 coordinates.put("Blood Group", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("DOB ", dlc.getDobDisplay());
			 } catch (Exception e) {
				 coordinates.put("DOB ", new int[2][4] );
			 }
		 }	
		 
		 else if(fileType.equals("VoterCard"))
		 {
			 VoterCard vl = new ParseVoterCard().parseVoterCard(textAnnotationArray);
			 VoterCardCoord vlc = vl.getCoordinates();
			 
			 displayDocument.put("Voter Id", vl.getVoterId());
			 displayDocument.put("Elector's Name", vl.getName());
			 displayDocument.put("Father's Name", vl.getFatherName());
			 displayDocument.put("Sex", vl.getSex());
			 displayDocument.put("DOB ", vl.getDobDisplay());	
			 
			 try {
				 coordinates.put("Voter Id", vlc.getVoterId());
			 } catch (Exception e) {
				 coordinates.put("Voter Id", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("Elector's Name", vlc.getName());
			 } catch (Exception e) {
				 coordinates.put("Elector's Name", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("Father's Name", vlc.getFatherName());
			 } catch (Exception e) {
				 coordinates.put("Father's Name", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("Sex", vlc.getSex());
			 } catch (Exception e) {
				 coordinates.put("Sex", new int[2][4] );
			 }
			 
			 try {
				 coordinates.put("DOB ", vlc.getDobDisplay());
			 } catch (Exception e) {
				 coordinates.put("DOB ", new int[2][4] );
			 }
			
		 }	
		 
		 document.put("displayDocument", displayDocument);
		 document.put("coordinates", coordinates);	
		 return document;
	}
}
