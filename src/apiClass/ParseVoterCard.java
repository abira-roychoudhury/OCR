package apiClass;

import java.util.Arrays;

import apiClass.EntityType;
import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.VoterCard;
import templates.VoterCardCoord;

public class ParseVoterCard {
	
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
	
	public VoterCard parseCoord(JSONArray textAnnotationArray, VoterCard obj){
		VoterCardCoord coord = new VoterCardCoord();
		int n=0,f=0,d=0,s=0,v=0,i=1;
		int nl=0,fl=0,dl=0,sl=0,vl=0;
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString(Constants.VisionResponse.description);
			if(description.toUpperCase().contains(Constants.VoterCard.card.toUpperCase()))
				break;
		}
		
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString(Constants.VisionResponse.description);
			if(description.equals("/"))
				continue;
			
			//Setting coordinates VoterId
			if(Arrays.asList(obj.getVoterId().split("\\s+")).contains(description) && vl< obj.getVoterId().length()){
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
				vl = vl+description.length()+1;
			}
			//setting the coordinates for name
			else if(Arrays.asList(obj.getName().split("\\s+")).contains(description.toUpperCase()) && nl< obj.getName().length()){
				System.out.println("inside name");
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
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
				}
				nl = nl+description.length()+1;
			}
			
			//setting the coordinates for father's name
			else if(Arrays.asList(obj.getFatherName().split("\\s+")).contains(description.toUpperCase()) && fl< obj.getFatherName().length()){
				System.out.println("inside father's name");
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
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
				}
				fl = fl+description.length()+1;
			}
			
			//setting the coordinates for PanCard number 
			else if(obj.getSex().contains(description) && sl<obj.getSex().length()){
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
		}		
		obj.setCoordinates(coord);
		return obj;
	}
	
	public VoterCard parseContent(String content, VoterCard obj){
		String tokens[] = content.split("\n");

		for(int i=0; i < tokens.length; i++){

			String token = tokens[i];

			if(token.toLowerCase().contains(Constants.VoterCard.card.toLowerCase())){
				String voterId = tokens[i+1];
				obj.setVoterId(voterId);
			}
			else if(token.toLowerCase().contains(Constants.VoterCard.elector.toLowerCase())){
				String name = token.substring(token.toLowerCase().indexOf(Constants.VoterCard.name.toLowerCase())+4);
				
				if(EntityType.getType(tokens[i+1]).equals(Constants.NLPResponse.person.toUpperCase()))
					name = name.concat(" "+tokens[i+1]);
				
				obj.setName(name.toUpperCase().replace(Constants.colon, "").trim());
			}
			else if(token.toLowerCase().contains(Constants.VoterCard.father.toLowerCase())){
				String name = token.substring(token.toLowerCase().lastIndexOf(Constants.VoterCard.name.toLowerCase())+4);
				
				if(EntityType.getType(tokens[i+1]).equals(Constants.NLPResponse.person.toUpperCase()))
					name = name.concat(" "+tokens[i+1]);
				
				obj.setFatherName(name.toUpperCase().replace(Constants.colon, "").trim());
			}
			else if(token.toLowerCase().contains(Constants.VoterCard.sex.toLowerCase())){
				String sex = Constants.male;
				if(token.toLowerCase().contains(Constants.female.toLowerCase()))
					sex = Constants.female;
				obj.setSex(sex);
			}
			else if(token.toLowerCase().contains(Constants.birth.toLowerCase())){
				String dob = token.substring(token.toLowerCase().lastIndexOf(Constants.birth.toLowerCase())+5);
				obj.setDobDisplay(dob.toUpperCase().replace(Constants.colon, "").trim());
			}
		}
		return obj;
	}

}
