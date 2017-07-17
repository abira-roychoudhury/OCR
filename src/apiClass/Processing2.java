package apiClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.opencv.core.Mat;

import templates.DrivingLicense;
import templates.PanCardCoord;
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
				  File imgFile = new File("image.jpg");
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
				          filePath = imgFile.getAbsolutePath();
				          end = new Date();			          
			         }
			         else{
			        	 String fieldname = fi.getFieldName();
			             fileType = fi.getString();
			             System.out.println("Uploaded File fieldname "+ fieldname);
		                 System.out.println("Uploaded File Template "+ fileType);
		                 request.setAttribute("fileType", fileType);
			          }
			        }
			       }catch(Exception ex) {
			        System.out.println(ex);
			        ex.printStackTrace();} 
			      
			      tl.fileDesc(fileName, fileType);
			      int diff = tl.fileLog("Uploading image into file", start, end);
			      request.setAttribute("Upload Image", diff);
			      
		          //Calling ImageEnhancement and getting back a preprocessed base64 image string
	      		  start = new Date();		          
	      		  ImageEnhancement ie = new ImageEnhancement();
		          //String processedImgBase64 = ie.imagePreprocessing(filePath, fileType);		          
		          String processedImgBase64 = ie.convertToBase64(filePath);		          
		          end = new Date();
		          diff = tl.fileLog("Image Preprocessing", start, end);
		          request.setAttribute("Image Preprocessing", diff);
		          
		          
		          //Calling Vision API
		          start = new Date();
		          VisionAPICall vac = new VisionAPICall();
		          JSONObject result = vac.performOCR(processedImgBase64);
		          end = new Date();
		          diff = tl.fileLog("Vision API Call", start, end);
		          request.setAttribute("Vision API Call", diff);
	          
			         
		  		try {			  			
		  			JSONObject body = new JSONObject(result.get("body"));
		  			String bodystring=result.getString("body");
		  			JSONObject bodyObject=new JSONObject(bodystring);
		  			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray("responses");
		  			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
		  			textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray("textAnnotations");
		  			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
		  			descriptionStr=firstObj.getString("description");
		  			//System.out.println("Description-"+descriptionStr);
		  			request.setAttribute("Description", descriptionStr);
		  		}catch (JSONException e) {
		  			e.printStackTrace();
		  		}
			          
			          
		          //Creating Base64 of original Image
		          FileInputStream fileInputStreamReader = new FileInputStream(imgFile);
	              byte[] bytes = new byte[(int)imgFile.length()];
	              fileInputStreamReader.read(bytes);
	              String imgBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");

		          String imgBase64Jsp = "data:image/jpg;base64,"+imgBase64;
		          request.setAttribute("imgBase64", imgBase64Jsp);
			         
			     //Parsing the description as per the template
			      start = new Date();
			      LinkedHashMap<String,Object> document = new DocumentTemplating().parseContent(textAnnotationArray,fileType);
			      request.setAttribute("document", document);
			      end = new Date();
			      diff = tl.fileLog("Templating", start, end);
			      request.setAttribute("Templating", diff);
			      tl.fileWrite();
			      
			      
			      LinkedHashMap<String,String> displayDocument = (LinkedHashMap<String,String>) document.get("displayDocument");
			      LinkedHashMap<String,int[][]> coordinates = (LinkedHashMap<String,int[][]>)document.get("coordinates");
			     			      
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
			      
			      System.out.println("jsonCoord : "+jsonCoord);
			      request.setAttribute("displaydocument", displayDocument);
			      request.setAttribute("coordinates", coordinates);
			      request.setAttribute("jsonCoord", jsonCoord);
			     
			     RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewImage.jsp");
			        dispatcher.forward(request, response);
				/*}catch(Exception e){
					 response.setContentType("text/plain");
					 PrintWriter out = response.getWriter();
					 out.println("An Error has occured");
				}*/
			        
			        
	}

}
