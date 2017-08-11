package apiClass;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import modal.Constants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import templates.Address;

public class ParseAddress {
	
	private JSONObject StateCityJSON = null;
	private List<String> listOfCities = null;	
	private Map<String, String[]> mapStateCity = null;
	
	
	/* DESCRIPTION : Constructor reads file and loads the required data structure
	  * */
	public ParseAddress() {
		
		try {
			JSONParser parser = new JSONParser();
			StateCityJSON = (JSONObject) parser.parse(new FileReader(Constants.jsonFileOfStateCity));
        	
    		mapStateCity = new HashMap<String,String[]>();
    		listOfCities = new LinkedList<String>();
    		
    		for(Object key : StateCityJSON.keySet()){    			
    			JSONArray cities =  (JSONArray) StateCityJSON.get(key);
    			String cityList[] = (String[]) cities.toArray(new String[mapStateCity.size()]);    			
    			mapStateCity.put((String)key, cityList);    			
    			listOfCities.addAll(Arrays.asList(cityList)); 			
    		}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
	}
	
	
	/* DESCRIPTION : Fetches state city pincode and addressline from a complete address string
	 * INPUT : String address
	 * OUTPUT : Address Object
	 * */
	public Address getAddress(String address){		
		Address addressObject = new Address();
		
		String STATE = "";
		String CITY = "";
		String PIN = "";
		String ADDRESSLINE = "";
		
        try{
    		for(Map.Entry<String, String[]> entry : mapStateCity.entrySet()){
    			if(address.toUpperCase().contains(entry.getKey().toUpperCase())){    				
    				STATE = entry.getKey();    				
    				String cities[] = entry.getValue();    				
    				for(String city : cities ){    					
    					if(address.toUpperCase().contains(city.toUpperCase())){
    						CITY = city.toUpperCase();
    						break;
    					}    					
    				}
    				break;
    			}
    		}
    		    	
    		if(STATE.equals("")){    			
    			for (String city : listOfCities){    				
    				if(city != null && address.toUpperCase().contains(city)){
    					CITY = city;
    					break;
    				}    				
    			}    			
    		}
    		
    		//GET PIN
    		Pattern pattern = Pattern.compile("[0-9]{6}");
			Matcher matcher = pattern.matcher(address);
			
			if (matcher.find())
				PIN = matcher.group(0).trim();
			
			//GET ADDRESSLINE
			ADDRESSLINE = address.toUpperCase().replace(CITY, "").replace(STATE, "").replace(PIN, "");    		
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        addressObject.setCity(CITY);
        addressObject.setState(STATE);
        addressObject.setZipCode(PIN);
        addressObject.setAddressLine(ADDRESSLINE);
		
		return addressObject;
	}

	
	
	/*public static void main(String[] args) {
		
		ParseAddress addressParser = new ParseAddress();
		
		String address = "Do IT ARoet, V, R, D/O Joy Roychoud ury, 78 /2 , COLLEGE ROAD, Haora PGES (ITT, TIGT Corporation, Haora,(FrCASH ATTIASTa ), West Bengal - 711103";
		
		Address addressObject = addressParser.getAddress(address);
		
		System.out.println(addressObject.getAddressLine()+"\n"+addressObject.getCity()+"\n"+addressObject.getState()+"\n"+addressObject.getZipCode());
		
	}*/
	
}
