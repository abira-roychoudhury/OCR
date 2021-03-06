package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedHashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import modal.Constants;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apiClass.DocumentTemplating;
import apiClass.ImageEnhancement;
import apiClass.TimestampLogging;
import apiClass.VisionAPICall;

/**
 * Servlet implementation class Processing_SF
 */
@WebServlet("/Processing_SF")
public class Processing_SF extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public Processing_SF() {
		super();

	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgUploadBase64 = request.getParameter("file");
		String fileType = request.getParameter("fileType");
		String salesforcerecordID = request.getParameter("salesforcerecordID");
		System.out.println("salesforceid :"+salesforcerecordID);
		//System.out.println("###########################\n"+imgUploadBase64);
		request.setAttribute("salesforcerecordID",salesforcerecordID);
		request.setAttribute(Constants.fileType, fileType);
		boolean proxyError = false; 
		
		//Initialization
		Date start = new Date(),end = new Date();
		TimestampLogging  timelogging = new TimestampLogging();		
		String fileName = "",descriptionStr="";
		String visionTextDetectionType = Constants.VisionRequest.textDetection;
		JSONArray textAnnotationArray = new JSONArray();

		String imgName = Constants.imgFile+start.getTime()+Constants.dot+Constants.jpg;
		File imgFile = new File(imgName);
		String filePath = imgFile.getAbsolutePath();
		//converting base64 image into a file
		byte imageByte[] = Base64.decodeBase64(imgUploadBase64);
		//System.out.println("image Byte : "+imageByte);
		FileOutputStream fos = new FileOutputStream(filePath); 
		fos.write(imageByte); 
		fos.close();	

		//uploading image completed logging upload image
		timelogging.fileDesc(fileName, fileType);
		int timeDifference = timelogging.fileLog(Constants.uploadImage, start, end);
		int totalTimeTaken = timeDifference;
		request.setAttribute(Constants.uploadImage, timeDifference);

		//Calling ImageEnhancement and getting back a preprocessed base64 image string
		start = new Date();		          
		ImageEnhancement ie = new ImageEnhancement();
		String processedImgBase64= ie.imagePreprocessing(filePath, fileType);	
		end = new Date();
		timeDifference = timelogging.fileLog(Constants.base64conversion, start, end);
		totalTimeTaken += timeDifference;
		request.setAttribute(Constants.base64conversion, timeDifference);

		//compressed image property 
		byte[] decoded = Base64.decodeBase64(processedImgBase64.getBytes());
		ImageIcon img = new ImageIcon(decoded);
		request.setAttribute(Constants.compressWidth, img.getIconWidth());
		request.setAttribute(Constants.compressHeight, img.getIconHeight());

		//Calling Vision API
		start = new Date();
		VisionAPICall vac = new VisionAPICall();
		if(fileType.equals(Constants.ITReturn.iTReturn))
			visionTextDetectionType = Constants.VisionRequest.documentTextDetection;
		JSONObject result = vac.performOCR(processedImgBase64,visionTextDetectionType);
		end = new Date();
		timeDifference = timelogging.fileLog(Constants.visionAPICall, start, end);
		totalTimeTaken += timeDifference;
		request.setAttribute(Constants.visionAPICall, timeDifference);


		//GET textAnnotation as JSON Array
		textAnnotationArray = getTextAnnotationArray(result);

		//GET description String from textAnnotation JSOANArray
		descriptionStr = getDescription(textAnnotationArray);
		
		/*//set proper error message if the vision api response was improper
		if(descriptionStr.isEmpty())
			proxyError = true;*/


		//set description
		request.setAttribute(Constants.description, descriptionStr);	
		
		//System.out.println("######################################################fileType "+fileType);
		//System.out.println("######################################################description string "+descriptionStr);

		//checking for correct File type
		if(checkFileType(descriptionStr, fileType))
		{	

			//Parsing the description as per the template
			start = new Date();
			LinkedHashMap<String,Object> document = new DocumentTemplating().parseContent(textAnnotationArray,fileType,filePath);
			request.setAttribute(Constants.document, document);
			end = new Date();
			timeDifference = timelogging.fileLog(Constants.templating, start, end);
			totalTimeTaken += timeDifference;
			request.setAttribute(Constants.templating, timeDifference);
			timelogging.fileLog("Total Time Taken", totalTimeTaken);
			timelogging.fileWrite();


			//retrieving the templates
			LinkedHashMap<String,String> displayDocument = (LinkedHashMap<String,String>) document.get(Constants.displaydocument);
			LinkedHashMap<String,int[][]> coordinates = (LinkedHashMap<String,int[][]>)document.get(Constants.coordinates);

			String jsonCoord = "{";
			for(String key : coordinates.keySet())
			{
				int coordinate[][] = coordinates.get(key);
				jsonCoord = jsonCoord+"\""+key+"\":[";
				for(int i=0;i<4;i++)
					jsonCoord = jsonCoord+"{\"x\":"+coordinate[0][i]+",\"y\":"+coordinate[1][i]+"},";
				jsonCoord = jsonCoord.substring(0, jsonCoord.length() - 1);
				jsonCoord = jsonCoord+"],";
			}
			jsonCoord = jsonCoord.substring(0, jsonCoord.length() - 1);
			jsonCoord = jsonCoord+"}";
			//System.out.println("json "+jsonCoord);
			request.setAttribute(Constants.displaydocument, displayDocument);
			request.setAttribute(Constants.coordinates, coordinates);
			request.setAttribute(Constants.jsonCoord, jsonCoord);



			//resizing original image if the resolution is high
			imgFile = ie.imageResize(imgFile);

			//Creating Base64 of original Image
			FileInputStream fileInputStreamReader = new FileInputStream(imgFile);
			byte[] bytes = new byte[(int)imgFile.length()];
			fileInputStreamReader.read(bytes);
			String imgBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");
			String imgBase64Jsp = "data:image/jpg;base64,"+imgBase64;
			request.setAttribute(Constants.imgBase64, imgBase64Jsp);

			//Closing ImageFile
			fileInputStreamReader.close();



			//dispatching request to jsp page
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewFormSF.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			//type mismatch error report
			response.setContentType(Constants.contentType);
			PrintWriter out = response.getWriter();
			if(proxyError)
				out.println(Constants.proxyError);
			else
			{
				out.println(Constants.fileTypeMismatch1);
				if(fileType.equals(Constants.AadharCardPage1.aadharCard))
					out.println(Constants.fileTypeMismatchAadharCard);	
			}						
		}
		//deleting image file
		System.out.println("Delete imgFile :"+imgFile.delete());
	}



	/* DESCRIPTION : checks if a String consist of Aadhar Number
	 * INPUT : String to be checked
	 * OUTPUT : Boolean
	 * */
	public static boolean hasAadharNumber(final String raw_string) 
	{                
		int index=0;
		if(raw_string == null || raw_string.isEmpty()) return false;
		StringBuilder stringBuilderObj = new StringBuilder();
		boolean found = false;
		for(char singleChar : raw_string.toCharArray())
		{
			if(Character.isDigit(singleChar) && stringBuilderObj.length()<12)
			{
				stringBuilderObj.append(singleChar);
				found = true;
			} 
			else if(singleChar!='\n' && singleChar!=' ' && found)
			{
				// If we already found a digit before and this char is not a digit or \n or space, stop looping
				break;                
			}
			index++;
		}
		if(stringBuilderObj.length()>10)
			return true;    //return true if the string found is of minimum length greater than 10
		else
		{
			if(stringBuilderObj.length()>=1)
				return hasAadharNumber(raw_string.substring(index));  //if the minimum length is 1 then recrusive call for the remaining length of the string

			else
				return false;  //when the entire string does not consist of the aadhar number
		}

	}



	/* DESCRIPTION : Get description string from textAnnotation JSONArray
	 * INPUT : textAnnotationArray
	 * OUTPUT : String
	 * */
	public static String getDescription(JSONArray textAnnotationArray){
		try {			  			
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");

			return descriptionStr;
		}catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}


	/* DESCRIPTION : Get textAnnotation from result body
	 * INPUT : result as JSONObject
	 * OUTPUT : textAnnotationArray
	 * */
	public static JSONArray getTextAnnotationArray(JSONObject result){
		try {
			JSONObject body = new JSONObject(result.get(Constants.VisionResponse.body));
			String bodystring=result.getString(Constants.VisionResponse.body);
			JSONObject bodyObject=new JSONObject(bodystring);
			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray(Constants.VisionResponse.responses);
			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
			JSONArray textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray(Constants.VisionResponse.textAnnotations);

			return textAnnotationArray;
		} catch (Exception e) {

			e.printStackTrace();

			return new JSONArray();
		}
	}


	/* DESCRIPTION : Check is the document is per the fileType selected
	 * INPUT : String description and String fileType
	 * OUTPUT : boolean 
	 * */
	public static boolean checkFileType(String descriptionStr,String fileType){

		boolean validFileType = false;

		if(fileType.equals(Constants.PanCard.panCard) && ( descriptionStr.toLowerCase().contains(Constants.PanCard.income.toLowerCase()) || descriptionStr.toLowerCase().contains(Constants.PanCard.tax.toLowerCase())) && !descriptionStr.contains(Constants.ITReturn.itr))
			validFileType = true;
		else if(fileType.equals(Constants.VoterCard.voterCard) && descriptionStr.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()))
			validFileType = true;
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense) && descriptionStr.toLowerCase().contains(Constants.DrivingLicense.driving.toLowerCase()))
			validFileType = true;
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard) && hasAadharNumber(descriptionStr)) 
			validFileType = true;
		else if(fileType.equals(Constants.ITReturn.iTReturn) && descriptionStr.toUpperCase().contains(Constants.ITReturn.itr.toUpperCase()))
			validFileType = true;
	
		return validFileType;
	}		

}
