package apiClass;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.ITReturn;
import templates.ITReturnCoord;

public class ParseITReturn {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an AadharCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : AadharCard object
	 * */
	public ITReturn parseITReturn(JSONArray textAnnotationArray,String filePath){
		ITReturn obj = new ITReturn();
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

	private ITReturn parseCoord(JSONArray textAnnotationArray, ITReturn obj) {
		ITReturnCoord iTReturnCoord = new ITReturnCoord();
		int n = 0, f = 0, d = 0, p = 0, startIndex = 1;
		int nl = 0, fl = 0, dl = 0, pl = 0;
		
		startIndex = getStartIndex(textAnnotationArray);
		
		for (; startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jsonObject = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jsonObject.getString(Constants.VisionResponse.description);
			
			if(description.equals("/"))
				continue;
			
			
			// setting the coordinates for panNumber
			if(obj.getPanNumber().contains(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setPanNumber(x, 0, j);
					iTReturnCoord.setPanNumber(y, 1, j);
				}
			}
			
			// setting the coordinates for aadharNumber
			if(obj.getPanNumber().contains(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setPanNumber(x, 0, j);
					iTReturnCoord.setPanNumber(y, 1, j);
				}
			}
			// setting the coordinates for assessmentYear
			// setting the coordinates for address
			// setting the coordinates for status
			// setting the coordinates for designationOfAO
			// setting the coordinates for originalRevised
			// setting the coordinates for eFillingAckNumber
			// setting the coordinates for eFillingDate
			// setting the coordinates for grossTotalIncome
			
			
			
			
			
			// setting the coordinates for name
			if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl < obj.getName().length()) {
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (n == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setName(x, 0, j);
						iTReturnCoord.setName(y, 1, j);
					}
					n++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setName(x, 0, j);
						iTReturnCoord.setName(y, 1, j);
					}
				}
				nl = nl + description.length() + 1;
			}
			
			
			
			
			
			
		}
		
		obj.setCoordinates(iTReturnCoord);
		return obj;
	}

	private ITReturn parseContent(String content, String filePath, ITReturn obj) {
		int upperIndex;
		String name="",panNumber="",assessmentYear="",address="",ward="",aadharNumber="",originalRevised="",eFilingAck="",date="",
				grossIncome="",status="";
		
		assessmentYear = getAssessmentYear(content);		
				
		if(content.toLowerCase().indexOf(Constants.ITReturn.original.toLowerCase())
				!= content.toLowerCase().lastIndexOf(Constants.ITReturn.original.toLowerCase()))
			originalRevised = Constants.ITReturn.original;
		else
			originalRevised = Constants.ITReturn.revised;			
		
		
		//reduce the size of string from "Assessment year" till "total income" or "deductions"
		upperIndex = getUpperIndex(content);
		content = content.substring(content.toLowerCase().indexOf(Constants.ITReturn.name.toLowerCase()),upperIndex);
		
		//panNumber = getPanNumber(content);		
		aadharNumber = getAadharNumber(content);
		
		String splitDesc[] = content.split("\\n");
				
		for(int index = 0;index<splitDesc.length;index++)
		{
			System.out.println(splitDesc[index]);
			
			if(Arrays.stream(Constants.ITReturn.status).parallel().anyMatch(splitDesc[index]::contains)){
				if(splitDesc[index].trim().lastIndexOf(" ")>0)
					status = splitDesc[index].substring(splitDesc[index].trim().lastIndexOf(" "));
			}
			
			else if(!isRequired(splitDesc[index]))					
				continue;
			
			else if(name.isEmpty()){
				name = splitDesc[index];
				if(panNumber.isEmpty())
					panNumber = splitDesc[++index];
			}
			
			else if(Arrays.stream(Constants.ITReturn.ward).parallel().anyMatch(splitDesc[index]::contains)){
				if(splitDesc[index].toLowerCase().contains(Constants.ITReturn.original.toLowerCase()))
					ward = splitDesc[index].substring(0,splitDesc[index].indexOf(Constants.ITReturn.original));
				else
					ward = splitDesc[index];
			}
			
			else if(Arrays.stream(Constants.ITReturn.eFiling).parallel().anyMatch(splitDesc[index]::contains)){
				eFilingAck = getEFilingAckNumber(splitDesc[index]);
				//if the ack number is in the next line
				if(eFilingAck.isEmpty())
					eFilingAck = getEFilingAckNumber(splitDesc[++index]);
				//if date is in the same line
				else if(Arrays.stream(Constants.ITReturn.date).parallel().anyMatch(splitDesc[index]::contains))
					date  = splitDesc[index].substring(splitDesc[index].lastIndexOf(Constants.ITReturn.closingBracket)+1);
				}
			
			else if(Arrays.stream(Constants.ITReturn.date).parallel().anyMatch(splitDesc[index]::contains)){
				date  = splitDesc[index].substring(splitDesc[index].lastIndexOf(Constants.ITReturn.closingBracket)+1);
			}
			
			else if(Arrays.stream(Constants.ITReturn.grossIncome).parallel().anyMatch(splitDesc[index]::contains)){
				grossIncome = getNumber(splitDesc[index]);
				if(grossIncome.isEmpty())
					grossIncome = getNumber(splitDesc[++index]);
			}
			
			else if(Arrays.stream(Constants.ITReturn.statusType).parallel().anyMatch(splitDesc[index]::contains)){
				status = splitDesc[index];
			}
			else if(splitDesc[index].toLowerCase().contains(Constants.ITReturn.original.toLowerCase()))
				continue; //do nothing
			
			else{
				if(!splitDesc[index].equals(aadharNumber))
					address = address + splitDesc[index] + ",\n";
			}
				
			
		}
		
		obj.setAssessmentYear(assessmentYear);
		obj.setPanNumber(panNumber);
		obj.setAadharNumber(aadharNumber);
		obj.setName(name);
		obj.setAddress(address.trim());
		obj.setDesignationOfAO(ward);
		obj.seteFillingAckNumber(eFilingAck);
		obj.seteFillingDate(date);
		obj.setGrossTotalIncome(grossIncome);
		obj.setOriginalRevised(originalRevised);
		obj.setStatus(status);
		
		return obj;
	}

	

	private String getAssessmentYear(String descriptionStr) {
		String year = "";
		Pattern pattern = Pattern.compile("[0-9]{4}-[0-9]{2}");
		Matcher matcher = pattern.matcher(descriptionStr);
		if (matcher.find())
		{
			try{
				year = matcher.group(0);
			}catch(Exception e){}
		}
		return year;
	}
	
	private String getPanNumber(String content) {
		String pan = "";
		Pattern pattern = Pattern.compile("[A-Z0-9 ]{9,11}");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find())
		{
			try{
				pan = matcher.group(0);
			}catch(Exception e){}
		}
		return pan;
	}
	
	private String getAadharNumber(String content) {
		String pan = "";
		Pattern pattern = Pattern.compile("[0-9 ]{12}");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find())
		{
			try{
				pan = matcher.group(0);
			}catch(Exception e){}
		}
		return pan;
	}
	
	
	private int getUpperIndex(String content) {
		int index = 0;
		content = content.toLowerCase();
		
		index = content.indexOf(Constants.ITReturn.deduction1.toLowerCase());
		if(index < 0)
			index = content.indexOf(Constants.ITReturn.deduction2.toLowerCase());
					
		if(index < 0)
			index = content.lastIndexOf(Constants.ITReturn.verification.toLowerCase());
		
		if(index < 0)
			return content.length();
		return index;
		
		}
	
	private boolean isRequired(String rowContent) {
		if(Arrays.stream(Constants.ITReturn.notRequiredRows).parallel().anyMatch(rowContent::contains)){
			return false;
		}			
		return true;
	}
	
	private String getEFilingAckNumber(String content) {
		int i=0;
		if(content == null || content.isEmpty()) return "";
		StringBuilder sb = new StringBuilder();
		boolean found = false;
		for(char c : content.toCharArray())
		{
			if(Character.isDigit(c) && sb.length()<15)
			{
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
	
	private String getNumber(String content) {
		int index=0;
		if(content == null || content.isEmpty()) return "";
		StringBuilder stringBuilderObj = new StringBuilder();
		boolean found = false;
		for(char singleChar : content.toCharArray())
		{
			if(Character.isDigit(singleChar))
			{
				stringBuilderObj.append(singleChar);
				found = true;
			} 
			else if(singleChar!='\n' && singleChar!=' ' && found)
			{
				// If we already found a digit before and this char is not a digit or \n or space, stop looping
				break;                
			}
			index++;
		}
		if(stringBuilderObj.length()>=4)
			return stringBuilderObj.toString();    //return true if the string found is of minimum length greater than 4
		else
		{
			if(stringBuilderObj.length()>=1)
				return getNumber(content.substring(index));  //if the minimum length is 1 then recrusive call for the remaining length of the string
			
			else
				return "";  //when the entire string does not consist of the salary
		}
	}
	
	private int getStartIndex(JSONArray textAnnotationArray) {
		int startIndex = 1;
		
		for (; startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jobj = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jobj.getString(Constants.VisionResponse.description);
			if (description.toLowerCase().contains(Constants.ITReturn.name))
				break;
		}
		
		return startIndex;
	}
	
	

}
