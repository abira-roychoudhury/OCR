package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import templates.SalesForceTemplate;
import modal.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
		String salesforcerecordID = request.getParameter("salesforcerecordID");
		
		SalesForceTemplate salesForceTemplateObject = new SalesForceTemplate();
				
		
		
		
		
		
		if(fileType.equals(Constants.PanCard.panCard))
		{
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.PanCard.firstName+"]") + " " +
										request.getParameter(Constants.originalJson+"["+Constants.PanCard.middleName+"]") + " " +
											request.getParameter(Constants.originalJson+"["+Constants.PanCard.lastName+"]");
			salesForceTemplateObject.setFullNameOld__c(fullnameOriginal);			
			salesForceTemplateObject.setFirstNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.firstName+"]"));			
			salesForceTemplateObject.setLastNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.lastName+"]"));
			salesForceTemplateObject.setFatherNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.fatherName+"]"));
			salesForceTemplateObject.setDOBOrAgeOld__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.dob+"]"));
			salesForceTemplateObject.setPANNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.PanCard.panNumber+"]"));			
			
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.PanCard.firstName+"]") + " " +
											request.getParameter(Constants.correctedJson+"["+Constants.PanCard.middleName+"]") + " " +
												request.getParameter(Constants.correctedJson+"["+Constants.PanCard.lastName+"]");
			salesForceTemplateObject.setFullName__c(fullnameCorrected);
			salesForceTemplateObject.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.firstName+"]"));
			salesForceTemplateObject.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.lastName+"]"));
			salesForceTemplateObject.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.fatherName+"]"));
			salesForceTemplateObject.setDOBOrAge__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.dob+"]"));
			salesForceTemplateObject.setPANNo__c(request.getParameter(Constants.correctedJson+"["+Constants.PanCard.panNumber+"]"));			
		}
		
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard))
		{
			String dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.dob+"]");
			String dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.dob+"]");
			if(dobOriginal.isEmpty()){
				dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.year+"]");
				dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.year+"]");
			}
				
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.firstName+"]") + " " +
					request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.middleName+"]") + " " +
						request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.lastName+"]");
			salesForceTemplateObject.setFullNameOld__c(fullnameOriginal);
			salesForceTemplateObject.setDOBOrAgeOld__c(dobOriginal);
			salesForceTemplateObject.setFirstNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.firstName+"]"));
			salesForceTemplateObject.setLastNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.lastName+"]"));
			salesForceTemplateObject.setFatherNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name+"]"));
			salesForceTemplateObject.setGenderOld__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.gender+"]"));
			salesForceTemplateObject.setAadharNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.AadharCardPage1.aadharNumber+"]"));
			salesForceTemplateObject.setAddressOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.address+"]"));
			salesForceTemplateObject.setCityOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setStateOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCodeOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.firstName+"]") + " " +
					request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.middleName+"]") + " " +
						request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.lastName+"]");
			salesForceTemplateObject.setFullName__c(fullnameCorrected);
			salesForceTemplateObject.setDOBOrAge__c(dobCorrected);
			salesForceTemplateObject.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.firstName+"]"));
			salesForceTemplateObject.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.lastName+"]"));
			salesForceTemplateObject.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.father+"'s "+Constants.AadharCardPage1.name+"]"));
			salesForceTemplateObject.setGender__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.gender+"]"));
			salesForceTemplateObject.setAadharNo__c(request.getParameter(Constants.correctedJson+"["+Constants.AadharCardPage1.aadharNumber+"]"));
			salesForceTemplateObject.setAddress__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.address+"]"));
			salesForceTemplateObject.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));
				
		}
		
		else if(fileType.equals(Constants.VoterCard.voterCard))
		{
			String dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.dob+"]");
			String dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.dob+"]");
			if(dobOriginal.isEmpty()){
				dobOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.age+"]");
				dobCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.age+"]");
			}
				
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.VoterCard.firstName+"]") + " " +
					request.getParameter(Constants.originalJson+"["+Constants.VoterCard.middleName+"]") + " " +
						request.getParameter(Constants.originalJson+"["+Constants.VoterCard.lastName+"]");
			salesForceTemplateObject.setFullNameOld__c(fullnameOriginal);
			salesForceTemplateObject.setDOBOrAgeOld__c(dobOriginal);
			salesForceTemplateObject.setVoterCardNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.voterId+"]"));
			salesForceTemplateObject.setFirstNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.firstName+"]"));
			salesForceTemplateObject.setLastNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.lastName+"]"));
			salesForceTemplateObject.setFatherNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.fatherName+"]"));
			salesForceTemplateObject.setGenderOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.sex+"]"));
			salesForceTemplateObject.setAddressOld__c(request.getParameter(Constants.originalJson+"["+Constants.VoterCard.address+"]"));
			salesForceTemplateObject.setCityOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setStateOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCodeOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + " " +
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") + " " +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			salesForceTemplateObject.setFullName__c(fullnameCorrected);
			salesForceTemplateObject.setDOBOrAge__c(dobCorrected);
			salesForceTemplateObject.setVoterCardNo__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.voterId+"]"));
			salesForceTemplateObject.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]"));
			salesForceTemplateObject.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]"));
			salesForceTemplateObject.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.fatherName+"]"));
			salesForceTemplateObject.setGender__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.sex+"]"));
			salesForceTemplateObject.setAddress__c(request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.address+"]"));
			salesForceTemplateObject.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));
			
		}	
		
		else if(fileType.equals(Constants.ITReturn.iTReturn))
		{
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]") + " " +
					request.getParameter(Constants.originalJson+"["+Constants.ITReturn.middleName+"]") + " " +
						request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]");
			salesForceTemplateObject.setFullNameOld__c(fullnameOriginal);			
			salesForceTemplateObject.setAssessmentYearOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.assessmentYear+"]"));
			salesForceTemplateObject.setPANNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.pan+"]"));
			salesForceTemplateObject.setAadharNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.aadharNumber+"]"));			
			salesForceTemplateObject.setFirstNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]"));
			salesForceTemplateObject.setLastNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]"));
			salesForceTemplateObject.setStatusOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.status[0]+"]"));
			salesForceTemplateObject.setDesignationofAOOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.designation+"]"));
			salesForceTemplateObject.setOriginalOrRevisedOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.orgRev+"]"));
			salesForceTemplateObject.setEfillingAcknowledgementNoOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.eFilingAck+"]"));
			salesForceTemplateObject.setDateOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.eFilingDate+"]"));
			salesForceTemplateObject.setGrossTotalIncomeOld__c(request.getParameter(Constants.originalJson+"["+Constants.ITReturn.grossTotalIncome+"]"));			
			salesForceTemplateObject.setAddressOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.address+"]"));
			salesForceTemplateObject.setCityOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setStateOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCodeOld__c(request.getParameter(Constants.originalJson+"["+Constants.Address.zipCode+"]"));
			
			
			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + " " +
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") + " " +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			salesForceTemplateObject.setFullName__c(fullnameCorrected);			
			salesForceTemplateObject.setAssessmentYear__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.assessmentYear+"]"));
			salesForceTemplateObject.setPANNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.pan+"]"));
			salesForceTemplateObject.setAadharNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.aadharNumber+"]"));			
			salesForceTemplateObject.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.firstName+"]"));
			salesForceTemplateObject.setLastName__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.lastName+"]"));
			salesForceTemplateObject.setStatus__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.status[0]+"]"));
			salesForceTemplateObject.setDesignationofAO__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.designation+"]"));
			salesForceTemplateObject.setOriginalOrRevised__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.orgRev+"]"));
			salesForceTemplateObject.setEfillingAcknowledgementNo__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.eFilingAck+"]"));
			salesForceTemplateObject.setDate__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.eFilingDate+"]"));
			salesForceTemplateObject.setGrossTotalIncome__c(request.getParameter(Constants.correctedJson+"["+Constants.ITReturn.grossTotalIncome+"]"));			
			salesForceTemplateObject.setAddress__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.address+"]"));
			salesForceTemplateObject.setCity__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.city+"]"));
			salesForceTemplateObject.setState__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.state+"]"));
			salesForceTemplateObject.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.Address.zipCode+"]"));

		}
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense))
		{
			
			String fullnameOriginal = request.getParameter(Constants.originalJson+"["+Constants.ITReturn.firstName+"]") + " " +
					request.getParameter(Constants.originalJson+"["+Constants.ITReturn.middleName+"]") + " " +
						request.getParameter(Constants.originalJson+"["+Constants.ITReturn.lastName+"]");
			salesForceTemplateObject.setFullNameOld__c(fullnameOriginal);			
			salesForceTemplateObject.setFirstNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.name+"]"));
			salesForceTemplateObject.setFatherNameOld__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.middleName+"]"));
			salesForceTemplateObject.setAddressOld__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.address+"]"));
			salesForceTemplateObject.setZipCodeOld__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.pin+"]"));
			//salesForceTemplateObject.set(Constants.DrivingLicense.bloodGroup, drivingLicense.getBloodGroup());
			salesForceTemplateObject.setDOBOrAgeOld__c(request.getParameter(Constants.originalJson+"["+Constants.DrivingLicense.dob+"]"));	
			
			

			String fullnameCorrected = request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.firstName+"]") + " " +
					request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.middleName+"]") + " " +
						request.getParameter(Constants.correctedJson+"["+Constants.VoterCard.lastName+"]");
			salesForceTemplateObject.setFullName__c(fullnameCorrected);			
			salesForceTemplateObject.setFirstName__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.name+"]"));
			salesForceTemplateObject.setFatherName__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.middleName+"]"));
			salesForceTemplateObject.setAddress__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.address+"]"));
			salesForceTemplateObject.setZipCode__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.pin+"]"));
			//salesForceTemplateObject.set(Constants.DrivingLicense.bloodGroup, drivingLicense.getBloodGroup());
			salesForceTemplateObject.setDOBOrAge__c(request.getParameter(Constants.correctedJson+"["+Constants.DrivingLicense.dob+"]"));				
		}	
		
		
		String nameOld = request.getParameter(Constants.originalJson+"["+Constants.PanCard.firstName+"]");
		out.println("OLD NAME : "+nameOld);
		
		String nameNew = request.getParameter(Constants.correctedJson+"["+Constants.PanCard.firstName+"]");
		out.println("NEW NAME : "+nameNew);
		
		String accessToken = getAccessToken();
		out.println("accessToken : "+accessToken);		
		
		JSONObject requestBody = createSFRequestBody(salesForceTemplateObject, salesforcerecordID);	
		out.println("requestBody : "+requestBody);
	}
	
	

	private JSONObject createSFRequestBody(SalesForceTemplate salesForceTemplateObject,	String salesforcerecordID) {
		JSONObject requestBody = new JSONObject();
		
		JSONObject richInput = new JSONObject();	
		
		richInput.put(Constants.SFRequest.AadharNo__c, salesForceTemplateObject.getAadharNo__c());
		richInput.put(Constants.SFRequest.AadharNoOld__c, salesForceTemplateObject.getAadharNoOld__c());
		richInput.put(Constants.SFRequest.Address__c, salesForceTemplateObject.getAddress__c());
		richInput.put(Constants.SFRequest.AddressOld__c, salesForceTemplateObject.getAddressOld__c());
		
		richInput.put(Constants.SFRequest.AdvanceTax__c, salesForceTemplateObject.getAdvanceTax__c());
		richInput.put(Constants.SFRequest.AdvanceTaxOld__c, salesForceTemplateObject.getAadharNo__c());
		
		richInput.put(Constants.SFRequest.AssessmentYear__c, salesForceTemplateObject.getAssessmentYear__c());
		richInput.put(Constants.SFRequest.AssessmentYearOld__c, salesForceTemplateObject.getAssessmentYearOld__c());
		richInput.put(Constants.SFRequest.City__c, salesForceTemplateObject.getCity__c());
		richInput.put(Constants.SFRequest.CityOld__c, salesForceTemplateObject.getCityOld__c());
		
		richInput.put(Constants.SFRequest.CurrentYearLoss__c, salesForceTemplateObject.getCurrentYearLoss__c());
		richInput.put(Constants.SFRequest.CurrentYearLossOld__c, salesForceTemplateObject.getCurrentYearLossOld__c());

		
		richInput.put(Constants.SFRequest.Date__c, salesForceTemplateObject.getDate__c());
		richInput.put(Constants.SFRequest.DateOld__c, salesForceTemplateObject.getDateOld__c());
		
		richInput.put(Constants.SFRequest.DeductionUnderChapterVI__c, salesForceTemplateObject.getDeductionUnderChapterVI__c());
		richInput.put(Constants.SFRequest.DeductionUnderChapterVIOld__c, salesForceTemplateObject.getDeductionUnderChapterVIOld__c());

		
		richInput.put(Constants.SFRequest.DesignationofAO__c, salesForceTemplateObject.getDesignationofAO__c());
		richInput.put(Constants.SFRequest.DesignationofAOOld__c, salesForceTemplateObject.getDesignationofAOOld__c());
		richInput.put(Constants.SFRequest.DOBOrAge__c, salesForceTemplateObject.getDOBOrAge__c());
		richInput.put(Constants.SFRequest.DOBOrAgeOld__c, salesForceTemplateObject.getDOBOrAgeOld__c());
		richInput.put(Constants.SFRequest.EfillingAcknowledgementNo__c, salesForceTemplateObject.getEfillingAcknowledgementNo__c());
		richInput.put(Constants.SFRequest.EfillingAcknowledgementNoOld__c, salesForceTemplateObject.getEfillingAcknowledgementNoOld__c());
		
		richInput.put(Constants.SFRequest.ExemptIncome__c, salesForceTemplateObject.getExemptIncome__c());
		richInput.put(Constants.SFRequest.ExemptIncomeOld__c, salesForceTemplateObject.getExemptIncomeOld__c());
		
		
		richInput.put(Constants.SFRequest.FatherName__c, salesForceTemplateObject.getFatherName__c());
		richInput.put(Constants.SFRequest.FatherNameOld__c, salesForceTemplateObject.getFatherNameOld__c());
		richInput.put(Constants.SFRequest.FirstName__c, salesForceTemplateObject.getFirstName__c());
		richInput.put(Constants.SFRequest.FirstNameOld__c, salesForceTemplateObject.getFirstNameOld__c());
		
		richInput.put(Constants.SFRequest.FormNoWhichHasBeenTransmitted__c, salesForceTemplateObject.getFormNoWhichHasBeenTransmitted__c());
		richInput.put(Constants.SFRequest.FormNoWhichHasBeenTransmittedOld__c, salesForceTemplateObject.getFormNoWhichHasBeenTransmittedOld__c());

		
		richInput.put(Constants.SFRequest.FullName__c, salesForceTemplateObject.getFullName__c());
		richInput.put(Constants.SFRequest.FullNameOld__c, salesForceTemplateObject.getFullNameOld__c());
		richInput.put(Constants.SFRequest.Gender__c, salesForceTemplateObject.getGender__c());
		richInput.put(Constants.SFRequest.GenderOld__c, salesForceTemplateObject.getGenderOld__c());
		richInput.put(Constants.SFRequest.GrossTotalIncome__c, salesForceTemplateObject.getGrossTotalIncome__c());
		richInput.put(Constants.SFRequest.GrossTotalIncomeOld__c, salesForceTemplateObject.getGrossTotalIncomeOld__c());
		
		richInput.put(Constants.SFRequest.InterestPayable__c, salesForceTemplateObject.getInterestPayable__c());
		richInput.put(Constants.SFRequest.InterestPayableOld__c, salesForceTemplateObject.getInterestPayableOld__c());

		
		richInput.put(Constants.SFRequest.LastName__c, salesForceTemplateObject.getLastName__c());
		richInput.put(Constants.SFRequest.LastNameOld__c, salesForceTemplateObject.getLastNameOld__c());
		
		richInput.put(Constants.SFRequest.NetTaxPayable__c, salesForceTemplateObject.getNetTaxPayable__c());
		richInput.put(Constants.SFRequest.NetTaxPayableOld__c, salesForceTemplateObject.getNetTaxPayableOld__c());

		
		richInput.put(Constants.SFRequest.OriginalOrRevised__c, salesForceTemplateObject.getOriginalOrRevised__c());
		richInput.put(Constants.SFRequest.OriginalOrRevisedOld__c, salesForceTemplateObject.getOriginalOrRevisedOld__c());
		richInput.put(Constants.SFRequest.PANNo__c, salesForceTemplateObject.getPANNo__c());
		richInput.put(Constants.SFRequest.PANNoOld__c, salesForceTemplateObject.getPANNoOld__c());
		
		richInput.put(Constants.SFRequest.Place__c, salesForceTemplateObject.getPlace__c());
		richInput.put(Constants.SFRequest.PlaceOld__c, salesForceTemplateObject.getPlaceOld__c());
		richInput.put(Constants.SFRequest.Refund__c, salesForceTemplateObject.getRefund__c());
		richInput.put(Constants.SFRequest.RefundOld__c, salesForceTemplateObject.getRefundOld__c());
		richInput.put(Constants.SFRequest.SelfAsssessmentTax__c, salesForceTemplateObject.getSelfAsssessmentTax__c());
		richInput.put(Constants.SFRequest.SelfAsssessmentTaxOld__c, salesForceTemplateObject.getSelfAsssessmentTaxOld__c());

		
		richInput.put(Constants.SFRequest.State__c, salesForceTemplateObject.getState__c());
		richInput.put(Constants.SFRequest.StateOld__c, salesForceTemplateObject.getStateOld__c());
		richInput.put(Constants.SFRequest.Status__c, salesForceTemplateObject.getStatus__c());
		richInput.put(Constants.SFRequest.StatusOld__c, salesForceTemplateObject.getStatusOld__c());
		
		richInput.put(Constants.SFRequest.TaxPayable__c, salesForceTemplateObject.getTaxPayable__c());
		richInput.put(Constants.SFRequest.TaxPayableOld__c, salesForceTemplateObject.getTaxPayableOld__c());
		richInput.put(Constants.SFRequest.TCS__c, salesForceTemplateObject.getTCS__c());
		richInput.put(Constants.SFRequest.TCSOld__c, salesForceTemplateObject.getTCSOld__c());
		richInput.put(Constants.SFRequest.TDC__c, salesForceTemplateObject.getTDC__c());
		richInput.put(Constants.SFRequest.TDCOld__c, salesForceTemplateObject.getTDCOld__c());
		richInput.put(Constants.SFRequest.TotalIncome__c, salesForceTemplateObject.getTotalIncome__c());
		richInput.put(Constants.SFRequest.TotalIncomeOld__c, salesForceTemplateObject.getTotalIncomeOld__c());
		richInput.put(Constants.SFRequest.TotalTaxAndInterestPayable__c, salesForceTemplateObject.getTotalTaxAndInterestPayable__c());
		richInput.put(Constants.SFRequest.TotalTaxAndInterestPayableOld__c, salesForceTemplateObject.getTotalTaxAndInterestPayableOld__c());
		richInput.put(Constants.SFRequest.TotalTaxesPaid__c, salesForceTemplateObject.getTotalTaxesPaid__c());
		richInput.put(Constants.SFRequest.TotalTaxesPaidOld__c, salesForceTemplateObject.getTotalTaxesPaidOld__c());

		
		richInput.put(Constants.SFRequest.VoterCardNo__c, salesForceTemplateObject.getVoterCardNo__c());
		richInput.put(Constants.SFRequest.VoterCardNoOld__c, salesForceTemplateObject.getVoterCardNoOld__c());
		richInput.put(Constants.SFRequest.ZipCodeOld__c, salesForceTemplateObject.getZipCodeOld__c());
		richInput.put(Constants.SFRequest.ZipCode__c, salesForceTemplateObject.getZipCode__c());
		
		
		String url =  Constants.SFRequest.urlValue+salesforcerecordID;
		
		JSONObject  batchRequestsObject = new JSONObject();
		batchRequestsObject.put(Constants.SFRequest.method,Constants.SFRequest.methodType);
		batchRequestsObject.put(Constants.SFRequest.url, url);
		batchRequestsObject.put(Constants.SFRequest.richInput, richInput);
		
		JSONArray batchRequests = new JSONArray();
		batchRequests.put(batchRequestsObject);	
		
		requestBody.put(Constants.SFRequest.batchRequests, batchRequests);	
		return requestBody;
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
