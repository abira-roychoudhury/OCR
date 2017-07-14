package apiClass;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.apache.commons.codec.binary.Base64;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;


public class ImageEnhancement {
			
	public Mat loadOpenCvImage(final String filePath) {
		//LOAD THE LIBRARY

		//File nativeFile = new File(Core.NATIVE_LIBRARY_NAME + ".dll");
		//System.load(nativeFile.getAbsolutePath());
		
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//LOAD IMAGE IN GRAYSCALE
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    return imgMat;
	}
	
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

	public File saveImage(Mat img){
		File processedFile = new File("gray.jpg");
        Imgcodecs.imwrite(processedFile.getAbsolutePath(), img);
		System.out.println("New image saved at location : "+processedFile.getAbsolutePath());
		return processedFile;
	}
	
	public String imagePreprocessing(String filepath, String documentType){
		Mat imgMat = loadOpenCvImage(filepath);
		Mat imgMat2 = preprocess(imgMat, documentType);
		//Mat imgMat3 = imageCompress(imgMat2);
		File img = saveImage(imgMat2);
		String imgBase64 = convertToBase64(img.getAbsolutePath());
		return imgBase64;		
	}

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
	
	public Mat imageCompress(Mat img){
				long size = img.step1(0) * img.rows();
				//long size = img.total() * img.elemSize();
				System.out.println(size);
				while(size > 800000)
				{
					Imgproc.resize(img, img, new Size(img.width()/2, img.height()/2));
					size = img.step1(0) * img.rows();
				}
				
				return img;
			} 

	
}

