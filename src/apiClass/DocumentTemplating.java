package apiClass;

import java.util.LinkedHashMap;

import modal.Constants;

import org.json.JSONArray;

import templates.*;

public class DocumentTemplating {

	public LinkedHashMap<String,Object> parseContent(JSONArray textAnnotationArray,String fileType,String filePath ){
		LinkedHashMap<String,Object> document=new LinkedHashMap<String,Object>();
		LinkedHashMap<String,String> displayDocument = new LinkedHashMap<String,String>();
		LinkedHashMap<String,int[][]> coordinates = new LinkedHashMap<String,int[][]>();
		String content = "";

		if(fileType.equals(Constants.PanCard.panCard))
		{
			PanCard pc = new ParsePanCard().parsePanCard(textAnnotationArray,filePath);
			PanCardCoord pcc = pc.getCoordinates();

			displayDocument.put(Constants.PanCard.name, pc.getName());
			displayDocument.put(Constants.PanCard.fatherName, pc.getFatherName());
			displayDocument.put(Constants.PanCard.dob, pc.getDobDisplay());
			displayDocument.put(Constants.PanCard.panNumber, pc.getPanNumber());

			try{
				coordinates.put(Constants.PanCard.name, pcc.getName());
			}
			catch(Exception e){
				coordinates.put(Constants.PanCard.name, new int[2][4]);
			}

			try{
				coordinates.put(Constants.PanCard.fatherName, pcc.getFatherName()); 
			}
			catch(Exception e){
				coordinates.put(Constants.PanCard.fatherName, new int[2][4]); 
			}

			try{
				coordinates.put(Constants.PanCard.dob, pcc.getDobDisplay());
			}
			catch(Exception e){
				coordinates.put(Constants.PanCard.dob, new int[2][4]);
			}

			try{
				coordinates.put(Constants.PanCard.panNumber, pcc.getPanNumber());
			}
			catch(Exception e){
				coordinates.put(Constants.PanCard.panNumber, new int[2][4]);
			}
		}
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard))
		{
			AadharCard ac = new ParseAadharCard().parseAadharCard(textAnnotationArray,filePath);
			AadharCardCoord acc = ac.getCoordinates();

			displayDocument.put(Constants.AadharCardPage1.name, ac.getName());
			try {
				coordinates.put(Constants.AadharCardPage1.name, acc.getName());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.name, new int[2][4] );
			}
			
			displayDocument.put(Constants.AadharCardPage1.year, Integer.toString(ac.getYearOfBirth()));
			try {
				coordinates.put(Constants.AadharCardPage1.year, acc.getYearOfBirth());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.year, new int[2][4] );
			}
			
			displayDocument.put(Constants.AadharCardPage1.gender, ac.getGender());
			try {
				coordinates.put(Constants.AadharCardPage1.gender, acc.getGender());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.gender, new int[2][4] );
			}

			displayDocument.put(Constants.AadharCardPage1.aadharNumber, ac.getAadharNumber());
			try {
				coordinates.put(Constants.AadharCardPage1.aadharNumber, acc.getAadharNumber());
			} catch (Exception e) {
				coordinates.put(Constants.AadharCardPage1.aadharNumber, new int[2][4] );
			}
			
			displayDocument.put(Constants.AadharCardPage1.address, ac.getAddress());

		}
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense))
		{
			DrivingLicense dl = new ParseDrivingLicense().parseDrivingLicense(textAnnotationArray);
			DrivingLicenseCoord dlc = dl.getCoordinates();

			displayDocument.put(Constants.DrivingLicense.name, dl.getName());
			displayDocument.put(Constants.DrivingLicense.middleName, dl.getMiddleName());
			displayDocument.put(Constants.DrivingLicense.address, dl.getAddress());
			displayDocument.put(Constants.DrivingLicense.pin, dl.getPin());
			displayDocument.put(Constants.DrivingLicense.bloodGroup, dl.getBloodGroup());
			displayDocument.put(Constants.DrivingLicense.dob, dl.getDobDisplay());	

			try {
				coordinates.put(Constants.DrivingLicense.name, dlc.getName());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.name, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.middleName, dlc.getMiddleName());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.middleName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.address, dlc.getAddress());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.address, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.pin, dlc.getPin());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.pin, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.bloodGroup, dlc.getBloodGroup());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.bloodGroup, new int[2][4] );
			}

			try {
				coordinates.put(Constants.DrivingLicense.dob, dlc.getDobDisplay());
			} catch (Exception e) {
				coordinates.put(Constants.DrivingLicense.dob, new int[2][4] );
			}
		}	

		else if(fileType.equals(Constants.VoterCard.voterCard))
		{
			VoterCard vl = new ParseVoterCard().parseVoterCard(textAnnotationArray);
			VoterCardCoord vlc = vl.getCoordinates();

			displayDocument.put(Constants.VoterCard.voterId, vl.getVoterId());
			displayDocument.put(Constants.VoterCard.electorName, vl.getName());
			displayDocument.put(Constants.VoterCard.fatherName, vl.getFatherName());
			displayDocument.put(Constants.VoterCard.sex, vl.getSex());
			displayDocument.put(Constants.VoterCard.dob, vl.getDobDisplay());	

			try {
				coordinates.put(Constants.VoterCard.voterId, vlc.getVoterId());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.voterId, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.electorName, vlc.getName());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.electorName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.fatherName, vlc.getFatherName());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.fatherName, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.sex, vlc.getSex());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.sex, new int[2][4] );
			}

			try {
				coordinates.put(Constants.VoterCard.dob, vlc.getDobDisplay());
			} catch (Exception e) {
				coordinates.put(Constants.VoterCard.dob, new int[2][4] );
			}

		}	

		document.put(Constants.displaydocument, displayDocument);
		document.put(Constants.coordinates, coordinates);	
		return document;
	}
}
