package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;


public class EntityType {
	
	public static String getType(String desc){
		JSONObject result =  NlpCall(desc);
		JSONObject body = new JSONObject(result.get("body"));
		String bodyString=result.getString("body");
		JSONObject bodyObject=new JSONObject(bodyString);
		JSONArray entitiesArray=(JSONArray) bodyObject.getJSONArray("entities");
		if(entitiesArray != null && entitiesArray.length() > 0 )
		{
			JSONObject entityDict=entitiesArray.getJSONObject(0);
			String entityName = entityDict.getString("name");
			String entityType = entityDict.getString("type");
			//System.out.println(entityName+"  "+entityType);
			return entityType;
		}		
		return "Non";
	}
	
	public static JSONArray getEntityArray(String desc){
		JSONObject result =  NlpCall(desc);
		JSONObject body = new JSONObject(result.get("body"));
		String bodyString=result.getString("body");
		JSONObject bodyObject=new JSONObject(bodyString);
		JSONArray entitiesArray=(JSONArray) bodyObject.getJSONArray("entities");
		return entitiesArray;
	}
	
	public static JSONObject NlpCall(String text){
	String apiUrl = "https://language.googleapis.com/v1beta2/documents:analyzeEntities?";
	String apiKey = "key=AIzaSyBvIc_jHtviAsiXwQbQbAY3LdMdhY2BBQ8";
	JSONObject result = new JSONObject();
	
	try{	
		
		URL url = new URL(apiUrl+apiKey);
		
		System.setProperty("https.proxyHost", "hjproxy.persistent.co.in");
		System.setProperty("https.proxyPort", "8080");
		
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		String strBody="{\"document\": { \"type\": \"PLAIN_TEXT\",\"content\": \""+text+"\" },\"encodingType\": \"UTF16\"}";
		
		OutputStream os = conn.getOutputStream();
		os.write(strBody.getBytes());
		os.flush();
		
		if (conn.getResponseCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}		
		
		BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
		
		String output = "";
		String op;
		while ((op = br.readLine()) != null) {
			output = output.concat(op);
			//System.out.println(output);
		}

		result.put("body", output);
		conn.disconnect();
		//System.out.println(result);
		
	}
	catch(Exception e){
		System.err.print("inside error in nlp catch"+e);
	}
	return result;
 }
}

