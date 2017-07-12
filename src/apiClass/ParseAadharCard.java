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
		int n=0,yr=0,d=0,g=0,a=1,ad=1,i=1;
		int nl=0,yrl=0,dl=0,gl=0,al=0,adl=0;
		
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
		}
		obj.setCoordinates(coord);
		return obj;
	}
	
	
	public AadharCard parseContent(String content,AadharCard obj){
		String splitDesc[] = content.split("\\n");
		int i,year=0;
		Calendar cal = Calendar.getInstance();
		String name="",gender="",aadharNumber="",dobstr="";
		for(i = 0;i<splitDesc.length;i++)
		{
			if(splitDesc[i].contains("DOB"))
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
				aadharNumber = splitDesc[i+2];
				name = splitDesc[i-1];	
				break;
			}
			else if(splitDesc[i].contains("Birth"))
			{
				String yearstr = splitDesc[i].substring(splitDesc[i].lastIndexOf("Birth")+5).replace(":", "").trim();
				year = Integer.parseInt(yearstr);
				aadharNumber = splitDesc[i+2];
				name = splitDesc[i-1];
				break;
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
