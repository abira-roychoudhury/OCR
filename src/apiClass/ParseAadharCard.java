package apiClass;

import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.EnumSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;












import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.AadharCard;
import templates.AadharCardAddress;
import templates.AadharCardCoord;

public class ParseAadharCard {

	public AadharCard parseAadharCard(JSONArray textAnnotationArray,String filePath){
		AadharCard obj = new AadharCard();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");
			obj = parseContent(descriptionStr,filePath,obj);		
			obj = parseCoord(textAnnotationArray,obj);	
		}catch(JSONException je){ je.printStackTrace();
		}catch(Exception e){ e.printStackTrace();
		}
		return obj;
	}


	public AadharCard parseCoord(JSONArray textAnnotationArray,AadharCard obj)
	{
		AadharCardCoord coord = new AadharCardCoord();
		int n=0,f=0,yr=0,d=0,g=0,a=0,ad=0,i=1;
		int nl=0,fl=0,yrl=0,dl=0,gl=0,al=0,adl=0;
		String year =  Integer.toString(obj.getYearOfBirth());

		for(;i<textAnnotationArray.length();i++)
		{
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString(Constants.VisionResponse.description);

			//setting the coordinates for name
			if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl< obj.getName().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(n==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
					n++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
				}
				nl = nl+description.length()+1;
			}
			
			//setting the coordinates for father's name
			else if(Arrays.asList(obj.getFatherName().split("\\s+")).contains(description) && fl< obj.getFatherName().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(f==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
					f++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
				}
				fl = fl+description.length()+1;
			}

			//setting the coordinates for year of birth
			else if(obj.getYearOfBirth()!=0 && year.contains(description) && yrl<year.length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);

				if(yr==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setYearOfBirth(x, 0, j);
						coord.setYearOfBirth(y, 1, j);
					}
					yr++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setYearOfBirth(x, 0, j);
						coord.setYearOfBirth(y, 1, j);
					}
				}
				yrl = yrl+description.length();
			}

			//setting the coordinates for date of birth
			else if(obj.getDobDisplay().contains(description) && dl< obj.getDobDisplay().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(d==0)
				{
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
					d++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
				}
				dl = dl+description.length();
			}

			//setting the coordinates for gender
			else if(obj.getGender().toLowerCase().contains(description.toLowerCase()) && gl< obj.getGender().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(g==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setGender(x, 0, j);
						coord.setGender(y, 1, j);
					}
					g++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setGender(x, 0, j);
						coord.setGender(y, 1, j);
					}
				}
				gl = gl+description.length();	
			}

			//setting the coordinates for aadhar card number
			else if(Arrays.asList(obj.getAadharNumber().split("\\s+")).contains(description) && al< obj.getAadharNumber().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(a==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAadharNumber(x, 0, j);
						coord.setAadharNumber(y, 1, j);
					}
					a++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAadharNumber(x, 0, j);
						coord.setAadharNumber(y, 1, j);
					}
				}
				al = al+description.length()+1;	
			}
			
			//setting the coordinates for address
			else if(Arrays.asList(obj.getAddress().split("\\s+")).contains(description) && adl< obj.getAddress().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(ad==0)
				{
					for(int j=0;j<4;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
					ad++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
				}
				adl = adl+description.length()+1;
			}
			
		}
		obj.setCoordinates(coord);
		return obj;
	}


	public AadharCard parseContent(String content,String filePath,AadharCard obj)throws WriterException, IOException
	{
		/* */

		int year = 0,d=0;
		String uid="",name="",fname = "",gender="",aadharNumber="",dobstr="",address="";
		Calendar cal = Calendar.getInstance();
		AadharCardAddress addr = new AadharCardAddress();

		String resultQR;
		try {

			resultQR = QRScan.scanQR(filePath);
			System.out.println(resultQR);
			if(resultQR.isEmpty() || resultQR.matches("^[0-9]{1,12}$"))
				throw new BarCodeException();

			if(resultQR.contains("dob")){
				dobstr=resultQR.substring(resultQR.lastIndexOf("dob=\"")+5,resultQR.lastIndexOf("\""));
				SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormatSlash);
				try {
					cal.setTime(sdf.parse(dobstr));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				d=1;
			}

			String tokens[] = resultQR.split("\" ");
			for(String token : tokens){
				token=token.trim();
				if(token.contains("uid"))
					uid=token.substring(token.lastIndexOf("=")+2);
				else if(token.contains("name"))
					name=token.substring(token.lastIndexOf("=")+2);
				else if(token.contains("father"))
					fname=token.substring(token.lastIndexOf("=")+2);
				else if(token.contains("gender"))
					gender=(token.substring(token.lastIndexOf("=")+2).equals("M")?"Male":"Female");
				else if(token.contains("yob"))
					year=Integer.parseInt(token.substring(token.lastIndexOf("=")+2));

				else if(token.contains("co"))
					addr.setCo(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("house"))
					addr.setHouse(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("street"))
					addr.setStreet(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("lm"))
					addr.setLm(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("loc"))
					addr.setLoc(token.substring(token.lastIndexOf("=")+2));
				else if(token.contains("vtc"))
					addr.setVtc(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("po"))
					addr.setPo(token.substring(token.lastIndexOf("=")+2));
				else if(token.contains("dist"))
					addr.setDist(token.substring(token.lastIndexOf("=")+2));
				else if(token.contains("subdist"))
					addr.setSubdist(token.substring(token.lastIndexOf("=")+2));	
				else if(token.contains("state"))
					addr.setState(token.substring(token.lastIndexOf("=")+2));
				else if(token.contains("pc"))
				{
					if(d==0)
						addr.setPc(token.substring(token.lastIndexOf("=")+2,token.lastIndexOf("\"")));
					else
						addr.setPc(token.substring(token.lastIndexOf("=")+2));
				}
			}
			aadharNumber = uid.substring(0,4)+" "+uid.substring(4,8)+" "+uid.substring(8);
			obj.setAddress(addr);
		} catch (NotFoundException | BarCodeException e1) {
			System.out.println("couldn't detect QR code");
			content = content.concat("\\n EOF");
			String splitDesc[] = content.split("\\n");
			int i;
			for(i = 0;i<splitDesc.length;i++)
			{
				//System.out.println(splitDesc[i]);

				String number = extractNumber(splitDesc[i].replace(" ", "").trim());
				//System.out.println(number);
				if(number.length() == 14)
				{
					try
					{
						aadharNumber = number;
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if(splitDesc[i].contains(Constants.AadharCardPage1.dob))
				{
					try
					{
						SimpleDateFormat sdf;
						if(splitDesc[i].contains("/"))
						{
							sdf = new SimpleDateFormat(Constants.dateFormatSlash);
							dobstr = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.AadharCardPage1.dob)+3,
									splitDesc[i].lastIndexOf("/")+5);
							dobstr = dobstr.replace(Constants.colon, "").trim();
							cal.setTime(sdf.parse(dobstr));
						}						   
						else if(splitDesc[i].contains("-")){
							sdf = new SimpleDateFormat(Constants.dateFormatDashed);
							dobstr = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.AadharCardPage1.dob)+3,
									splitDesc[i].lastIndexOf("-")+5);
							dobstr = dobstr.replace(Constants.colon, "").trim();
							cal.setTime(sdf.parse(dobstr));
						}
					}
					catch(Exception e)
					{
						System.err.println(e);
						String dateArr[] = splitDesc[i].split(":");
						dobstr = dateArr[dateArr.length-1];
						System.out.println(dobstr);
					}
					
					name = splitDesc[i-1];	
					if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
					{
						fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
						name = splitDesc[i-2];
					}
				}
				else if(splitDesc[i].contains(Constants.birth) || splitDesc[i].contains(Constants.year))
				{
					try
					{
						String yearstr = extractDateNumber(splitDesc[i]);
						System.out.println(yearstr);
						yearstr = yearstr.substring(yearstr.length()-4,yearstr.length());

						year = Integer.parseInt(yearstr);

						name = splitDesc[i-1];
						if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
						{
							fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
							name = splitDesc[i-2];
						}
						int j = i;
						while(name.length() < 5){
							name = splitDesc[--j];
							if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
							{
								fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
								name = splitDesc[j-1];
							}
						}
							
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				else if(splitDesc[i].contains(Constants.dob))
				{
					try
					{
						String yearstr = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.birth)+5).replace(Constants.colon, "").trim();
						year = Integer.parseInt(yearstr);

						name = splitDesc[i-1];
						if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
						{
							fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
							name = splitDesc[i-2];
						}
						int j = i;
						while(name.length() < 5){
							name = splitDesc[--j];
							if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
							{
								fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
								name = splitDesc[j-1];
							}
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				else if(splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.address.toLowerCase()))
				{
					address = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.colon)+1);
					while(!splitDesc[i].matches("^.+?\\d{6}$") && i<splitDesc.length-1){
						i++;						
						address = address + "\n" + splitDesc[i];		
					}		
					AadharCardAddress completeAddr = new AadharCardAddress(address);
					obj.setAddress(completeAddr);
				}

				else if(splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.unique.toLowerCase()) ||
						splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.identification.toLowerCase()) ||
						splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.authority.toLowerCase()))
				{
						while(!splitDesc[i].matches("^.+?\\d{6}$") && i<splitDesc.length-1) i++;
						
						i++;
						address = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.colon)+1);
						while(!splitDesc[i].matches("^.+?\\d{6}$") && i<splitDesc.length-1){
							i++;						
							address = address + "\n" + splitDesc[i];		
						}
					
					
					AadharCardAddress completeAddr = new AadharCardAddress(address);
					obj.setAddress(completeAddr);
				}
			}

			try
			{
				if(content.toUpperCase().contains(Constants.female)) 
					gender = Constants.female;
				else if(content.toUpperCase().contains(Constants.male)) 
					gender = Constants.male;		
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		obj.setName(name);
		obj.setFatherName(fname);
		obj.setGender(gender);
		obj.setAadharNumber(aadharNumber);
		obj.setYearOfBirth(year);
		obj.setDob(cal);
		obj.setDobDisplay(dobstr);
		return obj;
	}

	//Extract number form String. Ignore all characters and iterate up to length 14 (Aadhaar plus 2 spaces)
	public static String extractNumber(final String str) 
	{                
		int i=0;
		if(str == null || str.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : str.toCharArray())
		{
			if(Character.isDigit(c) && sb.length()<14)
			{
				if(i>1 && i%4==0)
					sb.append(" ");
				sb.append(c);
				i++;
				found = true;
			} 
			else if(found)
			{
				// If we already found a digit before and this char is not a digit, stop looping
				break;                
			}
		}
		return sb.toString();
	}

	public static String extractDateNumber(final String str) 
	{                
		if(str == null || str.isEmpty()) return "";

		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : str.toCharArray())
		{
			if(Character.isDigit(c) && sb.length()<5)
			{
				sb.append(c);
				if(sb.length()==4)
					found = true;
			} 
			else if(found)
			{
				// If we already found a digit before and this char is not a digit, stop looping
				break;                
			}
		}
		return sb.toString();
	}

}
