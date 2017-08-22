package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.Constants;
import templates.AadharCard;
import templates.AadharCardCoord;
import templates.Address;
import templates.DrivingLicense;
import templates.DrivingLicenseCoord;
import templates.ITReturn;
import templates.ITReturnCoord;
import templates.PanCard;
import templates.PanCardCoord;
import templates.SalesForceTemplate;
import templates.VoterCard;
import templates.VoterCardCoord;
import apiClass.ParseAadharCard;
import apiClass.ParseDrivingLicense;
import apiClass.ParseITReturn;
import apiClass.ParsePanCard;
import apiClass.ParseVoterCard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Servlet implementation class SubmitToSF
 */
@WebServlet("/SubmitToSF")
public class SubmitToSF extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitToSF() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String fileType = request.getParameter("fileType");
		SalesForceTemplate originalValues = new SalesForceTemplate();
		SalesForceTemplate correctedValues = new SalesForceTemplate();
		
		
		if(fileType.equals(Constants.PanCard.panCard))
		{
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.PanCard.firstName+"]") + 
										request.getParameter(Constants.originalJson+"["+Constants.PanCard.middleName+"]") +
											request.getParameter(Constants.originalJson+"["+Constants.PanCard.lastName+"]");
			originalValues.setFullName__c(fullnameOriginal);
			
			originalValues.setFirstName__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.firstName+"]"));			
			originalValues.setLastName__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.lastName+"]"));
			originalValues.setFatherName__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.fatherName+"]"));
			originalValues.setDOBOrAge__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.dob+"]"));
			originalValues.setPANNo__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.panNumber+"]"));			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.PanCard.firstName+"]") + 
											request.getParameter(Constants.correctedJson+"["+Constants.PanCard.middleName+"]") +
												request.getParameter(Constants.correctedJson+"["+Constants.PanCard.lastName+"]");
			correctedValues.setFullName__c(fullnameCorrected);
			correctedValues.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.firstName+"]"));
			correctedValues.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.lastName+"]"));
			correctedValues.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.fatherName+"]"));
			correctedValues.setDOBOrAge__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.dob+"]"));
			correctedValues.setPANNo__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.panNumber+"]"));			
		}
		
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard))
		{
			String dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.dob+"]");
			String dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.dob+"]");
			if(dobOriginal.isEmpty()){
				dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.year+"]");
				dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.year+"]");
			}
				
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.firstName+"]") + 
					request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.middleName+"]") +
						request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.lastName+"]");
			originalValues.setFullName__c(fullnameOriginal);
			originalValues.setDOBOrAge__c(dobOriginal);
			originalValues.setFirstName__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.firstName+"]"));
			originalValues.setLastName__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.lastName+"]"));
			originalValues.setFatherName__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name+"]"));
			originalValues.setGender__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.gender+"]"));
			originalValues.setAadharNo__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.aadharNumber+"]"));
			originalValues.setAddressLine1__c(request.getParameter(Constants.originalJson+"["+Constants.Address.address+"]"));
			originalValues.setCity__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			originalValues.setState__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			originalValues.setZipCode__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.firstName+"]") + 
					request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.middleName+"]") +
						request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.lastName+"]");
			correctedValues.setFullName__c(fullnameCorrected);
			correctedValues.setDOBOrAge__c(dobCorrected);
			correctedValues.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.firstName+"]"));
			correctedValues.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.lastName+"]"));
			correctedValues.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name+"]"));
			correctedValues.setGender__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.gender+"]"));
			correctedValues.setAadharNo__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.aadharNumber+"]"));
			correctedValues.setAddressLine1__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.address+"]"));
			correctedValues.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			correctedValues.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			correctedValues.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));
				
		}
		
		else if(fileType.equals(Constants.VoterCard.voterCard))
		{
			String dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.dob+"]");
			String dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.dob+"]");
			if(dobOriginal.isEmpty()){
				dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.age+"]");
				dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.age+"]");
			}
				
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.firstName+"]") + 
					request.getParameter(Constants.originalJson+"["+Constants.VoterCard.middleName+"]") +
						request.getParameter(Constants.originalJson+"["+Constants.VoterCard.lastName+"]");
			originalValues.setFullName__c(fullnameOriginal);
			originalValues.setDOBOrAge__c(dobOriginal);
			originalValues.setVoterCardNo__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.voterId+"]"));
			originalValues.setFirstName__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.firstName+"]"));
			originalValues.setLastName__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.lastName+"]"));
			originalValues.setFatherName__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.fatherName+"]"));
			originalValues.setGender__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.sex+"]"));
			originalValues.setAddressLine1__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.address+"]"));
			originalValues.setCity__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			originalValues.setState__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			originalValues.setZipCode__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + 
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			correctedValues.setFullName__c(fullnameCorrected);
			correctedValues.setDOBOrAge__c(dobCorrected);
			correctedValues.setVoterCardNo__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.voterId+"]"));
			correctedValues.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]"));
			correctedValues.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]"));
			correctedValues.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.fatherName+"]"));
			correctedValues.setGender__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.sex+"]"));
			correctedValues.setAddressLine1__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.address+"]"));
			correctedValues.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			correctedValues.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			correctedValues.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));
			
		}	
		
		else if(fileType.equals(Constants.ITReturn.iTReturn))
		{
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]") + 
					request.getParameter(Constants.originalJson+"["+Constants.ITReturn.middleName+"]") +
						request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]");
			originalValues.setFullName__c(fullnameOriginal);
			
			originalValues.setAssessmentYear__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.assessmentYear+"]"));
			originalValues.setPANNo__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.pan+"]"));
			originalValues.setAadharNo__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.aadharNumber+"]"));			
			originalValues.setFirstName__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]"));
			originalValues.setLastName__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]"));
			originalValues.setStatus__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.status[0]+"]"));
			originalValues.setDesignationofAO__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.designation+"]"));
			originalValues.setOriginalorRevised__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.orgRev+"]"));
			originalValues.setEfillingAcknowledgementNo__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.eFilingAck+"]"));
			originalValues.setDate__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.eFilingDate+"]"));
			originalValues.setGrossTotalIncome__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.grossTotalIncome+"]"));			
			originalValues.setAddressLine1__c(request.getParameter(Constants.originalJson+"["+Constants.Address.address+"]"));
			originalValues.setCity__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			originalValues.setState__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			originalValues.setZipCode__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + 
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			correctedValues.setFullName__c(fullnameCorrected);
			
			correctedValues.setAssessmentYear__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.assessmentYear+"]"));
			correctedValues.setPANNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.pan+"]"));
			correctedValues.setAadharNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.aadharNumber+"]"));			
			correctedValues.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.firstName+"]"));
			correctedValues.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.lastName+"]"));
			correctedValues.setStatus__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.status[0]+"]"));
			correctedValues.setDesignationofAO__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.designation+"]"));
			correctedValues.setOriginalorRevised__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.orgRev+"]"));
			correctedValues.setEfillingAcknowledgementNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.eFilingAck+"]"));
			correctedValues.setDate__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.eFilingDate+"]"));
			correctedValues.setGrossTotalIncome__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.grossTotalIncome+"]"));			
			correctedValues.setAddressLine1__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.address+"]"));
			correctedValues.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			correctedValues.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			correctedValues.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));

		}
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense))
		{
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]") + 
					request.getParameter(Constants.originalJson+"["+Constants.ITReturn.middleName+"]") +
						request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]");
			originalValues.setFullName__c(fullnameOriginal);
			
			originalValues.setFullName__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.name+"]"));
			originalValues.setFatherName__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.middleName+"]"));
			originalValues.setAddressLine1__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.address+"]"));
			originalValues.setZipCode__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.pin+"]"));
			//originalValues.set(Constants.DrivingLicense.bloodGroup, drivingLicense.getBloodGroup());
			originalValues.setDOBOrAge__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.dob+"]"));	

			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + 
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			correctedValues.setFullName__c(fullnameCorrected);
			
			correctedValues.setFullName__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.name+"]"));
			correctedValues.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.middleName+"]"));
			correctedValues.setAddressLine1__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.address+"]"));
			correctedValues.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.pin+"]"));
			//correctedValues.set(Constants.DrivingLicense.bloodGroup, drivingLicense.getBloodGroup());
			correctedValues.setDOBOrAge__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.dob+"]"));				
		}	
		
		
		String name = request.getParameter("originalJson[First Name]");
		out.println("NAME : "+name);
		
		String accessToken = getAccessToken();
		out.println("accessToken : "+accessToken);
		
	}

	private String getAccessToken() {
		try{
			URL url = new URL("https://fincorp--herodev2.cs57.my.salesforce.com/services/oauth2/token?client_id=3MVG959Nd8JMmavQe5kgiSSQJpws6EydIsyaTN07ms2UOmCxXdesnlc3jjJZagffJVi2.4__c3gJUWMfLPG0j&client_secret=7967524131757639248&grant_type=password&username=dharmvir_singh@herofincorp.com.herodev2&password=test@1234");
			System.setProperty("jdk.http.auth.tunneling.disabledSchemes",""); 
			try{
				System.setProperty("https.proxyHost", "ptproxy.persistent.co.in");
				System.setProperty("https.proxyPort", "8080");
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			StringBuilder output = new StringBuilder();			
			String op;
			while ((op = bufferedReaderObject.readLine()) != null) {
				output.append(op);
			}
			conn.disconnect();
			return output.toString();				
		}
		catch(Exception e){
			System.err.print("inside error in SF ACCESS TOKEN catch"+e);
		}
		return null;
	}

}
