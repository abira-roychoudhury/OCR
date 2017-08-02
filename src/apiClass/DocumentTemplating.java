package apiClass;

import java.util.LinkedHashMap;

import modal.Constants;

import org.json.JSONArray;

import templates.*;

public class DocumentTemplating {
	
	/* DESCRIPTION : parses the content of Vision API Call Response based on the fileType selected
	 * INPUT : JSONArray from Vision API Call, String of FileType, String of FilePath of image file uploaded
	 * OUTPUT : LinkedHashMap document containing the parsed details and co-ordinates for boundary blocking
	 * */
	public LinkedHashMap<String,Object> parseContent(JSONArray textAnnotationArray,String fileType,String filePath){
		LinkedHashMap<String,Object> document=new LinkedHashMap<String,Object>();
		LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
		LinkedHashMap<String,int[][]> coordinates = new LinkedHashMap<String,int[][]>();
		
		if(fileType.equals(Constants.PanCard.panCard))
		{
			PanCard panCard = new ParsePanCard().parsePanCard(textAnnotationArray,filePath);
			PanCardCoord panCardCoord = panCard.getCoordinates();

			displayDocument.put(Constants.PanCard.name, panCard.getName());
			displayDocument.put(Constants.PanCard.fatherName, panCard.getFatherName());
			displayDocument.put(Constants.PanCard.dob, panCard.getDobDisplay());
			displayDocument.put(Constants.PanCard.panNumber, panCard.getPanNumber());

			try{
				coordinates.put(Constants.PanCard.name, panCardCoord.getName());
			}catch(Exception e){
				coordinates.put(Constants.PanCard.name, new int[2][4]);
			}

			try{
				coordinates.put(Constants.PanCard.fatherName, panCardCoord.getFatherName()); 
			}catch(Exception e){
				coordinates.put(Constants.PanCard.fatherName, new int[2][4]); 
			}

			try{
				coordinates.put(Constants.PanCard.dob, panCardCoord.getDobDisplay());
			}catch(Exception e){
				coordinates.put(Constants.PanCard.dob, new int[2][4]);
			}

			try{
				coordinates.put(Constants.PanCard.panNumber, panCardCoord.getPanNumber());
			}catch(Exception e){
				coordinates.put(Constants.PanCard.panNumber, new int[2][4]);
			}
		}
		
		
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard))
		{
			AadharCard aadharCard = new ParseAadharCard().parseAadharCard(textAnnotationArray,filePath);
			AadharCardCoord aadharCardCoord = aadharCard.getCoordinates();

			displayDocument.put(Constants.AadharCardPage1.name, aadharCard.getName());
			try {
				coordinates.put(Constants.AadharCardPage1.name, aadharCardCoord.getName());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.name, new int[2][4] );
			}
			
			displayDocument.put(Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name, aadharCard.getFatherName());
			try {
				coordinates.put(Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name, aadharCardCoord.getFatherName());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name, new int[2][4] );
			}
			
			if(aadharCard.getYearOfBirth() != 0)
			{
				displayDocument.put(Constants.AadharCardPage1.year, Integer.toString(aadharCard.getYearOfBirth()));
				try {
					coordinates.put(Constants.AadharCardPage1.year, aadharCardCoord.getYearOfBirth());
				} catch (Exception e) {
					coordinates.put(Constants.AadharCardPage1.year, new int[2][4] );
				}
			}
			else
			{
				displayDocument.put(Constants.AadharCardPage1.dob, (aadharCard.getDobDisplay()));
				try {
					coordinates.put(Constants.AadharCardPage1.dob, aadharCardCoord.getDobDisplay());
				} catch (Exception e) {
					coordinates.put(Constants.AadharCardPage1.dob, new int[2][4] );
				}
			}			
			
				
			displayDocument.put(Constants.AadharCardPage1.gender, aadharCard.getGender());
			try {
				coordinates.put(Constants.AadharCardPage1.gender, aadharCardCoord.getGender());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.gender, new int[2][4] );
			}

			displayDocument.put(Constants.AadharCardPage1.aadharNumber, aadharCard.getAadharNumber());
			try {
				coordinates.put(Constants.AadharCardPage1.aadharNumber, aadharCardCoord.getAadharNumber());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.aadharNumber, new int[2][4] );
			}
			
			if(!aadharCard.getAddress().isEmpty()){
				displayDocument.put(Constants.AadharCardPage1.address, aadharCard.getAddress());
				try {
					coordinates.put(Constants.AadharCardPage1.address, aadharCardCoord.getAddress());
				} catch (Exception e) {
					coordinates.put(Constants.AadharCardPage1.address, new int[2][4] );
				}
			}
		}
		
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense))
		{
			DrivingLicense drivingLicense = new ParseDrivingLicense().parseDrivingLicense(textAnnotationArray);
			DrivingLicenseCoord drivingLicenseCoord = drivingLicense.getCoordinates();

			displayDocument.put(Constants.DrivingLicense.name, drivingLicense.getName());
			displayDocument.put(Constants.DrivingLicense.middleName, drivingLicense.getMiddleName());
			displayDocument.put(Constants.DrivingLicense.address, drivingLicense.getAddress());
			displayDocument.put(Constants.DrivingLicense.pin, drivingLicense.getPin());
			displayDocument.put(Constants.DrivingLicense.bloodGroup, drivingLicense.getBloodGroup());
			displayDocument.put(Constants.DrivingLicense.dob, drivingLicense.getDobDisplay());	

			try {
				coordinates.put(Constants.DrivingLicense.name, drivingLicenseCoord.getName());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.name, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.middleName, drivingLicenseCoord.getMiddleName());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.middleName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.address, drivingLicenseCoord.getAddress());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.address, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.pin, drivingLicenseCoord.getPin());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.pin, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.bloodGroup, drivingLicenseCoord.getBloodGroup());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.bloodGroup, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.dob, drivingLicenseCoord.getDobDisplay());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.dob, new int[2][4] );
			}
		}	

		else if(fileType.equals(Constants.VoterCard.voterCard))
		{
			VoterCard voterCard = new ParseVoterCard().parseVoterCard(textAnnotationArray);
			VoterCardCoord voterCardCoord = voterCard.getCoordinates();

			displayDocument.put(Constants.VoterCard.voterId, voterCard.getVoterId());
			displayDocument.put(Constants.VoterCard.electorName, voterCard.getName());
			displayDocument.put(Constants.VoterCard.fatherName, voterCard.getFatherName());
			displayDocument.put(Constants.VoterCard.sex, voterCard.getSex());
			
			if(!voterCard.getAge().isEmpty()){
				displayDocument.put(Constants.VoterCard.age, voterCard.getAge());
				try {
					coordinates.put(Constants.VoterCard.age, voterCardCoord.getAge());
				} catch (Exception e) {
					coordinates.put(Constants.VoterCard.age, new int[2][4] );
				}

			}
			else{			
				displayDocument.put(Constants.VoterCard.dob, voterCard.getDobDisplay());
				try {
					coordinates.put(Constants.VoterCard.dob, voterCardCoord.getDobDisplay());
				} catch (Exception e) {
					coordinates.put(Constants.VoterCard.dob, new int[2][4] );
				}

			}
			displayDocument.put(Constants.VoterCard.address, voterCard.getAddress());
			try {
				coordinates.put(Constants.VoterCard.address, voterCardCoord.getAddress());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.address, new int[2][4] );
			}
				

			try {
				coordinates.put(Constants.VoterCard.voterId, voterCardCoord.getVoterId());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.voterId, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.electorName, voterCardCoord.getName());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.electorName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.fatherName, voterCardCoord.getFatherName());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.fatherName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.sex, voterCardCoord.getSex());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.sex, new int[2][4] );
			}
		}	

		document.put(Constants.displaydocument, displayDocument);
		document.put(Constants.coordinates, coordinates);	
		return document;
	}
}
