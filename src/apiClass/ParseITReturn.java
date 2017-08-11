package apiClass;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.Address;
import templates.ITReturn;
import templates.ITReturnCoord;

public class ParseITReturn {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an AadharCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : AadharCard object
	 * */
	public ITReturn parseITReturn(JSONArray textAnnotationArray,String filePath){
		ITReturn iTReturnObject = new ITReturn();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");
			iTReturnObject = parseContent(descriptionStr,filePath,iTReturnObject);		
			iTReturnObject = parseCoord(textAnnotationArray,iTReturnObject);	
		}catch(JSONException je){ je.printStackTrace();
		}catch(Exception e){ e.printStackTrace();
		}
		return iTReturnObject;
	}
	
	
	/* DESCRIPTION : Finds the co-ordinate for boundary blocking from Vision API Call response by matching with the values from ITReturn object
	 * INPUT :  JSONArray response from Vision API Call and ITReturn object
	 * OUTPUT : ITReturn object
	 * */
	private ITReturn parseCoord(JSONArray textAnnotationArray, ITReturn iTReturnObject) {
		ITReturnCoord iTReturnCoord = new ITReturnCoord();
		int name = 0, address = 0, assessmenYear = 0, designationOfWard = 0, startIndex = 1;
		int nameLength = 0, addressLength = 0, assessmenYearLength = 0, designationOfWardLength = 0;
		
		for (;startIndex < textAnnotationArray.length()  && assessmenYearLength < iTReturnObject.getAssessmentYear().length(); startIndex++) {
			JSONObject jsonObject = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jsonObject.getString(Constants.VisionResponse.description);
			System.out.println(description);
			
			// setting the coordinates for assessmentYear
			if(Arrays.asList(iTReturnObject.getAssessmentYear().split("-")).contains(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				if (assessmenYear == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setAssessmentYear(x, 0, j);
						iTReturnCoord.setAssessmentYear(y, 1, j);
					}
					assessmenYear++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setAssessmentYear(x, 0, j);
						iTReturnCoord.setAssessmentYear(y, 1, j);
					}
				}
				assessmenYearLength = assessmenYearLength + description.length()+1;
			}
		}
		
		startIndex = getStartIndex(textAnnotationArray,startIndex);
				
		for (;startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jsonObject = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jsonObject.getString(Constants.VisionResponse.description);
			System.out.println(description);
			
			if(description.equals("/") || description.equals("-") || description.equalsIgnoreCase(Constants.ITReturn.of))
				continue;
			
			//stopping the iteration at DEDUCTION or VERIFICATION
			if(Constants.ITReturn.deduction1.equalsIgnoreCase(description) || 
					Constants.ITReturn.deduction2.equalsIgnoreCase(description) ||
					Constants.ITReturn.verification.equalsIgnoreCase(description) )
				break; 
			
				
			// setting the coordinates for panNumber
			else if(iTReturnObject.getPanNumber().equals(description)){
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
			
			
			// setting the coordinates for name
			else if(Arrays.asList(iTReturnObject.getName().split("\\s+")).contains(description) && nameLength < iTReturnObject.getName().length()) {
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (name == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setName(x, 0, j);
						iTReturnCoord.setName(y, 1, j);
					}
					name++;
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
				nameLength = nameLength + description.length() + 1;
			}
			
			
			// setting the coordinates for address
			else if(iTReturnObject.getAddress().toString().contains(description) && addressLength< iTReturnObject.getAddress().toString().length())			{
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(address==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setAddress(x, 0, j);
						iTReturnCoord.setAddress(y, 1, j);
					}
					address++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(iTReturnCoord.getAddress()[0][j]<x)
							iTReturnCoord.setAddress(x, 0, j);
						iTReturnCoord.setAddress(y, 1, j);
					}
				}
				addressLength = addressLength+description.length()+1;
			}
			
			// setting the coordinates for status
			else if(iTReturnObject.getStatus().equalsIgnoreCase(description)){
				System.out.println("inside status coord");
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setStatus(x, 0, j);
					iTReturnCoord.setStatus(y, 1, j);
				}
			}
			
			// setting the coordinates for aadharNumber
			else if(iTReturnObject.getAadharNumber().equals(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setAadharNumber(x, 0, j);
					iTReturnCoord.setAadharNumber(y, 1, j);
				}
			}
						
			
			// setting the coordinates for designationOfAO
			else if(Arrays.asList(iTReturnObject.getDesignationOfAO().split("\\s+")).contains(description) && designationOfWardLength < iTReturnObject.getDesignationOfAO().length()) {
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
				if (designationOfWard == 0) {
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setDesignationOfAO(x, 0, j);
						iTReturnCoord.setDesignationOfAO(y, 1, j);
					}
					designationOfWard++;
				} else {
					for (int j = 1; j < 3; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.setDesignationOfAO(x, 0, j);
						iTReturnCoord.setDesignationOfAO(y, 1, j);
					}
				}
				designationOfWardLength = designationOfWardLength + description.length() + 1;
			}
			
			// setting the coordinates for originalRevised
			else if(iTReturnObject.getOriginalRevised().equalsIgnoreCase(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setOriginalRevised(x, 0, j);
					iTReturnCoord.setOriginalRevised(y, 1, j);
				}
			}
			
			// setting the coordinates for eFillingAckNumber
			else if(iTReturnObject.geteFillingAckNumber().equals(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.seteFillingAckNumber(x, 0, j);
					iTReturnCoord.seteFillingAckNumber(y, 1, j);
				}
			}
			
			// setting the coordinates for eFillingDate
			else if(Constants.ITReturn.date[0].equalsIgnoreCase(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				
					for (int j = 0; j < 4; j++) { // iterate columns
						JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						iTReturnCoord.seteFillingDate(x, 0, j);
						iTReturnCoord.seteFillingDate(y, 1, j);
					}
					
					for (int j = 1; j < 3; j++) { // iterate columns
						int x = iTReturnCoord.geteFillingDate()[0][j] + 150;
						iTReturnCoord.seteFillingDate(x, 0, j);
					}					
				
			}
			
			// setting the coordinates for grossTotalIncome
			else if(iTReturnObject.getGrossTotalIncome().equals(description)){
				JSONObject boundingPoly = jsonObject.getJSONObject(Constants.VisionResponse.boundingPoly);
				for (int j = 0; j < 4; j++) { // iterate columns
					JSONArray vertices = (JSONArray) boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
					JSONObject xy = (JSONObject) vertices.get(j);
					int x = xy.getInt(Constants.VisionResponse.x);
					int y = xy.getInt(Constants.VisionResponse.y);
					iTReturnCoord.setGrossTotalIncome(x, 0, j);
					iTReturnCoord.setGrossTotalIncome(y, 1, j);
				}
			}			
		}
		
		iTReturnObject.setCoordinates(iTReturnCoord);
		return iTReturnObject;
	}

	
	/* DESCRIPTION : Parses the content from TEXT_DETECTION of Vision API Call to find the values for ITReturn object
	 * INPUT : String content containing the entire text as detected by Vision API and ITReturn object
	 * OUTPUT : ITReturn object
	 * */
	private ITReturn parseContent(String content, String filePath, ITReturn iTReturnObject) {
		int upperIndex;
		String name="",panNumber="",assessmentYear="",address="",ward="",aadharNumber="",originalRevised="",eFilingAck="",date="",
				grossIncome="",status="";
		
		assessmentYear = getAssessmentYear(content);		
				
		if(content.toLowerCase().indexOf(Constants.ITReturn.original.toLowerCase())
				!= content.toLowerCase().lastIndexOf(Constants.ITReturn.original.toLowerCase()))
			originalRevised = Constants.ITReturn.original;
		else
			originalRevised = Constants.ITReturn.revised;			
		
		
		//reduce the size of string from "Assessment year" till "Verification" or "deductions"
		upperIndex = getUpperIndex(content);
		content = content.substring(content.toLowerCase().indexOf(Constants.ITReturn.name.toLowerCase()),upperIndex);
		
		//panNumber = getPanNumber(content);		
		aadharNumber = getAadharNumber(content);
		
		String splitDesc[] = content.split("\\n");
				
		for(int index = 0;index<splitDesc.length;index++)
		{
			//System.out.println(splitDesc[index]);
			
			if(Arrays.stream(Constants.ITReturn.status).parallel().anyMatch(splitDesc[index]::contains)){
				if(splitDesc[index].trim().lastIndexOf(" ")>0)
					status = splitDesc[index].substring(splitDesc[index].trim().lastIndexOf(" ")).trim();
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
				status = splitDesc[index].trim();
			}
			else if(splitDesc[index].toLowerCase().contains(Constants.ITReturn.original.toLowerCase()))
				continue; //do nothing
			
			else{
				if(!splitDesc[index].equals(aadharNumber))
					address = address + splitDesc[index] + ",\n";
			}
				
			
		}
		
		iTReturnObject.setAssessmentYear(assessmentYear);
		iTReturnObject.setPanNumber(panNumber);
		iTReturnObject.setAadharNumber(aadharNumber);
		iTReturnObject.setName(name);
		iTReturnObject.setDesignationOfAO(ward);
		iTReturnObject.seteFillingAckNumber(eFilingAck);
		iTReturnObject.seteFillingDate(date);
		iTReturnObject.setGrossTotalIncome(grossIncome);
		iTReturnObject.setOriginalRevised(originalRevised);
		iTReturnObject.setStatus(status);
		
		Address addressObj = new ParseAddress().getAddress(address.trim());
		iTReturnObject.setAddress(addressObj);
		
		return iTReturnObject;
	}

	
	/* DESCRIPTION : Searches for the Assessment Year in the entire String 
	 * INPUT : String containing Assessment year
	 * OUTPUT : Assessment year
	 * */
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
	
	
	/* DESCRIPTION : Searches for PAN in the entire String
	 * INPUT : String containing PAN
	 * OUTPUT : PAN 
	 * */
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
	
	
	/* DESCRIPTION : Searches for Aadhar Number in the entire String
	 * INPUT : String containing Aadhar Number
	 * OUTPUT : Aadhar Number
	 * */
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
	
	
	/* DESCRIPTION : Find the position of upper index , the point to which the words should be processed
	 * INPUT : String content to be processed
	 * OUTPUT : Index position of words "Deductions or Verification.
	 * */
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
	
	
	/* DESCRIPTION : Checks if the row is required to be processed 
	 * INPUT : String content of row
	 * OUTPUT : Boolean
	 * */
	private boolean isRequired(String rowContent) {
		if(Arrays.stream(Constants.ITReturn.notRequiredRows).parallel().anyMatch(rowContent::contains)){
			return false;
		}			
		return true;
	}
	
	
	/* DESCRIPTION :Extracts the E Filling Acknowledgement number from a row of String 
	 * INPUT : String content of the row
	 * OUTPUT : String of E Filling Acknowledgement Number
	 * */
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
	
	
	/* DESCRIPTION :Extracts the salary from a row of String 
	 * INPUT : String content of the row
	 * OUTPUT : String of salary
	 * */
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
	
	
	/* DESCRIPTION : Finds the Description "Name" from the JSONArray and marks it as startIndex for finding co-ordinates
	 * INPUT : JSONArray response from Vision API and Integer startIndex
	 * OUTPUT : Integer startIndex
	 * */
	private int getStartIndex(JSONArray textAnnotationArray, int startIndex) {
		for (; startIndex < textAnnotationArray.length(); startIndex++) {
			JSONObject jobj = (JSONObject) textAnnotationArray.get(startIndex);
			String description = jobj.getString(Constants.VisionResponse.description);
			if (description.toLowerCase().contains(Constants.ITReturn.name.toLowerCase()))
				break;
		}		
		return startIndex;
	}
	
	

}
