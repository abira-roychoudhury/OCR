package apiClass;

import apiClass.EntityType;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.PanCard;
import templates.PanCardCoord;

public class ParsePanCard {
	
	public PanCard parsePanCard(JSONArray textAnnotationArray){
		PanCard obj = new PanCard();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString("description");
			obj = parseContent(descriptionStr,obj);		
			obj = parseCoord(textAnnotationArray,obj);	
		}catch(JSONException je){ 
			je.printStackTrace();
		}catch(Exception e){ 
			e.printStackTrace();
		 }
		return obj;
	}
	
	
	public PanCard parseCoord(JSONArray textAnnotationArray, PanCard obj){
		int n=0,f=0,d=0,p=0,i=1;
		int nl=0,fl=0,dl=0,pl=0;
		PanCardCoord coord = new PanCardCoord();
		
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString("description");
			if(description.toUpperCase().contains("INDIA"))
				break;
		}
		
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString("description");
			
			//setting the coordinates for name
			if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl< obj.getName().length()){
				System.out.println("name found");
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(n==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						System.out.println("coords : "+x+" "+y);
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
					n++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setName(x, 0, j);
						coord.setName(y, 1, j);
					}
				}
				nl = nl+description.length()+1;
			}
			
			
			//setting the coordinates for father's name
			else if(Arrays.asList(obj.getFatherName().split("\\s+")).contains(description) && fl< obj.getFatherName().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(f==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
					f++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setFatherName(x, 0, j);
						coord.setFatherName(y, 1, j);
					}
				}
				fl = fl+description.length()+1;
			}
			
			//setting the coordinates for date 
			else if(obj.getDobDisplay().contains(description) && dl< obj.getDobDisplay().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(d==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
					d++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setDobDisplay(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
				}
				dl = dl+description.length();
			}
			
			//setting the coordinates for PanCard number 
			else if(obj.getPanNumber().contains(description) && pl<obj.getPanNumber().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(p==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setPanNumber(x, 0, j);
						coord.setPanNumber(y, 1, j);
					}
					p++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setPanNumber(x, 0, j);
						coord.setPanNumber(y, 1, j);
					}
				}
				pl = pl+description.length();
			}			
		}
		
		obj.setCoordinates(coord);
		return obj;
	}
	
	
	public PanCard parseContent(String content, PanCard obj){
		
		String splitDesc[] = content.split("\\n");
		int i;
		String name="",fname="",dob="",pan="";
		Calendar cal = Calendar.getInstance();
		if(content.contains("INDIA"))
			
		{ //New type of PAN Card
		 for(i = 0;i<splitDesc.length;i++){
			if(splitDesc[i].indexOf("INDIA")>-1)
			{
				//extracting name
				name=splitDesc[++i];
				if(EntityType.getType(name).equals("Non"))
					name=splitDesc[++i]; 
				
				//extracting father's name
				fname=splitDesc[++i];
				if(EntityType.getType(fname).equals("Non"))
					fname=splitDesc[++i];
				
				//extracting dob
				dob=splitDesc[++i];
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try{
					cal.setTime(sdf.parse(dob));
					//System.out.println("date:"+cal.getTime().toString());
				}catch(Exception e){
					System.err.println(e);	}	
				
				//extracting pan
				pan=splitDesc[i+2];
			    break;
			}
		 }		
		}
		else
		{
			//Old type of PAN card
			for(i = 0;i<splitDesc.length;i++){
				if(splitDesc[i].toUpperCase().contains("PERMANENT ACCOUNT NUMBER"))
					pan = splitDesc[i+1];
				else if(splitDesc[i].toUpperCase().contains("NAME"))
				{
					if(splitDesc[i].toUpperCase().contains("FATHER"))
						{
							fname = splitDesc[i+1];
							if(splitDesc[i+2].toUpperCase().contains("DATE"))
								dob = splitDesc[i+3];
							else
							{
								fname = fname + " " + splitDesc[i+2];
								dob = splitDesc[i+4];
							}
							break;
						}
					else
						{
							name = splitDesc[i+1];
							if(!splitDesc[i+2].toUpperCase().contains("FATHER"))
								name = name + " " + splitDesc[i+2];
						}					
				 }					  
			}
		}
		//setting the PanCard object
		obj.setName(name);
		obj.setFatherName(fname);
		obj.setDob(cal);
		obj.setPanNumber(pan);
		obj.setDobDisplay(dob);
		
		return obj;
	}

	
}

