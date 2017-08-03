package apiClass;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ImageEnhancement {
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */		
	public Mat loadOpenCvImage(final String filePath) {
		//LOAD THE LIBRARY

		//File nativeFile = new File(Core.NATIVE_LIBRARY_NAME + ".dll");
		//System.load(nativeFile.getAbsolutePath());
		//System.out.println(System.getProperty("java.library.path"));
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//LOAD IMAGE IN GRAYSCALE
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    return imgMat;
	}
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */
	public Mat preprocess(Mat imgMat, String documentType){
		//THRESHOLDING PARAMETERS
	    final double min = 0, max = 255.0, threshold = 127.0;
	    //CREATE AN EMPTY IMAGE
	    Mat imgNew = imgMat;
	    if(documentType.equalsIgnoreCase("AadharCardPage1") || documentType.equalsIgnoreCase("AadharCardPage2")){
	    	//PERFORM GLOBAL THRESHOLDING
		    Imgproc.threshold(imgMat, imgMat, threshold, max, Imgproc.THRESH_BINARY);
		    //PERFORM ADAPTIVE THRESHOLDING USING MEAN FILTER
		    Imgproc.adaptiveThreshold(imgMat, imgNew, max, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 8);
		    
	    }
	    //PERFORM ADAPTIVE THRESHOLDING USING MEAN FILTER
	    //Imgproc.adaptiveThreshold(imgMat, imgNew, max, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 8);
	    return imgNew;
	}
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */
	public File saveImage(Mat img){
		Date d = new Date();
		File processedFile = new File("Img"+d.getTime()+".jpg");
        Imgcodecs.imwrite(processedFile.getAbsolutePath(), img);
		//System.out.println("New image saved at location : "+processedFile.getAbsolutePath());
		return processedFile;
	}
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */
	public String imagePreprocessing(String filepath, String documentType){
		Mat imgMat = loadOpenCvImage(filepath);
		//Mat imgMat2 = preprocess(imgMat, documentType);
		imgMat = imageCompress(imgMat);
		File img = saveImage(imgMat);
		String imgBase64 = convertToBase64(img.getAbsolutePath());
		
		//img.delete();
		return imgBase64;		
	}
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */
	public String convertToBase64(String filePath){
		String imageData = "";
		try{			
			Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);
			//Image
			imageData = Base64.encodeBase64URLSafeString(data);
		}catch(Exception e){
			System.err.println(e);
		}		
		return imageData;
	}
	
	/* DESCRIPTION : 
	 * INPUT : 
	 * OUTPUT : 
	 * */
	public Mat imageCompress(Mat img){
				long size = img.step1(0) * img.rows();
				//long size = img.total() * img.elemSize();
				while(size > 4000000)
				{
					Imgproc.resize(img, img, new Size(img.width()/2, img.height()/2));
					size = img.step1(0) * img.rows();
				}				
				return img;
			} 

	
}

