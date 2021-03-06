package apiClass;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import templates.PanCard;
import templates.PanCardCoord;

import customExceptions.BarCodeException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;


public class ParsePanCard {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an PanCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : PanCard object
	 * */
	public PanCard parsePanCard(JSONArray textAnnotationArray,String filePath) {
		
		//Initialize PanCard Object
		PanCard panCardObject = new PanCard();
		
		try 
		{
			JSONObject firstObj = (JSONObject) textAnnotationArray.get(0);
			String descriptionStr = firstObj.getString(Constants.VisionResponse.description);
			
			panCardObject = parseContent(descriptionStr,filePath,panCardObject);
			panCardObject = parseCoord(textAnnotationArray, panCardObject);
		} 
		catch (JSONException je) 
		{
			je.printStackTrace();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return panCardObject;
	}
	
	
	/* DESCRIPTION : Finds the co-ordinate for boundary blocking from Vision API Call response by matching with the values from PanCard object
	 * INPUT :  JSONArray response from Vision API Call and PanCard object
	 * OUTPUT : PanCard object
	 * */
	public PanCard parseCoord(JSONArray textAnnotationArray, PanCard panCardObject) {
		int n = 0, f = 0, d = 0, p = 0, startIndex = 1;
		int nl = 0, fl = 0, dl = 0, pl = 0;
		PanCardCoord panCardCoordinates = new PanCardCoord();

		startIndex = getStartIndex(textAnnotationArray);
		
		for (; startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jsonObject = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jsonObject.getString(Constants.VisionResponse.description);
			
			if(description.equals("/"))
				continue;
			
			// setting the coordinates for name
			if (Arrays.asList(panCardObject.getName().split("\\s+")).contains(description) && nl < panCardObject.getName().length()) {
				
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (n == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setName(x, 0, j);
						panCardCoordinates.setName(y, 1, j);
					}
					n++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setName(x, 0, j);
						panCardCoordinates.setName(y, 1, j);
					}
				}
				nl = nl + description.length() + 1;
			}

			// setting the coordinates for father's name
			else if (Arrays.asList(panCardObject.getFatherName().split("\\s+")).contains(description) && fl < panCardObject.getFatherName().length()) {
				
				//Initialize boundingPloy Object
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (f == 0) 
				{
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setFatherName(x, 0, j);
						panCardCoordinates.setFatherName(y, 1, j);
					}
					f++;
				} 
				else 
				{
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setFatherName(x, 0, j);
						panCardCoordinates.setFatherName(y, 1, j);
					}
				}
				fl = fl + description.length() + 1;
			}

			// setting the coordinates for date
			else if (panCardObject.getDobDisplay().contains(description) && dl < panCardObject.getDobDisplay().length()) {

				//Initialize boundingPloy Object
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (d == 0) 
				{
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setDobDisplay(x, 0, j);
						panCardCoordinates.setDobDisplay(y, 1, j);
					}
					d++;
				} 
				else 
				{
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly
								.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setDobDisplay(x, 0, j);
						panCardCoordinates.setDobDisplay(y, 1, j);
					}
				}
				
				dl = dl + description.length();
			}

			// setting the coordinates for PanCard number
			else if (panCardObject.getPanNumber().equals(description)&& pl < panCardObject.getPanNumber().length()) 
			{
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (p == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setPanNumber(x, 0, j);
						panCardCoordinates.setPanNumber(y, 1, j);
					}
					p++;
				} 
				else
				{
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						panCardCoordinates.setPanNumber(x, 0, j);
						panCardCoordinates.setPanNumber(y, 1, j);
					}
				}
				pl = pl + description.length();
			}
		}

		panCardObject.setCoordinates(panCardCoordinates);
		return panCardObject;
	}
	
	/* DESCRIPTION : Parses the content from TEXT_DETECTION of Vision API Call to find the values for PanCard object
	 * INPUT : String content containing the entire text as detected by Vision API and PanCard object
	 * OUTPUT : PanCard object
	 * */
	public PanCard parseContent(String content, String filePath, PanCard panCardObject)throws WriterException, IOException
	{
		int i;
		String name = "", fname = "", dob = "", pan = "";
		Calendar calendarObject = Calendar.getInstance();
		
		
		if (!content.contains(Constants.PanCard.name)) 
		{ 
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
					/*if(splitDesc[i+1].indexOf(Constants.india1) > -1)
						i++;
					if(splitDesc[i+1].indexOf(Constants.PanCard.tax) > -1)
						i++;*/
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
						calendarObject.setTime(sdf.parse(dob));
						System.out.println("CALENDER" + calendarObject);
						
						if(!calendarObject.toString().matches("^[A-Z]$"))
						{
							System.out.println("MACTHES");
						}
					} 
					catch (Exception e) 
					{
						//If there is exception chances are that name has been set to this.
						//Iterate one back to push all string one above
						System.err.println(e);
						
						while(e.getMessage().toString().contains("Unparseable date") && !dob.contains("/") && !dob.matches(".*\\d+.*"))
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
								e = ez;
							}
						}
					}

					try
					{
						// extracting Pan
						pan = splitDesc[i + 1];
						//Allow and error for +-1 for Number
						if (!pan.matches("^[A-Z0-9 ]{9,11}$"))
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
			try{
				System.out.println("detected QR Code");
				String resultQR = QRScan.scanQR(filePath);
				if(resultQR.isEmpty())
					throw new BarCodeException();
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
							calendarObject.setTime(sdf.parse(dob));
						} catch (ParseException e) {
							e.printStackTrace();
						}
					}
					else if(token.contains(Constants.PanCard.panAbb))
						pan = token.substring(token.lastIndexOf(":")+1).trim();
				}
			}catch(NotFoundException | BarCodeException ex){
				System.out.println("could not detect bar code");
				String splitDesc[] = content.split("\\n");
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
				    
				}
			}
			
		}
		// setting the PanCard object
		panCardObject.setName(name);
		panCardObject.setFatherName(fname);
		panCardObject.setDob(calendarObject);
		panCardObject.setPanNumber(pan);
		panCardObject.setDobDisplay(dob);

		return panCardObject;
	}
	
	/* DESCRIPTION : Filters string removes all characters that are not Capital Letter or digit
	 * INPUT : String content containing the entire text as detected by Vision API
	 * OUTPUT : String filteredContent
	 * */
	private String filterContent(String content) 
	{

		String lines[] = content.split("\n");
		String filteredContent = "";
		
		for (String line : lines) 
		{
			if(line.length() < 4)
				line = "";
			
			if(line.length() >= 9 && line.length() <=11)	//If it is PAN
				line = line.toUpperCase();
			
			String filteredToken = "";
			String tokens[] = line.split(" ");
			
			for (String token : tokens)
				if (token.matches("^[A-Z0-9/']+$"))
					filteredToken = filteredToken + " " + token;
			
			if (!filteredToken.isEmpty())
				filteredContent = filteredContent + Constants.newLine + filteredToken.trim();
		}
		return filteredContent;
	}
	
	
	/* DESCRIPTION : Finds the index of "INDIA" to start loop
	 * INPUT : JSONArray of Vision API Response
	 * OUTPUT : Integer startIndex
	 * */
	private int getStartIndex(JSONArray textAnnotationArray){
		int startIndex = 1;
		
		for (; startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jobj = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jobj.getString(Constants.VisionResponse.description);
			if (description.toUpperCase().contains(Constants.india))
				break;
		}
		
		return startIndex;
	}
}
