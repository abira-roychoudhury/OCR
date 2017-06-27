package apiClass;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.output.*;

/**
 * Servlet implementation class Processing
 */
@WebServlet("/Processing")
public class Processing extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024;
	private int maxMemSize = 4 * 1024;
	private File file ;

	
	
	public void init( ){
	      // Get the file location where it would be stored.
	      filePath = getServletContext().getInitParameter("file-upload");
	      System.out.println("inside init");
	   }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Check that we have a file upload request
		System.out.println("inside post");
	      isMultipart = ServletFileUpload.isMultipartContent(request);
	      response.setContentType("text/html");
	      java.io.PrintWriter out = response.getWriter( );
	      if( !isMultipart ){
	         out.println("<html>");
	         out.println("<head>");
	         out.println("<title>Servlet upload</title>");  
	         out.println("</head>");
	         out.println("<body>");
	         out.println("<p>No file uploaded</p>"); 
	         out.println("</body>");
	         out.println("</html>");
	         return;
	      }
	      DiskFileItemFactory factory = new DiskFileItemFactory();
	      
	      // Create a new file upload handler
	      ServletFileUpload upload = new ServletFileUpload(factory);

	      try{ 
	      // Parse the request to get file items.
	      List fileItems = upload.parseRequest(request);
		
	      // Process the uploaded file items
	      Iterator i = fileItems.iterator();

	      out.println("<html>");
	      out.println("<head>");
	      out.println("<title>Servlet upload</title>");  
	      out.println("</head>");
	      out.println("<body>");
	      while ( i.hasNext () ) 
	      {
	         FileItem fi = (FileItem)i.next();
	         if ( !fi.isFormField () )	
	         {
	        	 System.out.println("inside the iterator");
	            // Get the uploaded file parameters
	            String fieldName = fi.getFieldName();
	            String fileName = fi.getName();
	            String contentType = fi.getContentType();
	            boolean isInMemory = fi.isInMemory();
	            long sizeInBytes = fi.getSize();
	            // Write the file
	            if( fileName.lastIndexOf("\\") >= 0 ){
	               file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
	            }else{
	               file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
	           }
	           fi.write( file ) ;
	           out.println("Uploaded Filename: " + fileName + "<br>");
	         }
	         else{
	        	 String fieldname = fi.getFieldName();
	             String fieldvalue = fi.getString();
                 out.println("Uploaded File Template "+ fieldvalue);
	          }
	         }
	      
	      out.println("</body>");
	      out.println("</html>");
	      }catch(Exception ex) {
	        System.out.println(ex);	   } 
	}

}
