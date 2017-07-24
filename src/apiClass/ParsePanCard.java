package apiClass;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.common.HybridBinarizer;

import templates.PanCard;
import templates.PanCardCoord;

public class ParsePanCard {

	public PanCard parsePanCard(JSONArray textAnnotationArray,String filePath) {
		PanCard obj = new PanCard();
		try {
			JSONObject firstObj = (JSONObject) textAnnotationArray.get(0);
			String descriptionStr = firstObj
					.getString(Constants.VisionResponse.description);
			obj = parseContent(descriptionStr,filePath,obj);
			obj = parseCoord(textAnnotationArray, obj);
		} catch (JSONException je) {
			je.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public PanCard parseCoord(JSONArray textAnnotationArray, PanCard obj) {
		int n = 0, f = 0, d = 0, p = 0, i = 1;
		int nl = 0, fl = 0, dl = 0, pl = 0;
		PanCardCoord coord = new PanCardCoord();

		for (; i < textAnnotationArray.length(); i++) {
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj
					.getString(Constants.VisionResponse.description);
			if (description.toUpperCase().contains("INDIA"))
				break;
		}

		for (; i < textAnnotationArray.length(); i++) {
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString(Constants.VisionResponse.description);
			
			if(description.contains("/"))
				continue;
			
			System.out.println(description);

			// setting the coordinates for name
			if (Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl < obj.getName().length()) {
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if (n == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
					n++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
				}
				nl = nl + description.length() + 1;
			}

			// setting the coordinates for father's name
			else if (Arrays.asList(obj.getFatherName().split("\\s+")).contains(
					description)
					&& fl < obj.getFatherName().length()) {
				JSONObject boundingPoly = jobj
						.getJSONObject(Constants.VisionResponse.boundingPoly);
				if (f == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
					f++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
				}
				fl = fl + description.length() + 1;
			}

			// setting the coordinates for date
			else if (obj.getDobDisplay().contains(description)
					&& dl < obj.getDobDisplay().length()) {
				JSONObject boundingPoly = jobj
						.getJSONObject(Constants.VisionResponse.boundingPoly);
				if (d == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
					d++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
				}
				dl = dl + description.length();
			}

			// setting the coordinates for PanCard number
			else if (obj.getPanNumber().equals(description)&& pl < obj.getPanNumber().length()) {
				JSONObject boundingPoly = jobj
						.getJSONObject(Constants.VisionResponse.boundingPoly);
				if (p == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setPanNumber(x, 0, j);
						coord.setPanNumber(y, 1, j);
					}
					p++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setPanNumber(x, 0, j);
						coord.setPanNumber(y, 1, j);
					}
				}
				pl = pl + description.length();
			}
		}

		obj.setCoordinates(coord);
		return obj;
	}

	public PanCard parseContent(String content, String filePath, PanCard obj) 
	{
		int i;
		String name = "", fname = "", dob = "", pan = "";
		Calendar cal = Calendar.getInstance();
		System.out.println("Inside parsecontent pan card " + content);
		
		if (!content.contains(Constants.PanCard.name)) 
		{ 
			// New type of PAN Card
			//System.out.println("inside new pan card");
			
			content = filterContent(content);
			
			String splitDesc[] = content.split("\\n");
			
			//System.out.println("Filtered:" + content);
			
			for (i = 0; i < splitDesc.length; i++) 
			{	
				//Our main point of check in "INDIA"
				//Try for all combinations of India characters that Vision API can mistake for
				if (splitDesc[i].indexOf(Constants.india1) > -1 ||
						splitDesc[i].indexOf(Constants.india2) > -1 ||
						splitDesc[i].indexOf(Constants.india3) > -1 ||
						splitDesc[i].indexOf(Constants.india4) > -1 ||
						splitDesc[i].indexOf(Constants.india5) > -1 ||
						splitDesc[i].indexOf(Constants.india6) > -1 ||
						splitDesc[i].indexOf(Constants.india7) > -1 ||
						splitDesc[i].indexOf(Constants.india8) > -1) 
				{			
					try 
					{
						//Incase of First name is clubbed with INCOME TAX block
						//Chances are that First name will come between INCOME TAX and GOVT OF INDIA
						//This logic extracts that name
						if(Arrays.asList(splitDesc).indexOf(Constants.ITdepartment) - Arrays.asList(splitDesc).indexOf(Constants.india) > 1)
						{
							System.out.println("NAME SOMEWHERE IN BETWEEN");
							int index = Arrays.asList(splitDesc).indexOf(Constants.ITdepartment) + 1; 
							System.out.println(index);
							name = splitDesc[index];
						}
						else
						{
							// extracting name
							//If there is DOB in that name ignore and
							//back to index
							name = splitDesc[++i];
							if(name.matches(".*\\d+.*"))
							{
								name = "";
								i--;
							}
						}
					}
					catch (Exception e) 
					{
						System.err.println(e);
					}
					
					try
					{
						// extracting father's name
						//If there is DOB in that father's name ignore and
						//back to index
						fname = splitDesc[++i];
						if(fname.matches(".*\\d+.*"))
						{
							fname = "";
							i--;
						}
					}
					catch (Exception e) 
					{
						System.err.println(e);
					}

					// extracting dob
					dob = splitDesc[++i];
					SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormatSlash);
					try 
					{
						cal.setTime(sdf.parse(dob));
						System.out.println("CALENDER" + cal);
						
						if(!cal.toString().matches("^[A-Z]$"))
						{
							System.out.println("MACTHES");
						}
					} 
					catch (Exception e) 
					{
						//If there is exception chances are that name has been set to this.
						//Iterate one back to push all string one above
						System.err.println(e);
						
						if(e.getMessage().toString().contains("Unparseable date") && !dob.contains("/") && !dob.matches(".*\\d+.*"))
						{
							System.out.println("trying something else");
							try
							{
								name = fname;
								fname = dob;
								dob = splitDesc[++i];
							}
							catch(Exception ez)
							{
								ez.printStackTrace();
							}
						}
					}

					try
					{
						// extracting Pan
						pan = splitDesc[i + 1];
						//Allow and error for +-1 for Number
						if (!pan.matches("^[A-Z0-9]{9,11}$"))
							pan = "";
					}
					catch (Exception e) 
					{
						System.err.println(e);
					}

					break;
				}
			}
		} 
		else 
		{
			// Old type of PAN card
			System.out.println("inside old");
			/*String splitDesc[] = content.split("\\n");
			for (i = 0; i < splitDesc.length; i++) 
			{
				System.out.println(splitDesc[i]);
				if (splitDesc[i].toUpperCase().contains(Constants.PanCard.pan.toUpperCase())) 
				{
					pan = splitDesc[i + 1];
				}
				else if (splitDesc[i].toUpperCase().contains(Constants.PanCard.name.toUpperCase())) 
				{
					//System.out.println("inside name");
					if (splitDesc[i].toUpperCase().contains(Constants.PanCard.father.toUpperCase())) 
					{
						fname = splitDesc[i + 1];
						if (splitDesc[i + 2].toUpperCase().contains(Constants.date.toUpperCase()))
							dob = splitDesc[i + 3];
						else 
						{
							fname = fname + " " + splitDesc[i + 2];
							dob = splitDesc[i + 4];
						}
						break;
					} 
					else 
					{
						name = splitDesc[i + 1];
						if (!splitDesc[i + 2].toUpperCase().contains(Constants.PanCard.father.toUpperCase()))
							name = name + " " + splitDesc[i + 2];
					}
				}
				else if (splitDesc[i].toUpperCase().contains(Constants.date.toUpperCase()))
				{
					dob = splitDesc[i + 1];
				}
			}
			dob = dob.split(" ")[0];
			System.out.println("dob : "+dob);
			
			Pattern pattern = Pattern.compile("[A-Z0-9]{10}");
			Matcher matcher = pattern.matcher(pan);
			if (matcher.find())
			{
				try{
					pan = matcher.group(0);
				}catch(Exception e){}
			    
			}*/
			
			String resultQR = QRScan.scanQR(filePath);
			String tokens[] = resultQR.split("\n");
			for(String token :tokens)
			{
				if(token.contains(Constants.PanCard.name) && !token.contains(Constants.PanCard.father))
					name = token.substring(token.lastIndexOf(":")+1).trim();
				else if(token.contains(Constants.PanCard.father))
					fname = token.substring(token.lastIndexOf(":")+1).trim();
				else if(token.contains(Constants.PanCard.dob))
				{
					dob = token.substring(token.lastIndexOf(":")+1).trim();
					SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormatSlash);
					try {
						cal.setTime(sdf.parse(dob));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				else if(token.contains(Constants.PanCard.panAbb))
					pan = token.substring(token.lastIndexOf(":")+1).trim();
			}
		}
		// setting the PanCard object
		obj.setName(name);
		obj.setFatherName(fname);
		obj.setDob(cal);
		obj.setPanNumber(pan);
		obj.setDobDisplay(dob);

		return obj;
	}
	
	public String readQRCode(String filePath, String charset, Map<DecodeHintType,Object> hintMap, Map<DecodeHintType,Object> hintPure)
			throws FileNotFoundException, IOException, NotFoundException {
		BufferedImage image = ImageIO.read(new FileInputStream(filePath));
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		BinaryBitmap bitmap = new BinaryBitmap(new GlobalHistogramBinarizer(source));
		Reader reader = new MultiFormatReader();
		ReaderException savedException = null;
		try {
			// Look for pure barcode
			Result theResult = reader.decode(bitmap, hintPure);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			// Look for normal barcode in photo
			Result theResult = reader.decode(bitmap, hintMap);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			// Try again with other binarizer
			BinaryBitmap hybridBitmap = new BinaryBitmap(new HybridBinarizer(source));
			Result theResult = reader.decode(hybridBitmap, hintMap);
			if (theResult != null) {
				return theResult.getText();
			}
		} catch (ReaderException re) {
			savedException = re;
		}
		try {
			throw savedException == null ? NotFoundException.getNotFoundInstance() : savedException;
		} catch (FormatException | ChecksumException e) {
			System.out.println(e.toString());
		} catch (ReaderException e) { // Including NotFoundException
			System.out.println(e.toString());
		}

		return "";
	}

	private String filterContent(String content) 
	{

		String lines[] = content.split("\n");
		String filteredContent = "";

		System.out.println("UnFiltered:" + content);
		
		for (String line : lines) 
		{
			if(line.length() < 4)
				line = "";
			
			if(line.length() >= 9 && line.length() <=11)	//If it is PAN
				line = line.toUpperCase();
			
			String filteredToken = "";
			String tokens[] = line.split(" ");
			
			for (String token : tokens)
				if (token.matches("^[A-Z0-9/]+$"))
					filteredToken = filteredToken + " " + token;
			
			if (!filteredToken.isEmpty())
				filteredContent = filteredContent + "\n" + filteredToken.trim();
		}
		return filteredContent;
	}
}
