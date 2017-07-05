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
/**
 * Servlet implementation class Processing2
 */
@WebServlet("/Processing2")
public class Processing2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file ;


	public void init( ){
	      System.out.println("inside init : "+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
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
				  System.out.println("inside post : "+ new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				  
				  String fileType="",descriptionStr="",filePath="";
				  File imgFile = new File("image.jpg");

			      isMultipart = ServletFileUpload.isMultipartContent(request);
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
				          System.out.println("inside the iterator for image : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				          
				          //writing a temporary file  
				          fi.write(imgFile); 
				          filePath = imgFile.getAbsolutePath();
				          
				          
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
				          
				          //Calling ImageEnhancement and getting back a preprocessed base64 image string
			      		  System.out.println("calling ImageEnhancement : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				          ImageEnhancement ie = new ImageEnhancement();
				          //String processedImgBase64 = ie.imagePreprocessing(filePath, fileType);
				          String processedImgBase64 = ie.convertToBase64(filePath);
				          System.out.println("returning ImageEnhancement : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				          
				          
				          //Calling Vision API
				          System.out.println("calling VisionAPICall : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				          VisionAPICall vac = new VisionAPICall();
				          JSONObject result = vac.performOCR(processedImgBase64);
				          System.out.println("returning VisionAPICall : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
				     
				          //request.setAttribute("jobj", jobj);
			          
			         
			  		try {
			  			
			  			JSONObject body = new JSONObject(result.get("body"));
			  			String bodytring=result.getString("body");
			  			JSONObject bodyObject=new JSONObject(bodytring);
			  			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray("responses");
			  			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
			  			JSONArray textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray("textAnnotations");
			  			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			  			descriptionStr=firstObj.getString("description");
			  			//System.out.println("Description-"+descriptionStr);
			  			request.setAttribute("Description", descriptionStr);
			  		}catch (JSONException e) {
			  			// TODO Auto-generated catch block
			  			e.printStackTrace();}
			          
			          
			          //Creating Base64 of original Image
			          FileInputStream fileInputStreamReader = new FileInputStream(imgFile);
		              byte[] bytes = new byte[(int)imgFile.length()];
		              fileInputStreamReader.read(bytes);
		              String imgBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");

			          String imgBase64Jsp = "data:image/jpg;base64,"+imgBase64;
			          request.setAttribute("imgBase64", imgBase64Jsp);
			         
			         
			      
			     //Parsing the description as per the template
			       System.out.println("calling Templating : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
			      LinkedHashMap<String,String> document = new DocumentTemplating().parseContent(descriptionStr,fileType);
			      request.setAttribute("document", document);
			      System.out.println("returning templating : "+new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()));
			      
			     
			     RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewImage.jsp");
			        dispatcher.forward(request, response);
			        
			        
	}

}
