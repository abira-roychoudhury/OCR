package apiClass;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.DrivingLicense;
import templates.DrivingLicenseCoord;
import templates.PanCardCoord;

public class ParseDrivingLicense {
	
	public DrivingLicense parseDrivingLicense(JSONArray textAnnotationArray){
		DrivingLicense obj = new DrivingLicense();
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
	
	public DrivingLicense parseCoord(JSONArray textAnnotationArray, DrivingLicense obj){
		DrivingLicenseCoord coord = new DrivingLicenseCoord();
		int d=0,n=0,m=0,a=0,p=1,b=0,i=1;
		int dl=0,nl=0,ml=0,al=0,pl=0,bl=0;
		
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString("description");
			if(description.toUpperCase().contains("DOB"))
				break;
		}
		for(;i<textAnnotationArray.length();i++){
			JSONObject jobj = (JSONObject) textAnnotationArray.get(i);
			String description = jobj.getString("description");
			
			//setting coordinates for dob
			if(obj.getDobDisplay().contains(description) && dl< obj.getDobDisplay().length()){
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
			
			//setting coordinates for bloodgroup
			else if(obj.getBloodGroup().contains(description) && bl< obj.getDobDisplay().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(b==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setBloodGroup(x, 0, j);
						coord.setDobDisplay(y, 1, j);
					}
					b++;
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
				bl = bl+description.length();
			}
			
			//setting coordinates for name
			else if(Arrays.asList(obj.getName().split("\\s+")).contains(description) && nl< obj.getName().length()){
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
			
			//setting coordinates for middle name
			else if(Arrays.asList(obj.getMiddleName().split("\\s+")).contains(description) && ml< obj.getMiddleName().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(m==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setMiddleName(x, 0, j);
						coord.setMiddleName(y, 1, j);
					}
					m++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setMiddleName(x, 0, j);
						coord.setMiddleName(y, 1, j);
					}
				}
				ml = ml+description.length()+1;
			}
			
			
			//setting coordinates for address
			else if(Arrays.asList(obj.getAddress().split("\\s+")).contains(description) && al< obj.getAddress().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(a==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
					a++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setAddress(x, 0, j);
						coord.setAddress(y, 1, j);
					}
				}
				al = al+description.length()+1;
			}
			
			//setting coordinates for PIN
			else if(obj.getPin().contains(description) && pl< obj.getPin().length()){
				JSONObject boundingPoly = jobj.getJSONObject("boundingPoly");
				if(p==0){
					for(int j=0;j<4;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setPin(x, 0, j);
						coord.setPin(y, 1, j);
					}
					p++;
				}
				else{
					for(int j=1;j<3;j++){ //iterate columns
						JSONArray vertices=(JSONArray)boundingPoly.getJSONArray("vertices");
						JSONObject xy = (JSONObject) vertices.get(j);
						int x = xy.getInt("x");
						int y = xy.getInt("y");
						coord.setPin(x, 0, j);
						coord.setPin(y, 1, j);
					}
				}
				pl = pl+description.length();
			}
			
		}
		
		
		obj.setCoordinates(coord);
		return obj;
	}
	
	public DrivingLicense parseContent(String content, DrivingLicense obj){
		String tokens[] = content.split("\n");
		
		for(int i=0; i < tokens.length; i++){
			String token = tokens[i];
			//Find Date Of Birth
			if(token.toLowerCase().contains("dob")){ 
				String dates[] = token.split(" "); 
				for(String date : dates){
					if(date.contains("-")){
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						Calendar cal = Calendar.getInstance();
						try{
							cal.setTime(sdf.parse(date));
							
							//System.out.println("date:"+cal.getTime().toString());
						}
						catch(Exception e)
						{
							System.err.println(e);
						}
						obj.setDOB(cal);
						obj.setDobDisplay(date);
						break;
					}
				}				
				//Find BloodGroup
				String bloodGroup = token.substring(token.lastIndexOf(":")+1);
				obj.setBloodGroup(bloodGroup.replace(":", "").trim());
			}
			//Find Name
			else if(token.toLowerCase().contains("name")){
				String name = token.substring(token.toLowerCase().lastIndexOf("name")+4);
				obj.setName(name.toUpperCase().replace(":", "").trim());
				
				//Find MiddleName
				String middleName = tokens[i+1].substring(tokens[i+1].toLowerCase().lastIndexOf("of")+2);
				obj.setMiddleName(middleName.toUpperCase().replace(":", "").trim());
				i++;				
			}
			//Find Address
			else if(token.toLowerCase().contains("add")){
				String address = token.substring(token.toLowerCase().lastIndexOf("add")+3);
				if(!tokens[i+1].toLowerCase().contains("pin"))
					address = address.concat(tokens[i+1]);
				obj.setAddress(address.replace(":", ""));
				i++;
			}
			//Find PIN
			
			else if(token.toLowerCase().contains("pin")){
				try{
				String pin = token.substring(token.toLowerCase().lastIndexOf("pin")+3,token.toLowerCase().lastIndexOf("pin")+3+7);
				obj.setPin(pin.replace(":", ""));
				}catch(Exception e){
					obj.setPin("");
				}
			}
		}
		return obj;
	}
}
