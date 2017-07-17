package modal;

public class Constants {
	
	
	
	//Inside servlet Processing2
	public static final String imgFile = "image.jpg";
	
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
	
	//Vision API constants
	public static final String visionApiUrl = "https://vision.googleapis.com/v1/images:annotate?";
	public static final String visionApiKey = "key=AIzaSyC3IyBtatcC3pWGseGIfS3kfxqSpnaUUNA";
	
	
	//Proxy settings
	public static final String proxyHost = "ptproxy.persistent.co.in";
	public static final String proxyPort = "8080";

	//date format
	public static final String dateFormatSlash = "dd/MM/yyyy";
	public static final String dateFormatDashed = "dd-MM-yyyy";
	
	//gender
	public static final String male = "Male";
	public static final String female = "Female";
	
	public static final String birth = "Birth";
	public static final String colon = ":";
	

	//Pan Card Constants
	public static class PanCard{
		public static final String panCard = "PanCard";
		public static final String name = "Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String panNumber = "Pan Number";
	}
	//Aadhar Card Constants
	public static class AadharCardPage1{
		public static final String aadharCard = "AadharCardPage1";
		public static final String name = "Name";
		public static final String year = "Year of Birth";
		public static final String dob = "DOB";
		public static final String gender = "Gender";
		public static final String aadharNumber = "Aadhar Number";
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
	}
	//Aadhar Card Constants
	public static class VoterCard{
		public static final String voterCard = "VoterCard";
		public static final String electorName = "Elector's Name";
		public static final String fatherName = "Father's Name";
		public static final String dob = "DOB";
		public static final String sex = "Sex";
		public static final String voterId = "Voter ID";
	}
	
	
}
