package apiClass;

import java.util.Arrays;
import java.util.Calendar;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.IOException;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import modal.Constants;
import customExceptions.BarCodeException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.AadharCard;
import templates.AadharCardCoord;
import templates.Address;

public class ParseAadharCard {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an AadharCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : AadharCard object
	 * */
	public AadharCard parseAadharCard(JSONArray textAnnotationArray,String filePath){
		AadharCard aadharCardObj = new AadharCard();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");
			aadharCardObj = parseContent(descriptionStr,filePath,aadharCardObj);		
			aadharCardObj = parseCoord(textAnnotationArray,aadharCardObj);	
		}catch(JSONException je){ je.printStackTrace();
		}catch(Exception e){ e.printStackTrace();
		}
		return aadharCardObj;
	}


	/* DESCRIPTION : Finds the co-ordinate for boundary blocking from Vision API Call response by matching with the values from AadharCard object
	 * INPUT :  JSONArray response from Vision API Call and AadharCard object
	 * OUTPUT : AadharCard object
	 * */
	public AadharCard parseCoord(JSONArray textAnnotationArray,AadharCard aadharCardObj)
	{
		AadharCardCoord coord = new AadharCardCoord();
		int name=0,fatherName=0,yearOfBirth=0,dateOfBirth=0,gender=0,aadharNumber=0,address=0,index=1;
		int nameLength=0,fatherNameLength=0,yearOfBirthLength=0,dateOfBirthLength=0,genderLength=0,aadharNumberLength=0,addressLength=0;
		String year =  Integer.toString(aadharCardObj.getYearOfBirth());

		for(;index<textAnnotationArray.length();index++)
		{
			JSONObject jobj = (JSONObject) textAnnotationArray.get(index);
			String description = jobj.getString(Constants.VisionResponse.description);

			//setting the coordinates for name
			if(Arrays.asList(aadharCardObj.getName().split("\\s+")).contains(description) && nameLength< aadharCardObj.getName().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(name==0)
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
					name++;
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
				nameLength = nameLength+description.length()+1;
			}
			
			//setting the coordinates for father's name
			else if(Arrays.asList(aadharCardObj.getFatherName().split("\\s+")).contains(description) && fatherNameLength< aadharCardObj.getFatherName().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(fatherName==0)
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
					fatherName++;
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
				fatherNameLength = fatherNameLength+description.length()+1;
			}

			//setting the coordinates for year of birth
			else if(aadharCardObj.getYearOfBirth()!=0 && year.contains(description) && yearOfBirthLength<year.length())
			{	
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(yearOfBirth==0)
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
					yearOfBirth++;
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
				yearOfBirthLength = yearOfBirthLength+description.length();
			}

			//setting the coordinates for date of birth
			else if(aadharCardObj.getDobDisplay().contains(description) && dateOfBirthLength< aadharCardObj.getDobDisplay().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(dateOfBirth==0)
				{
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
					dateOfBirth++;
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
				dateOfBirthLength = dateOfBirthLength+description.length();
			}

			//setting the coordinates for gender
			else if(description.toLowerCase().contains(aadharCardObj.getGender().toLowerCase()) && genderLength< aadharCardObj.getGender().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(gender==0)
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
					gender++;
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
				genderLength = genderLength+description.length();	
			}

			//setting the coordinates for aadhar card number
			else if(Arrays.asList(aadharCardObj.getAadharNumber().split("\\s+")).contains(description) && aadharNumberLength< aadharCardObj.getAadharNumber().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(aadharNumber==0)
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
					aadharNumber++;
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
				aadharNumberLength = aadharNumberLength+description.length()+1;	
			}
			
			//setting the coordinates for address
			else if(Arrays.asList(aadharCardObj.getAddress().toString().split("\\s+")).contains(description) && addressLength< aadharCardObj.getAddress().toString().length())
			{
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(address==0)
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
					address++;
				}
				else
				{
					for(int j=1;j<3;j++)
					{ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(coord.getAddress()[0][j]<x)
							coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
				}
				addressLength = addressLength+description.length()+1;
			}
		}
			
		aadharCardObj.setCoordinates(coord);
		return aadharCardObj;
	}


	/* DESCRIPTION : Parses the content from TEXT_DETECTION of Vision API Call to find the values for AadharCard object
	 * INPUT : String content containing the entire text as detected by Vision API and AadharCard object
	 * OUTPUT : AadharCard object
	 * */
	public AadharCard parseContent(String content,String filePath,AadharCard aadharCardObj)throws WriterException, IOException
	{
		System.out.println("called for aadhar card inside parse content");
		int year = 0,dob=0;
		String uid="",name="",fname = "",gender="",aadharNumber="",dobstr="",address="";
		Calendar cal = Calendar.getInstance();
		Address addr = new Address();

		String resultQR;
		try {
			
			System.out.println("inside QR");

			resultQR = QRScan.scanQR(filePath);
			if(resultQR.isEmpty() || resultQR.matches("^[0-9]{1,12}$"))
				throw new BarCodeException();

			if(resultQR.contains(Constants.AadharCardPage1.qrDob)){
				dobstr=resultQR.substring(resultQR.lastIndexOf(Constants.AadharCardPage1.qrDob+Constants.equal+Constants.doubleQuote)+5,resultQR.lastIndexOf(Constants.doubleQuote));
				SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormatSlash);
				try {
					cal.setTime(sdf.parse(dobstr));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dob=1;
			}
			System.out.println("result QR "+resultQR);
			String tokens[] = resultQR.split(Constants.doubleQuote+Constants.space);
			for(String token : tokens){
				token=token.trim();
				if(token.contains(Constants.AadharCardPage1.qrUid))
					uid=token.substring(token.lastIndexOf(Constants.equal)+2);
				else if(token.contains(Constants.AadharCardPage1.qrName) && name.isEmpty())
					name=token.substring(token.lastIndexOf(Constants.equal)+2);
				else if(token.contains(Constants.AadharCardPage1.qrFather) || token.contains(Constants.AadharCardPage1.qrGname))
					fname=token.substring(token.lastIndexOf(Constants.equal)+2);
				else if(token.contains(Constants.AadharCardPage1.qrGender))
					gender=(token.substring(token.lastIndexOf(Constants.equal)+2).equals("M")?Constants.male:Constants.female);
				else if(token.contains(Constants.AadharCardPage1.qrYob))
					year=Integer.parseInt(token.substring(token.lastIndexOf(Constants.equal)+2));

				else if(token.contains(Constants.AadharCardPage1.qrCo))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrHouse))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrStreet))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrLm))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrLoc))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrVtc))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrPo))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);	
				else if(token.contains(Constants.AadharCardPage1.qrSubdist))
					address = address +" "+ token.substring(token.lastIndexOf(Constants.equal)+2);
				else if(token.contains(Constants.AadharCardPage1.qrDist))
					addr.setCity(token.substring(token.lastIndexOf(Constants.equal)+2));					
				else if(token.contains(Constants.AadharCardPage1.qrState))
					addr.setState(token.substring(token.lastIndexOf(Constants.equal)+2));
				else if(token.contains(Constants.AadharCardPage1.qrPc))
				{
					if(dob==0)
						addr.setZipCode(token.substring(token.lastIndexOf(Constants.equal)+2,token.lastIndexOf(Constants.doubleQuote)));
					else
						addr.setZipCode(token.substring(token.lastIndexOf(Constants.equal)+2));
				}
			}
			aadharNumber = uid.substring(0,4)+" "+uid.substring(4,8)+" "+uid.substring(8);
			
			addr.setAddressLine(address);
			aadharCardObj.setAddress(addr);
			
		} catch (NotFoundException | BarCodeException e1) {
			System.out.println("inside catch");
			content = content.concat("\\n "+Constants.eof);
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
			String splitDesc[] = content.split("\\n");
			int i;
			for(i = 0;i<splitDesc.length;i++)
			{
				System.out.println(splitDesc[i]);

				String number = extractNumber(splitDesc[i].replace(Constants.space, "").trim());
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
						String dateArr[] = splitDesc[i].split(Constants.colon);
						dobstr = dateArr[dateArr.length-1];
						System.out.println(dobstr);
					}
					
					name = splitDesc[i-1];	
					if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
					{
						fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
						name = splitDesc[i-2];
						System.out.println("inside first if name: "+name+"father: "+fname);
					}
					int j = i;
					while(name.length() < 5){
						name = splitDesc[--j];
						if(name.toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()))
						{
							fname = name.substring(name.lastIndexOf(Constants.AadharCardPage1.father)+7).replace(Constants.colon, "").trim();
							name = splitDesc[j-1];
							System.out.println("inside while if name: "+name+"father: "+fname);
						}
					}
				}
				else if(splitDesc[i].contains(Constants.birth) || splitDesc[i].contains(Constants.year))
				{
					try
					{
						String yearstr = extractDateNumber(splitDesc[i]);
						if(yearstr.isEmpty())
						{
							yearstr = extractDateNumber(splitDesc[i+1]);
						}
						yearstr = yearstr.substring(yearstr.length()-4,yearstr.length());
						year = Integer.parseInt(yearstr);

					}
					catch(Exception e)
					{
						e.printStackTrace();						
					}
					finally{
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
				}
				else if(splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.address.toLowerCase()))
				{
					System.out.println("Inside address "+splitDesc[i]);
					address = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.colon)+1);
					while(!splitDesc[i].matches("^.+?\\d{6}$") && i<splitDesc.length-1){
						System.out.println("Inside address "+splitDesc[i]);
						i++;						
						address = address + Constants.newLine + splitDesc[i];		
					}		
					address.replaceAll(Constants.AadharCardPage1.address.toUpperCase(), "");
					System.out.println("inside address : "+address);
					Address completeAddr = new ParseAddress().getAddress(address);
					aadharCardObj.setAddress(completeAddr);
				}

				else if(splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.unique.toLowerCase()) ||
						splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.identification.toLowerCase()) ||
						splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.authority.toLowerCase()))
				{
						//System.out.println("inside unique identification ");
						i++;
						System.out.println("Inside uia "+splitDesc[i]);
						address = splitDesc[i].substring(splitDesc[i].lastIndexOf(Constants.colon)+1);
						while(!splitDesc[i].matches("^.+?\\d{6}$") && i<splitDesc.length-1){
							i++;						
							address = address + Constants.newLine + splitDesc[i];
							System.out.println("Inside uia "+splitDesc[i]);
						}				
					
						address.replace(Constants.AadharCardPage1.address, "");
						System.out.println("inside unique : "+address);
						Address completeAddr = new ParseAddress().getAddress(address);
						aadharCardObj.setAddress(completeAddr);
				}
				else if(splitDesc[i].toLowerCase().contains(Constants.AadharCardPage1.father.toLowerCase()) && fname.isEmpty()){
					fname = splitDesc[i];
					fname = fname.substring(fname.indexOf(Constants.AadharCardPage1.father)+8);
					if(name.isEmpty() || name.length()<3)
						name = splitDesc[i-1];
				}
				else if(year == 0 && dobstr.isEmpty() && splitDesc[i].contains(Constants.AadharCardPage1.dobError)){
					dobstr = splitDesc[i].substring(splitDesc[i].indexOf(Constants.AadharCardPage1.dobError)+3);
				}
			}

			
		}
		aadharCardObj.setName(name);
		aadharCardObj.setFatherName(fname);
		aadharCardObj.setGender(gender);
		aadharCardObj.setAadharNumber(aadharNumber);
		aadharCardObj.setYearOfBirth(year);
		aadharCardObj.setDob(cal);
		aadharCardObj.setDobDisplay(dobstr);
		return aadharCardObj;
	}

	
	/* DESCRIPTION : Extract number form String. Ignore all characters and iterate up to length 14 (Aadhaar plus 2 spaces)
	 * INPUT : String that may contain aadhar number
	 * OUTPUT : String of aadhar number
	 * */
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
					sb.append(Constants.space);
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
	
	
	/* DESCRIPTION : Extract date from a String
	 * INPUT : String that contains date
	 * OUTPUT : Date in String format
	 * */
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
