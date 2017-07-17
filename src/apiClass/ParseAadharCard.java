package apiClass;

import java.util.Arrays;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.AadharCard;
import templates.AadharCardCoord;

public class ParseAadharCard {
	
	public AadharCard parseAadharCard(JSONArray textAnnotationArray){
		AadharCard obj = new AadharCard();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString("description");
			obj = parseContent(descriptionStr,obj);		
			obj = parseCoord(textAnnotationArray,obj);	
			}catch(JSONException je){ je.printStackTrace();
			}catch(Exception e){ e.printStackTrace();
			 }
			return obj;
		
	}
	
	
	public AadharCard parseCoord(JSONArray textAnnotationArray,AadharCard obj){
		AadharCardCoord coord = new AadharCardCoord();
		int n=0,yr=0,d=0,g=0,a=0,ad=0,i=1;
		int nl=0,yrl=0,dl=0,gl=0,al=0,adl=0;
		String year =  Integer.toString(obj.getYearOfBirth());
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString("description");
			
			//setting the coordinates for name
			if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl< obj.getName().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(n==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
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

			//setting the coordinates for year of birth
			else if(obj.getYearOfBirth()!=0 && year.contains(description) && yrl<year.length()){
					JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
					if(yr==0){
						for(int j=0;j<4;j++){ //iterate columns
							JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
							JSONObject xy = (JSONObject) vertices.get(j);
							int x = xy.getInt("x");
							int y = xy.getInt("y");
							coord.setYearOfBirth(x, 0, j);
							coord.setYearOfBirth(y, 1, j);
						}
						yr++;
					}
					else{
						for(int j=1;j<3;j++){ //iterate columns
							JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
							JSONObject xy = (JSONObject) vertices.get(j);
							int x = xy.getInt("x");
							int y = xy.getInt("y");
							coord.setYearOfBirth(x, 0, j);
							coord.setYearOfBirth(y, 1, j);
						}
					}
					yrl = yrl+description.length();
				}
			
			//setting the coordinates for date of birth
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

			//setting the coordinates for gender
			else if(obj.getGender().contains(description) && gl< obj.getGender().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(g==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setGender(x, 0, j);
						coord.setGender(y, 1, j);
					}
					g++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setGender(x, 0, j);
						coord.setGender(y, 1, j);
					}
				}
				gl = gl+description.length();	
			}

			//setting the coordinates for aadhar card number
			else if(Arrays.asList(obj.getAadharNumber().split("\\s+")).contains(description) && al< obj.getAadharNumber().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(a==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setAadharNumber(x, 0, j);
						coord.setAadharNumber(y, 1, j);
					}
					a++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setAadharNumber(x, 0, j);
						coord.setAadharNumber(y, 1, j);
					}
				}
				al = al+description.length()+1;	
			}
		}
		obj.setCoordinates(coord);
		return obj;
	}
	
	
	public AadharCard parseContent(String content,AadharCard obj){
		content = content.concat("\\n EOF");
		
		String splitDesc[] = content.split("\\n");
		int i,year=0;
		Calendar cal = Calendar.getInstance();
		String name="",gender="",aadharNumber="",dobstr="";
		
		
		for(i = 0;i<splitDesc.length;i++)
		{
			if(splitDesc[i].matches("\\d{4} \\d{4} \\d{4}"))
				aadharNumber = splitDesc[i];
			else if(splitDesc[i].contains("DOB"))
			{
				dobstr = splitDesc[i].substring(splitDesc[i].lastIndexOf("DOB")+3).replace(":", "").trim();
				SimpleDateFormat sdf;
				if(dobstr.contains("//"))
				    sdf = new SimpleDateFormat("dd/MM/yyyy");
				else
					sdf = new SimpleDateFormat("dd-MM-yyyy");
				try{
					cal.setTime(sdf.parse(dobstr));
				}catch(Exception e){
					System.err.println(e);
				}
				
				name = splitDesc[i-1];					
			}
			else if(splitDesc[i].contains("Birth"))
			{
				String yearstr = splitDesc[i].substring(splitDesc[i].lastIndexOf("Birth")+5).replace(":", "").trim();
				year = Integer.parseInt(yearstr);
				
				name = splitDesc[i-1];
				
			}
		}
		
		if(content.contains("Male")) 
			gender = "Male";			
		else if(content.contains("Female")) 
			gender = "Female";
		
		obj.setName(name);
		obj.setGender(gender);
		obj.setAadharNumber(aadharNumber);
		obj.setYearOfBirth(year);
		obj.setDob(cal);
		obj.setDobDisplay(dobstr);
		return obj;
	}
	
}
