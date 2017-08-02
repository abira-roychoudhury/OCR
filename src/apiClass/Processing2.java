package apiClass;

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
/**
 * Servlet implementation class Processing2
 */
@WebServlet("/Processing2")
public class Processing2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init( ){
		
	}
	
	public Processing2() {
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
		TimestampLogging  tl = new TimestampLogging();		
		String fileName = "",fileType="",descriptionStr="",filePath="";
		JSONArray textAnnotationArray = new JSONArray();
		String imgName = Constants.imgFile+start.getTime()+Constants.dot+Constants.jpg;
		File imgFile = new File(imgName);
		
		//uploading the image file
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);  // Create a new file upload handler
		try{ 
			List fileItems = upload.parseRequest(request);  // Parse the request to get file items.
			Iterator i = fileItems.iterator();  // Process the uploaded file items
			while ( i.hasNext () ) 
			{
				FileItem fi = (FileItem)i.next();
				if ( !fi.isFormField () )	
				{
					fileName = fi.getName();					 
					start = new Date();
					fi.write(imgFile);      //writing a temporary file
					fi.delete();
					filePath = imgFile.getAbsolutePath();
					end = new Date();			          
				}
				else{
					fileType = fi.getString();
					request.setAttribute(Constants.fileType, fileType);
				}
			}
		}
		catch(Exception ex) 
		{
			System.out.println(ex);
			ex.printStackTrace();
		} 

		//uploading image completed logging upload image
		tl.fileDesc(fileName, fileType);
		int diff = tl.fileLog(Constants.uploadImage, start, end);
		request.setAttribute(Constants.uploadImage, diff);

		//Calling ImageEnhancement and getting back a preprocessed base64 image string
		start = new Date();		          
		ImageEnhancement ie = new ImageEnhancement();
		String processedImgBase64= ie.imagePreprocessing(filePath, fileType);	
		end = new Date();
		diff = tl.fileLog(Constants.base64conversion, start, end);
		request.setAttribute(Constants.base64conversion, diff);
		
		//compressed image property 
		byte[] decoded = Base64.decodeBase64(processedImgBase64.getBytes());
        ImageIcon img = new ImageIcon(decoded);
        request.setAttribute(Constants.compressWidth, img.getIconWidth());
        request.setAttribute(Constants.compressHeight, img.getIconHeight());
        
		//Calling Vision API
		start = new Date();
		VisionAPICall vac = new VisionAPICall();
		JSONObject result = vac.performOCR(processedImgBase64);
		end = new Date();
		diff = tl.fileLog(Constants.visionAPICall, start, end);
		request.setAttribute(Constants.visionAPICall, diff);


		try {			  			
			JSONObject body = new JSONObject(result.get(Constants.VisionResponse.body));
			String bodystring=result.getString(Constants.VisionResponse.body);
			JSONObject bodyObject=new JSONObject(bodystring);
			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray(Constants.VisionResponse.responses);
			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
			textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray(Constants.VisionResponse.textAnnotations);
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");
			request.setAttribute(Constants.description, descriptionStr);		
		}catch (JSONException e) {
			e.printStackTrace();
		}

		//checking for correct File type
		if(( descriptionStr.toLowerCase().contains(Constants.PanCard.income.toLowerCase()) || descriptionStr.toLowerCase().contains(Constants.PanCard.tax.toLowerCase())) && fileType.equals(Constants.PanCard.panCard) ||
				descriptionStr.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()) && fileType.equals(Constants.VoterCard.voterCard) ||	
				descriptionStr.toLowerCase().contains(Constants.DrivingLicense.driving.toLowerCase()) && fileType.equals(Constants.DrivingLicense.drivingLicense) ||
				hasAadharNumber(descriptionStr) &&  fileType.equals(Constants.AadharCardPage1.aadharCard))
		{	
			
			//Creating Base64 of original Image
			FileInputStream fileInputStreamReader = new FileInputStream(imgFile);
			byte[] bytes = new byte[(int)imgFile.length()];
			fileInputStreamReader.read(bytes);
			String imgBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");
			String imgBase64Jsp = "data:image/jpg;base64,"+imgBase64;
			request.setAttribute(Constants.imgBase64, imgBase64Jsp);

			//Parsing the description as per the template
			start = new Date();
			LinkedHashMap<String,Object> document = new DocumentTemplating().parseContent(textAnnotationArray,fileType,filePath);
			request.setAttribute(Constants.document, document);
			end = new Date();
			diff = tl.fileLog(Constants.templating, start, end);
			request.setAttribute(Constants.templating, diff);
			tl.fileWrite();
			
			//Closing ImageFile
			fileInputStreamReader.close();
			
			//retrieving the templates
			LinkedHashMap<String,String> displayDocument = (LinkedHashMap<String,String>) document.get(Constants.displaydocument);
			LinkedHashMap<String,int[][]> coordinates = (LinkedHashMap<String,int[][]>)document.get(Constants.coordinates);

			String jsonCoord = "{";
			for(String key : coordinates.keySet())
			{
				int coord[][] = coordinates.get(key);
				jsonCoord = jsonCoord+"\""+key+"\":[";
				for(int i=0;i<4;i++)
					jsonCoord = jsonCoord+"{\"x\":"+coord[0][i]+",\"y\":"+coord[1][i]+"},";
				jsonCoord = jsonCoord.substring(0, jsonCoord.length() - 1);
				jsonCoord = jsonCoord+"],";
			}
			jsonCoord = jsonCoord.substring(0, jsonCoord.length() - 1);
			jsonCoord = jsonCoord+"}";
			System.out.println("json "+jsonCoord);
			request.setAttribute(Constants.displaydocument, displayDocument);
			request.setAttribute(Constants.coordinates, coordinates);
			request.setAttribute(Constants.jsonCoord, jsonCoord);

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
	public static boolean hasAadharNumber(final String str) 
	{                
		int i=0;
		if(str == null || str.isEmpty()) return false;
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : str.toCharArray())
		{
			if(Character.isDigit(c) && sb.length()<12)
			{
				sb.append(c);
				found = true;
			} 
			else if(c!='\n' && c!=' ' && found)
			{
				// If we already found a digit before and this char is not a digit or \n or space, stop looping
				break;                
			}
			i++;
		}
		if(sb.length()>10)
			return true;    //return true if the string found is of minimum length greater than 10
		else
		{
			if(sb.length()>=1)
				return hasAadharNumber(str.substring(i));  //if the minimum length is 1 then recrusive call for the remaining length of the string
			
			else
				return false;  //when the entire string does not consist of the aadhar number
		}
			
	}

}

