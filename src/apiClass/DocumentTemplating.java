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
		
		//Initializations
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

			coordinates.put(Constants.PanCard.name, panCardCoord.getName());
			coordinates.put(Constants.PanCard.fatherName, panCardCoord.getFatherName()); 
			coordinates.put(Constants.PanCard.dob, panCardCoord.getDobDisplay());
			coordinates.put(Constants.PanCard.panNumber, panCardCoord.getPanNumber());
			
		}
		
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard))
		{
			AadharCard aadharCard = new ParseAadharCard().parseAadharCard(textAnnotationArray,filePath);
			AadharCardCoord aadharCardCoord = aadharCard.getCoordinates();
			Address address = aadharCard.getAddress();

			displayDocument.put(Constants.AadharCardPage1.name, aadharCard.getName());
			coordinates.put(Constants.AadharCardPage1.name, aadharCardCoord.getName());
			
			displayDocument.put(Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name, aadharCard.getFatherName());
			coordinates.put(Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name, aadharCardCoord.getFatherName());
			
			if(aadharCard.getYearOfBirth() != 0)
			{
				displayDocument.put(Constants.AadharCardPage1.year, Integer.toString(aadharCard.getYearOfBirth()));
				coordinates.put(Constants.AadharCardPage1.year, aadharCardCoord.getYearOfBirth());
			}
			else
			{
				displayDocument.put(Constants.AadharCardPage1.dob, (aadharCard.getDobDisplay()));
				coordinates.put(Constants.AadharCardPage1.dob, aadharCardCoord.getDobDisplay());
			}			
			
				
			displayDocument.put(Constants.AadharCardPage1.gender, aadharCard.getGender());
			coordinates.put(Constants.AadharCardPage1.gender, aadharCardCoord.getGender());
			

			displayDocument.put(Constants.AadharCardPage1.aadharNumber, aadharCard.getAadharNumber());
			coordinates.put(Constants.AadharCardPage1.aadharNumber, aadharCardCoord.getAadharNumber());
			
			
			displayDocument.put(Constants.Address.address, address.getAddressLine());
			displayDocument.put(Constants.Address.city, address.getCity());
			displayDocument.put(Constants.Address.state, address.getState());
			displayDocument.put(Constants.Address.zipCode, address.getZipCode());
			coordinates.put(Constants.AadharCardPage1.address, aadharCardCoord.getAddress());
			coordinates.put(Constants.Address.city, aadharCardCoord.getAddress());
			coordinates.put(Constants.Address.state, aadharCardCoord.getAddress());
			coordinates.put(Constants.Address.zipCode, aadharCardCoord.getAddress());
		
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

			coordinates.put(Constants.DrivingLicense.name, drivingLicenseCoord.getName());
			coordinates.put(Constants.DrivingLicense.middleName, drivingLicenseCoord.getMiddleName());
			coordinates.put(Constants.DrivingLicense.address, drivingLicenseCoord.getAddress());
			coordinates.put(Constants.DrivingLicense.pin, drivingLicenseCoord.getPin());
			coordinates.put(Constants.DrivingLicense.bloodGroup, drivingLicenseCoord.getBloodGroup());
			coordinates.put(Constants.DrivingLicense.dob, drivingLicenseCoord.getDobDisplay());
			
		}	

		else if(fileType.equals(Constants.VoterCard.voterCard))
		{
			VoterCard voterCard = new ParseVoterCard().parseVoterCard(textAnnotationArray);
			VoterCardCoord voterCardCoord = voterCard.getCoordinates();
			Address address = voterCard.getAddress();

			displayDocument.put(Constants.VoterCard.voterId, voterCard.getVoterId());
			displayDocument.put(Constants.VoterCard.electorName, voterCard.getName());
			displayDocument.put(Constants.VoterCard.fatherName, voterCard.getFatherName());
			displayDocument.put(Constants.VoterCard.sex, voterCard.getSex());
			
			if(!voterCard.getAge().isEmpty()){
				
				displayDocument.put(Constants.VoterCard.age, voterCard.getAge());
				coordinates.put(Constants.VoterCard.age, voterCardCoord.getAge());
			}
			else{	
				
				displayDocument.put(Constants.VoterCard.dob, voterCard.getDobDisplay());
				coordinates.put(Constants.VoterCard.dob, voterCardCoord.getDobDisplay());
			}
			
			displayDocument.put(Constants.VoterCard.address, address.getAddressLine());
			displayDocument.put(Constants.Address.city, address.getCity());
			displayDocument.put(Constants.Address.state, address.getState());
			displayDocument.put(Constants.Address.zipCode, address.getZipCode());
			
					
			coordinates.put(Constants.VoterCard.voterId, voterCardCoord.getVoterId());
			coordinates.put(Constants.VoterCard.electorName, voterCardCoord.getName());
			coordinates.put(Constants.VoterCard.fatherName, voterCardCoord.getFatherName());
			coordinates.put(Constants.VoterCard.sex, voterCardCoord.getSex());
			coordinates.put(Constants.VoterCard.address, voterCardCoord.getAddress());
			coordinates.put(Constants.Address.city, voterCardCoord.getAddress());
			coordinates.put(Constants.Address.state, voterCardCoord.getAddress());
			coordinates.put(Constants.Address.zipCode, voterCardCoord.getAddress());
		}	
		if(fileType.equals(Constants.ITReturn.iTReturn))
		{
			ITReturn iTReturn = new ParseITReturn().parseITReturn(textAnnotationArray,filePath);
			ITReturnCoord iTReturnCoord = iTReturn.getCoordinates();
			Address address = iTReturn.getAddress();

			displayDocument.put(Constants.ITReturn.pan, iTReturn.getPanNumber());
			displayDocument.put(Constants.ITReturn.aadharNumber, iTReturn.getAadharNumber());
			displayDocument.put(Constants.ITReturn.assessmentYear, iTReturn.getAssessmentYear());
			displayDocument.put(Constants.ITReturn.name, iTReturn.getName());
			displayDocument.put(Constants.ITReturn.status[0], iTReturn.getStatus());
			displayDocument.put(Constants.ITReturn.designation, iTReturn.getDesignationOfAO());
			displayDocument.put(Constants.ITReturn.orgRev, iTReturn.getOriginalRevised());
			displayDocument.put(Constants.ITReturn.eFilingAck, iTReturn.geteFillingAckNumber());
			displayDocument.put(Constants.ITReturn.eFilingDate, iTReturn.geteFillingDate());
			displayDocument.put(Constants.ITReturn.grossTotalIncome, iTReturn.getGrossTotalIncome());
			
			displayDocument.put(Constants.Address.address, address.getAddressLine());
			displayDocument.put(Constants.Address.city, address.getCity());
			displayDocument.put(Constants.Address.state, address.getState());
			displayDocument.put(Constants.Address.zipCode, address.getZipCode());

			coordinates.put(Constants.ITReturn.pan, iTReturnCoord.getPanNumber());
			coordinates.put(Constants.ITReturn.aadharNumber, iTReturnCoord.getAadharNumber());
			coordinates.put(Constants.ITReturn.assessmentYear, iTReturnCoord.getAssessmentYear());
			coordinates.put(Constants.ITReturn.name, iTReturnCoord.getName());
			coordinates.put(Constants.ITReturn.status[0], iTReturnCoord.getStatus());
			coordinates.put(Constants.ITReturn.designation, iTReturnCoord.getDesignationOfAO());
			coordinates.put(Constants.ITReturn.orgRev, iTReturnCoord.getOriginalRevised());
			coordinates.put(Constants.ITReturn.eFilingAck, iTReturnCoord.geteFillingAckNumber());
			coordinates.put(Constants.ITReturn.eFilingDate, iTReturnCoord.geteFillingDate());
			coordinates.put(Constants.ITReturn.grossTotalIncome, iTReturnCoord.getGrossTotalIncome());
			
			coordinates.put(Constants.Address.address, iTReturnCoord.getAddress());
			coordinates.put(Constants.Address.city, iTReturnCoord.getAddress());
			coordinates.put(Constants.Address.state,iTReturnCoord.getAddress());
			coordinates.put(Constants.Address.zipCode, iTReturnCoord.getAddress());
			
		}

		document.put(Constants.displaydocument, displayDocument);
		document.put(Constants.coordinates, coordinates);	
		return document;
	}
}
