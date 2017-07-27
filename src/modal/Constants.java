package modal;

public class Constants {
	
	
	
	//Inside servlet Processing2
	public static final String imgFile = "image";
	
	//request attributes parameter
	public static final String fileType = "fileType";
	public static final String uploadImage = "Upload Image";
	public static final String imagePreprocessing = "Image Preprocessing";
	public static final String base64conversion = "Base 64 conversion";
	public static final String visionAPICall = "Vision API Call";
	public static final String imgBase64 = "imgBase64";
	public static final String document = "document";
	public static final String templating = "Templating";
	public static final String displaydocument = "displaydocument";
	public static final String coordinates = "coordinates";
	public static final String jsonCoord = "jsonCoord";
	public static final String description = "Description";
	
	
	//Vision API response JSON Keys
	public static class VisionResponse{
		public static final String body = "body";
		public static final String responses = "responses";
		public static final String textAnnotations = "textAnnotations";
		public static final String description = "description";	
		public static final String boundingPoly = "boundingPoly";
		public static final String vertices = "vertices";
		public static final String x = "x";
		public static final String y = "y";
	}
	
	//Entity type constants Google NLP
	public static class NLPResponse{
		public static final String non = "Non";
		public static final String person = "Person";
		public static final String body = "body";
		public static final String entities = "entities";
		public static final String name = "name";
		public static final String type = "type";
	}
	
	//Vision API constants
	public static final String visionApiUrl = "https://vision.googleapis.com/v1/images:annotate?";
	public static final String visionApiKey = "key=AIzaSyC3IyBtatcC3pWGseGIfS3kfxqSpnaUUNA";
	
	//NLP API constants
	public static final String nlpApiUrl = "https://language.googleapis.com/v1beta2/documents:analyzeEntities?";
	public static final String nlpApiKey = "key=AIzaSyBvIc_jHtviAsiXwQbQbAY3LdMdhY2BBQ8";	
	
	//Proxy settings
	public static final String proxyHost = "ptproxy.persistent.co.in";
	public static final String proxyPort = "8080";

	//date format
	public static final String dateFormatSlash = "dd/MM/yyyy";
	public static final String dateFormatDashed = "dd-MM-yyyy";
	
	//gender
	public static final String male = "MALE";
	public static final String female = "FEMALE";
	
	public static final String birth = "Birth";
	public static final String year = "Year";
	public static final String date = "Date";
	public static final String dob = "DOB";
	public static final String colon = ":";
	public static final String india = "INDIA";
	public static final String govt = "GOVTOF";
	public static final String department = "DEPARTMENT";
	public static final String ITdepartment = "INCOME TAX DEPARTMENT";
	

	//Pan Card Constants
	public static class PanCard{
		public static final String panCard = "PanCard";
		public static final String name = "Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String panNumber = "Pan Number";
		public static final String pan = "PERMANENT ACCOUNT NUMBER";
		public static final String father = "Father";
		public static final String panAbb = "PAN";
		public static final String tax = "Tax";
	}
	//Aadhar Card Constants
	public static class AadharCardPage1{
		public static final String aadharCard = "AadharCardPage1";
		public static final String name = "Name";
		public static final String year = "Year of Birth";
		public static final String dob = "DOB";
		public static final String gender = "Gender";
		public static final String aadharNumber = "Aadhar Number";
		public static final String government = "Government";
		public static final String address = "Address";
		public static final String father = "Father";
		public static final String unique = "Unique";
		public static final String authority = "Authority";
		public static final String identification = "Identification";
	}
	//Driving License Constants
	public static class DrivingLicense{
		public static final String drivingLicense = "DrivingLicense";
		public static final String name = "Name";
		public static final String middleName = "S/D/W of";
		public static final String address = "Address";
		public static final String dob = "DOB";
		public static final String pin = "PIN";
		public static final String bloodGroup = "Blood Group";
		public static final String add = "add";
		public static final String of = "of";
		public static final String driving = "Driving";
	}
	//Voter Card Constants
	public static class VoterCard{
		public static final String voterCard = "VoterCard";
		public static final String electorName = "Elector's Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String sex = "Sex";
		public static final String voterId = "Voter ID";
		public static final String card = "Card";
		public static final String elector = "Elector";
		public static final String name = "Name";
		public static final String father = "Father";
	}
	
	//TRY all combinations of INDIA
	public static final String india1 = "INDIA";
	public static final String india2 = "INDLA";
	public static final String india3 = "LNDIA";
	public static final String india4 = "LNDLA";
	public static final String india5 = "INOIA";
	public static final String india6 = "INOLA";
	public static final String india7 = "LNOIA";
	public static final String india8 = "LNOLA";
	
}
