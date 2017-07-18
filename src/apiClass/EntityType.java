package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONObject;


public class EntityType {
	
	public static String getType(String desc){
		JSONObject result =  NlpCall(desc);
		JSONObject body = new JSONObject(result.get(Constants.NLPResponse.body));
		String bodyString=result.getString(Constants.NLPResponse.body);
		JSONObject bodyObject=new JSONObject(bodyString);
		JSONArray entitiesArray=(JSONArray) bodyObject.getJSONArray(Constants.NLPResponse.entities);
		if(entitiesArray != null && entitiesArray.length() > 0 )
		{
			JSONObject entityDict=entitiesArray.getJSONObject(0);
			String entityName = entityDict.getString(Constants.NLPResponse.name);
			String entityType = entityDict.getString(Constants.NLPResponse.type);
			return entityType;
		}		
		return Constants.NLPResponse.non;
	}
	
	public static JSONArray getEntityArray(String desc){
		JSONObject result =  NlpCall(desc);
		JSONObject body = new JSONObject(result.get(Constants.NLPResponse.body));
		String bodyString=result.getString(Constants.NLPResponse.body);
		JSONObject bodyObject=new JSONObject(bodyString);
		JSONArray entitiesArray=(JSONArray) bodyObject.getJSONArray(Constants.NLPResponse.entities);
		return entitiesArray;
	}
	
	public static JSONObject NlpCall(String text){
	String apiUrl = Constants.nlpApiUrl;
	String apiKey = Constants.nlpApiKey;
	JSONObject result = new JSONObject();
	
	try{	
		
		URL url = new URL(apiUrl+apiKey);
		
		System.setProperty("https.proxyHost", Constants.proxyHost);
		System.setProperty("https.proxyPort", Constants.proxyPort);
		
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

		result.put(Constants.NLPResponse.body, output);
		conn.disconnect();
		//System.out.println(result);
		
	}
	catch(Exception e){
		System.err.print("inside error in nlp catch"+e);
	}
	return result;
 }
}

