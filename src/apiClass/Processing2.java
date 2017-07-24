package apiClass;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		//System.out.println("inside init : "+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date()));
	}
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Processing2() {
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
		// Check that we have a file upload request
		//System.out.println("inside post : "+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date()));
		//try{
		Date start = new Date(),end = new Date();
		TimestampLogging  tl = new TimestampLogging();
		String fileName = "",fileType="",descriptionStr="",filePath="";
		JSONArray textAnnotationArray = new JSONArray();
		String imgName = Constants.imgFile+start.getTime()+".jpg";
		File imgFile = new File(imgName);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		try{ 
			// Parse the request to get file items.
			List fileItems = upload.parseRequest(request);
			// Process the uploaded file items
			Iterator i = fileItems.iterator();
			while ( i.hasNext () ) 
			{
				FileItem fi = (FileItem)i.next();
				if ( !fi.isFormField () )	
				{
					fileName = fi.getName();
					//writing a temporary file 
					start = new Date();
					fi.write(imgFile); 
					fi.delete();
					filePath = imgFile.getAbsolutePath();
					end = new Date();			          
				}
				else{
					String fieldname = fi.getFieldName();
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


		System.out.println("ORIGINAL SIZE: " + imgFile.length()/1000 + " KB");
		boolean comp = false;
		double filesize = imgFile.length()/1000;
		File compressedImageFile = null;
		if(filesize > 1000)
		{
			comp = true;
			//-----------------------------------------------------------
			File input = new File(filePath);
			BufferedImage image = ImageIO.read(input);

			compressedImageFile = new File("compress.png");  
			OutputStream os = new FileOutputStream(compressedImageFile);

			Iterator<ImageWriter>writers = ImageIO.getImageWritersByFormatName("jpg");
			ImageWriter writer = (ImageWriter) writers.next();

			ImageOutputStream ios = ImageIO.createImageOutputStream(os);
			writer.setOutput(ios);

			ImageWriteParam param = writer.getDefaultWriteParam();
			// Check if canWriteCompressed is true
			if(param.canWriteCompressed()) 
			{
				param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
				param.setCompressionQuality(0.01f);
			}
			// End of check
			writer.write(null, new IIOImage(image, null, null), param);
			//--------------------------------------------------------------

			System.out.println("COMPRESSED SIZE: " + compressedImageFile.length()/1000 + " KB");
		}

		tl.fileDesc(fileName, fileType);
		int diff = tl.fileLog(Constants.uploadImage, start, end);
		request.setAttribute(Constants.uploadImage, diff);

		//Calling ImageEnhancement and getting back a preprocessed base64 image string
		start = new Date();		          
		ImageEnhancement ie = new ImageEnhancement();
		//String processedImgBase64 = ie.imagePreprocessing(filePath, fileType);		          

		String processedImgBase64 = "";
		if(comp)
			processedImgBase64 = ie.convertToBase64(compressedImageFile.getAbsolutePath());
		else
			processedImgBase64 = ie.convertToBase64(filePath);		          
		end = new Date();
		//diff = tl.fileLog(Constants.imagePreprocessing, start, end);
		//request.setAttribute(Constants.imagePreprocessing, diff);

		diff = tl.fileLog(Constants.base64conversion, start, end);
		request.setAttribute(Constants.base64conversion, diff);


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
			request.setAttribute(Constants.description, descriptionStr);
		}catch (JSONException e) {
			e.printStackTrace();
		}

		//checking for correct File type
		if(descriptionStr.toLowerCase().contains(Constants.ITdepartment.toLowerCase()) && fileType.equals(Constants.PanCard.panCard) ||
				descriptionStr.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()) && fileType.equals(Constants.VoterCard.voterCard) ||	
				descriptionStr.toLowerCase().contains(Constants.DrivingLicense.driving.toLowerCase()) && fileType.equals(Constants.DrivingLicense.drivingLicense) ||
				descriptionStr.toLowerCase().contains(Constants.AadharCardPage1.government.toLowerCase()) && fileType.equals(Constants.AadharCardPage1.aadharCard))
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
			//deleting image file
			imgFile.delete();


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


			RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewImage.jsp");
			dispatcher.forward(request, response);
		}
		else
		{
			response.setContentType("text/plain");
			PrintWriter out = response.getWriter();
			out.println("The Image file uploaded and the File Type selected does not match");
		}


	}

}
