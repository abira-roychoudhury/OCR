package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import modal.Constants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apiClass.DocumentTemplating;
import apiClass.ImageEnhancement;
import apiClass.TimestampLogging;
import apiClass.VisionAPICall;
/**
 * Servlet implementation class Processing2
 */
@WebServlet("/Processing_OCR")
public class Processing_OCR extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init( ){
		
	}
	
	public Processing_OCR() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//Initialization
		Date start = new Date(),end = new Date();
		TimestampLogging  timelogging = new TimestampLogging();		
		String fileName = "",fileType="",descriptionStr="",filePath="";
		String visionTextDetectionType = Constants.VisionRequest.textDetection;
		JSONArray textAnnotationArray = new JSONArray();
		String imgName = Constants.imgFile+start.getTime()+Constants.dot+Constants.jpg;
		File imgFile = new File(imgName);
		
		//uploading the image file
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);  // Create a new file upload handler
		try{ 
			List fileItems = upload.parseRequest(request);  // Parse the request to get file items.
			Iterator fileItemsIterator = fileItems.iterator();  // Process the uploaded file items
			while ( fileItemsIterator.hasNext () ) 
			{
				FileItem fileItem = (FileItem)fileItemsIterator.next();
				if ( !fileItem.isFormField () )	
				{
					fileName = fileItem.getName();					 
					start = new Date();
					fileItem.write(imgFile);      //writing a temporary file
					fileItem.delete();
					filePath = imgFile.getAbsolutePath();
					end = new Date();			          
				}
				else{
					fileType = fileItem.getString();
					request.setAttribute(Constants.fileType, fileType);
				}
			}
		}
		catch(Exception ex) 
		{
			System.out.println(ex);
			ex.printStackTrace();
		} 
		System.out.println("imgFile "+imgFile);

		//uploading image completed logging upload image
		timelogging.fileDesc(fileName, fileType);
		int timeDifference = timelogging.fileLog(Constants.uploadImage, start, end);
		request.setAttribute(Constants.uploadImage, timeDifference);

		//Calling ImageEnhancement and getting back a preprocessed base64 image string
		start = new Date();		          
		ImageEnhancement ie = new ImageEnhancement();
		String processedImgBase64= ie.imagePreprocessing(filePath, fileType);	
		end = new Date();
		timeDifference = timelogging.fileLog(Constants.base64conversion, start, end);
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
		request.setAttribute(Constants.visionAPICall, timeDifference);


		//GET textAnnotation as JSON Array
		textAnnotationArray = getTextAnnotationArray(result);
		
		//GET description String from textAnnotation JSOANArray
		descriptionStr = getDescription(textAnnotationArray);
		

		//set description
		request.setAttribute(Constants.description, descriptionStr);	

		//checking for correct File type
		if(checkFileType(descriptionStr, fileType))
		{	
			
			//Parsing the description as per the template
			start = new Date();
			LinkedHashMap<String,Object> document = new DocumentTemplating().parseContent(textAnnotationArray,fileType,filePath);
			request.setAttribute(Constants.document, document);
			end = new Date();
			timeDifference = timelogging.fileLog(Constants.templating, start, end);
			request.setAttribute(Constants.templating, timeDifference);
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
			System.out.println("json "+jsonCoord);
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
			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewImage.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			//type mismatch error report
			response.setContentType(Constants.contentType);
			PrintWriter out = response.getWriter();
			out.println(Constants.fileTypeMismatch1);
			if(fileType.equals(Constants.AadharCardPage1.aadharCard))
				out.println(Constants.fileTypeMismatchAadharCard);				
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
		
		if(fileType.equals(Constants.PanCard.panCard) && ( descriptionStr.toLowerCase().contains(Constants.PanCard.income.toLowerCase()) || descriptionStr.toLowerCase().contains(Constants.PanCard.tax.toLowerCase())) && !descriptionStr.contains(Constants.ITReturn.itrv))
			validFileType = true;
		else if(fileType.equals(Constants.VoterCard.voterCard) && descriptionStr.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()))
			validFileType = true;
		else if(fileType.equals(Constants.DrivingLicense.drivingLicense) && descriptionStr.toLowerCase().contains(Constants.DrivingLicense.driving.toLowerCase()))
			validFileType = true;
		else if(fileType.equals(Constants.AadharCardPage1.aadharCard) && hasAadharNumber(descriptionStr)) 
			validFileType = true;
		else if(fileType.equals(Constants.ITReturn.iTReturn) && descriptionStr.contains(Constants.ITReturn.itrv))
			validFileType = true;
	
		return validFileType;
	}

}

