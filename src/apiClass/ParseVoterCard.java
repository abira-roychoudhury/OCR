package apiClass;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.VoterCard;
import templates.VoterCardCoord;

public class ParseVoterCard {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an VoterCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseVoterCard(JSONArray textAnnotationArray){
		VoterCard obj = new VoterCard();
		try{
		JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
		String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
		obj = parseContent(descriptionStr,obj);		
		obj = parseCoord(textAnnotationArray,obj);	
		}catch(JSONException je){ 
			je.printStackTrace();
		}catch(Exception e){ 
			e.printStackTrace();
		 }
		return obj;
	}
	
	/* DESCRIPTION : Finds the co-ordinate for boundary blocking from Vision API Call response by matching with the values from VoterCard object
	 * INPUT :  JSONArray response from Vision API Call and VoterCard object
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseCoord(JSONArray textAnnotationArray, VoterCard obj){
		VoterCardCoord coord = new VoterCardCoord();
		int n=0,f=0,d=0,a=0,s=0,v=0,ad=0,i=1;
		int nl=0,fl=0,dl=0,al=0,sl=0,vl=0,adl=0;
				
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString(Constants.VisionResponse.description);
			//System.out.println(description);
			if(description.equals("/") || description.equals(":"))
				continue;
			
			
			//Setting coordinates VoterId
			if(obj.getVoterId().contains(description) && vl< obj.getVoterId().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(v==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setVoterId(x, 0, j);
						coord.setVoterId(y, 1, j);
					}
					v++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setVoterId(x, 0, j);
						coord.setVoterId(y, 1, j);
					}
				}
				vl = vl+description.length();
			}
			//setting the coordinates for name
			else if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl< obj.getName().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(n==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
					n++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(coord.getName()[0][j]<x)
							coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
				}
				nl = nl+description.length()+1;
			}
			
			//setting the coordinates for father's name
			else if(Arrays.asList(obj.getFatherName().split("\\s+")).contains(description) && fl< obj.getFatherName().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(f==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
					f++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(coord.getFatherName()[0][j]<x)
							coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
				}
				fl = fl+description.length()+1;
			}
			
			//setting the coordinates for sex 
			else if(obj.getSex().toLowerCase().contains(description.toLowerCase()) && sl<obj.getSex().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(s==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setSex(x, 0, j);
						coord.setSex(y, 1, j);
					}
					s++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setSex(x, 0, j);
						coord.setSex(y, 1, j);
					}
				}
				sl = sl+description.length();
			}	
			
			//setting the coordinates for date 
			else if(obj.getDobDisplay().contains(description) && dl< obj.getDobDisplay().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(d==0){
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
				else{
					for(int j=1;j<3;j++){ //iterate columns
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
			
			//setting the coordinates for age 
			else if(Arrays.asList(obj.getAge().split("\\s+")).contains(description) && al< obj.getAge().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(a==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAge(x, 0, j);
						coord.setAge(y, 1, j);
					}
					a++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAge(x, 0, j);
						coord.setAge(y, 1, j);
					}
				}
				al = al+description.length()+1;
			}
			//setting the coordinates for address
			else if(Arrays.asList(obj.getAddress().split("\\s+")).contains(description) && adl< obj.getAddress().length()){
				JSONObject boundingPoly = jobj.getJSONObject(Constants.VisionResponse.boundingPoly);
				if(ad==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
					ad++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray(Constants.VisionResponse.vertices);
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt(Constants.VisionResponse.x);
						int y = xy.getInt(Constants.VisionResponse.y);
						if(coord.getAddress()[0][j]<x)
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
	
	/* DESCRIPTION : Parses the content from TEXT_DETECTION of Vision API Call to find the values for VoterCard object
	 * INPUT : String content containing the entire text as detected by Vision API and VoterCard object
	 * OUTPUT : VoterCard object
	 * */
	public VoterCard parseContent(String content, VoterCard obj){
		String tokens[] = content.split("\n");

		for(int i=0; i < tokens.length; i++){
			String token = tokens[i];
			
			//checking for voter id
			if(token.toLowerCase().contains(Constants.VoterCard.commission.toLowerCase())){
				String voterId = "";
				if(tokens[i+1].toLowerCase().contains(Constants.VoterCard.card.toLowerCase()))
				{
					if(tokens[i+2].toLowerCase().contains(Constants.VoterCard.elector.toLowerCase()))
						voterId = tokens[i+1].substring(tokens[i+1].toLowerCase().lastIndexOf(Constants.VoterCard.card.toLowerCase())+4);
					else
						voterId = tokens[i+2];
				}
				else
					voterId = tokens[i+1];
				obj.setVoterId(voterId);
			}
			//checking for elector's name
			else if(obj.getName().isEmpty() && token.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase())){
				String name = token.substring(token.toLowerCase().indexOf(Constants.VoterCard.name.toLowerCase())+4);
				if(!(tokens[i+1].toLowerCase().contains(Constants.VoterCard.father.toLowerCase()) ||
						tokens[i+1].toLowerCase().contains(Constants.VoterCard.mother.toLowerCase()) ||
						  tokens[i+1].toLowerCase().contains(Constants.VoterCard.husband.toLowerCase()) || 
						   tokens[i+1].toLowerCase().contains(Constants.VoterCard.name.toLowerCase())))
					name = name.concat(" "+tokens[i+1]);				
				obj.setName(name.replace(Constants.colon, "").trim());
			}
			//checking for father's name
			else if(obj.getFatherName().isEmpty() && (token.toLowerCase().contains(Constants.VoterCard.father.toLowerCase()) ||
					  token.toLowerCase().contains(Constants.VoterCard.husband.toLowerCase()) || 
					  	token.toLowerCase().contains(Constants.VoterCard.name.toLowerCase()))){
				String name = token.substring(token.toLowerCase().lastIndexOf(Constants.VoterCard.name.toLowerCase())+4);
				if(!(tokens[i+1].toLowerCase().contains(Constants.VoterCard.sex.toLowerCase()) || 
						tokens[i+1].toLowerCase().contains(Constants.female.toLowerCase()) ||
						tokens[i+1].toLowerCase().contains(Constants.male.toLowerCase()) ||
						hasTwoDigit(tokens[i+1]) ||
						tokens[i+1].toLowerCase().contains(Constants.birth.toLowerCase()) || 
						tokens[i+1].toLowerCase().contains(Constants.VoterCard.age.toLowerCase())))
					name = name.concat(" "+tokens[i+1]);		
				obj.setFatherName(name.replace(Constants.colon, "").trim());
			}
			
			//checking for date of birth
			else if(token.toLowerCase().contains(Constants.birth.toLowerCase())){
				String dob = token.substring(token.toLowerCase().lastIndexOf(Constants.birth.toLowerCase())+5);
				obj.setDobDisplay(dob.replace(Constants.colon, "").trim());	
				//int j = i;
				while(obj.getDobDisplay().isEmpty() && i<tokens.length)
				{
					dob = tokens[++i];
					obj.setDobDisplay(dob.replace(Constants.colon, "").trim());
				}
					
			}
			//checking for age
			else if(token.toLowerCase().contains(Constants.VoterCard.age.toLowerCase())){
				String dob = token.substring(token.toLowerCase().lastIndexOf(Constants.VoterCard.age.toLowerCase())+3);
				if(hasTwoDigit(dob) || dob.toLowerCase().contains(Constants.VoterCard.years))
					obj.setAge(dob.trim());
				else{
					Pattern pattern = Pattern.compile("\\s[0-9]{2}\\s");
					Matcher matcher = pattern.matcher(content);
					if (matcher.find())
					{
					    //System.out.println(matcher.group(0));
					    obj.setAge(dob +" : "+ matcher.group(0).trim());
					}
					else
						obj.setAge(dob);
					
				}
			}
			//checking for address
			else if(obj.getAddress().isEmpty() && (token.toLowerCase().contains(Constants.VoterCard.address.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address1.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address2.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address3.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address4.toLowerCase()) ||
													token.toLowerCase().contains(Constants.VoterCard.address5.toLowerCase()))){
				String addr = token.substring(token.toLowerCase().indexOf("s")+2);
				if(hasPin(content.substring(token.toLowerCase().indexOf("s")+2))){
					while(i<tokens.length-1 && !tokens[i].matches("^.+?\\d{6}$")){
						i++;						
						addr = addr + "\n" + tokens[i];		
					}
				}
				else
					addr = addr+"\n"+tokens[i+1]+"\n"+tokens[i+2]+"\n"+tokens[i+3];
				
				obj.setAddress(addr.trim());			
			}
			
		}
		//checking for sex
		String sex =Constants.male;
		if(content.toLowerCase().contains(Constants.female.toLowerCase()))
			sex = Constants.female;
		obj.setSex(sex);
		
		return obj;
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
