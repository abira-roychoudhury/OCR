package apiClass;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.Address;
import templates.VoterCard;
import templates.VoterCardCoord;

public class ParseVoterCard {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an VoterCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseVoterCard(JSONArray textAnnotationArray){
		VoterCard voterCardObject = new VoterCard();
		try{
		JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
		String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
		voterCardObject = parseContent(descriptionStr,voterCardObject);		
		voterCardObject = parseCoord(textAnnotationArray,voterCardObject);	
		}catch(JSONException je){ 
			je.printStackTrace();
		}catch(Exception e){ 
			e.printStackTrace();
		 }
		return voterCardObject;
	}
	
	/* DESCRIPTION : Finds the co-ordinate for boundary blocking from Vision API Call response by matching with the values from VoterCard object
	 * INPUT :  JSONArray response from Vision API Call and VoterCard object
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseCoord(JSONArray textAnnotationArray, VoterCard voterCardObject){
		VoterCardCoord voterCardCoordObject = new VoterCardCoord();
		int name=0,father=0,dob=0,age=0,sex=0,voterNumber=0,address=0,index=1;
		int nameLength=0,fatherLength=0,dobLength=0,ageLength=0,sexLength=0,voterNumberLength=0,addressLength=0;
				
		for(;index<textAnnotationArray.length();index++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(index);
			String description = jobj.getString(Constants.VisionResponse.description);
			//System.out.println(description);
			if(description.equals("/") || description.equals(Constants.colon))
				continue;
			
			
			//Setting coordinates VoterId
			if(voterCardObject.getVoterId().contains(description) && voterNumberLength< voterCardObject.getVoterId().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(voterNumber==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setVoterId(x, 0, j);
						voterCardCoordObject.setVoterId(y, 1, j);
					}
					voterNumber++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setVoterId(x, 0, j);
						voterCardCoordObject.setVoterId(y, 1, j);
					}
				}
				voterNumberLength = voterNumberLength+description.length();
			}
			//setting the coordinates for name
			else if(Arrays.asList(voterCardObject.getName().split("\\s+")).contains(description) && nameLength< voterCardObject.getName().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(name==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setName(x, 0, j);
						voterCardCoordObject.setName(y, 1, j);
					}
					name++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(voterCardCoordObject.getName()[0][j]<x)
							voterCardCoordObject.setName(x, 0, j);
						voterCardCoordObject.setName(y, 1, j);
					}
				}
				nameLength = nameLength+description.length()+1;
			}
			
			//setting the coordinates for father's name
			else if(Arrays.asList(voterCardObject.getFatherName().split("\\s+")).contains(description) && fatherLength< voterCardObject.getFatherName().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(father==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setFatherName(x, 0, j);
						voterCardCoordObject.setFatherName(y, 1, j);
					}
					father++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(voterCardCoordObject.getFatherName()[0][j]<x)
							voterCardCoordObject.setFatherName(x, 0, j);
						voterCardCoordObject.setFatherName(y, 1, j);
					}
				}
				fatherLength = fatherLength+description.length()+1;
			}
			
			//setting the coordinates for sex 
			else if(description.toLowerCase().contains(voterCardObject.getSex().toLowerCase()) && sexLength<voterCardObject.getSex().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(sex==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setSex(x, 0, j);
						voterCardCoordObject.setSex(y, 1, j);
					}
					sex++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setSex(x, 0, j);
						voterCardCoordObject.setSex(y, 1, j);
					}
				}
				sexLength = sexLength+description.length();
			}	
			
			//setting the coordinates for date 
			else if(voterCardObject.getDobDisplay().contains(description) && dobLength< voterCardObject.getDobDisplay().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(dob==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setDobDisplay(x, 0, j);
						voterCardCoordObject.setDobDisplay(y, 1, j);
					}
					dob++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setDobDisplay(x, 0, j);
						voterCardCoordObject.setDobDisplay(y, 1, j);
					}
				}
				dobLength = dobLength+description.length();
			}
			
			//setting the coordinates for age 
			else if(Arrays.asList(voterCardObject.getAge().split("\\s+")).contains(description) && ageLength< voterCardObject.getAge().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(age==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setAge(x, 0, j);
						voterCardCoordObject.setAge(y, 1, j);
					}
					age++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setAge(x, 0, j);
						voterCardCoordObject.setAge(y, 1, j);
					}
				}
				ageLength = ageLength+description.length()+1;
			}
			//setting the coordinates for address
			else if(Arrays.asList(voterCardObject.getAddress().toString().split("\\s+")).contains(description) && addressLength< voterCardObject.getAddress().toString().length()){
				System.out.println(description);
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(address==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						voterCardCoordObject.setAddress(x, 0, j);
						voterCardCoordObject.setAddress(y, 1, j);
					}
					address++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(voterCardCoordObject.getAddress()[0][j]<x)
							voterCardCoordObject.setAddress(x, 0, j);
						voterCardCoordObject.setAddress(y, 1, j);
					}
				}
				addressLength = addressLength+description.length()+1;
			}
		}		
		voterCardObject.setCoordinates(voterCardCoordObject);
		return voterCardObject;
	}
	
	/* DESCRIPTION : Parses the content from TEXT_DETECTION of Vision API Call to find the values for VoterCard object
	 * INPUT : String content containing the entire text as detected by Vision API and VoterCard object
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseContent(String content, VoterCard voterCardObject){
		String tokens[] = content.split("\n");

		for(int index=0; index < tokens.length; index++){
			String token = tokens[index];
			
			//checking for voter id
			if(token.toLowerCase().contains(Constants.VoterCard.commission.toLowerCase())){
				String voterId = "";
				if(tokens[index+1].toLowerCase().contains(Constants.VoterCard.card.toLowerCase()))
				{
					if(tokens[index+2].toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()))
						voterId = tokens[index+1].substring(tokens[index+1].toLowerCase().lastIndexOf(Constants.VoterCard.card.toLowerCase())+4);
					else
						voterId = tokens[index+2];
				}
				else
					voterId = tokens[index+1];				
				voterCardObject.setVoterId(voterId);
			}
			//checking for elector's name
			else if(voterCardObject.getName().isEmpty() && token.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase())){
				String name = token.substring(token.toLowerCase().indexOf(Constants.VoterCard.name.toLowerCase())+4);
				if(!(tokens[index+1].toLowerCase().contains(Constants.VoterCard.father.toLowerCase()) ||
						tokens[index+1].toLowerCase().contains(Constants.VoterCard.mother.toLowerCase()) ||
						  tokens[index+1].toLowerCase().contains(Constants.VoterCard.husband.toLowerCase()) || 
						   tokens[index+1].toLowerCase().contains(Constants.VoterCard.name.toLowerCase())))
					name = name.concat(" "+tokens[index+1]);
				if(name.isEmpty())
					name = tokens[index-1];
				voterCardObject.setName(name.replace(Constants.colon, "").trim());
			}
			//checking for father's name
			else if(voterCardObject.getFatherName().isEmpty() && (token.toLowerCase().contains(Constants.VoterCard.father.toLowerCase()) ||
					  token.toLowerCase().contains(Constants.VoterCard.husband.toLowerCase()) || 
					  	token.toLowerCase().contains(Constants.VoterCard.name.toLowerCase()))){
				String name = token.substring(token.toLowerCase().lastIndexOf(Constants.VoterCard.name.toLowerCase())+4);
				if(!(tokens[index+1].toLowerCase().contains(Constants.VoterCard.sex.toLowerCase()) || 
						tokens[index+1].toLowerCase().contains(Constants.female.toLowerCase()) ||
						tokens[index+1].toLowerCase().contains(Constants.male.toLowerCase()) ||
						hasTwoDigit(tokens[index+1]) ||
						tokens[index+1].toLowerCase().contains(Constants.birth.toLowerCase()) || 
						tokens[index+1].toLowerCase().contains(Constants.VoterCard.age.toLowerCase())))
					name = name.concat(" "+tokens[index+1]);		
				voterCardObject.setFatherName(name.replace(Constants.colon, "").trim());
			}
			
			//checking for date of birth
			else if(token.toLowerCase().contains(Constants.birth.toLowerCase())){
				String dob = token.substring(token.toLowerCase().lastIndexOf(Constants.birth.toLowerCase())+5);
				voterCardObject.setDobDisplay(dob.replace(Constants.colon, "").trim());	
				//int j = i;
				while(voterCardObject.getDobDisplay().isEmpty() && index<tokens.length)
				{
					dob = tokens[++index];
					voterCardObject.setDobDisplay(dob.replace(Constants.colon, "").trim());
				}
					
			}
			//checking for age
			else if(token.toLowerCase().contains(Constants.VoterCard.age.toLowerCase())){
				String dob = token.substring(token.toLowerCase().lastIndexOf(Constants.VoterCard.age.toLowerCase())+3);
				if(hasTwoDigit(dob) || dob.toLowerCase().contains(Constants.VoterCard.years))
					voterCardObject.setAge(dob.trim());
				else{
					Pattern pattern = Pattern.compile("\\s[0-9]{2}\\s");
					Matcher matcher = pattern.matcher(content);
					if (matcher.find())
					{
					    //System.out.println(matcher.group(0));
						voterCardObject.setAge(dob +" : "+ matcher.group(0).trim());
					}
					else
						voterCardObject.setAge(dob);
					
				}
			}
			//checking for address
			else if(voterCardObject.getAddress().toString().isEmpty() && (token.toLowerCase().contains(Constants.VoterCard.address.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address1.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address2.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address3.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address4.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address5.toLowerCase()))){
				String addr = token.substring(token.toLowerCase().indexOf("s")+2);
				if(hasPin(content.substring(token.toLowerCase().indexOf("s")+2))){
					while(index<tokens.length-1 && !tokens[index].matches("^.+?\\d{6}$")){
						index++;						
						addr = addr + "\n" + tokens[index];		
					}
				}
				else
					addr = addr+"\n"+tokens[index+1]+"\n"+tokens[index+2]+"\n"+tokens[index+3];
				
				Address address = new ParseAddress().getAddress(addr.trim());
				voterCardObject.setAddress(address);
			}
			
		}
		//checking for sex
		String sex =Constants.male;
		if(content.toLowerCase().contains(Constants.female.toLowerCase()))
			sex = Constants.female;
		voterCardObject.setSex(sex);
		
		return voterCardObject;
	}
	
	/* DESCRIPTION : Parses a string to check whether it contains the age(two digit)
	 * INPUT : String content 
	 * OUTPUT : Boolean
	 * */
	public boolean hasTwoDigit(String str){			
		if(str.matches("^.+? [0-9]{2} .+?$") || str.matches("^.+? [0-9]{2}$"))
			return true;
		else 
			return false;		
	}
	
	/* DESCRIPTION : Parses a string to check whether it ends with the 	PIN(six digit)
	 * INPUT : String content 
	 * OUTPUT : Boolean
	 * */
	public boolean hasPin(String str){		
		return str.matches("^.+?\\d{6}$");		
	}

}
