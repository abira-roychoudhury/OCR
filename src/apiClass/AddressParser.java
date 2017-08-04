package apiClass;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class AddressParser {
	
	public JSONObject getStateCity(String address){
		JSONObject stateCity = new JSONObject();
		
		JSONParser parser = new JSONParser();

		String STATE = "";
		String CITY = "";
		
        try{
    		
        	JSONObject StateCityJSON = (JSONObject) parser.parse(new FileReader("list_of_cities_and_state1.json"));
        	
    		Map<String, String[]> mapStateCity = new HashMap<String,String[]>();
    		List<String> listOfCities = new LinkedList<String>();
    		
    		for(Object key : StateCityJSON.keySet()){
    			
    			JSONArray cities =  (JSONArray) StateCityJSON.get(key);
    			
    			//System.out.println(cities.toString());
    			
    			String cityList[] = (String[]) cities.toArray(new String[mapStateCity.size()]);
    			
    			mapStateCity.put((String)key, cityList);
    			
    			listOfCities.addAll(Arrays.asList(cityList));
    			
    		}
    		//String address = "Do IT ARoet, V, R, D/O Joy Roychoud ury, 78 /2 , COLLEGE ROAD, Haora PGES (ITT, TIGT Corporation, Haora,(FrCASH ATTIASTa ), Wet Bengal - 711103";
    				
    		
    		for(Map.Entry<String, String[]> entry : mapStateCity.entrySet())
    		{
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
    		
    		
    		
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        stateCity.put("city", CITY);
		stateCity.put("state", STATE);
		
		return stateCity;
	}

}
