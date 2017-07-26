package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opencv.core.Mat;

public class VisionAPICall {

	public JSONObject performOCR(String base64String, String fileType){
		
		String apiUrl = Constants.visionApiUrl;
		String apiKey = Constants.visionApiKey;
		JSONObject result = new JSONObject();
		String strBody = "";
		
		try{	
			URL url = new URL(apiUrl+apiKey);
			
			System.setProperty("https.proxyHost", Constants.proxyHost);
			System.setProperty("https.proxyPort", Constants.proxyPort);
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			
			//strBody="{ \"requests\":[ { \"image\":{ \"content\":\""+base64String+"\" }, \"features\":[ { \"type\":\"TEXT_DETECTION\", \"maxResults\":1000 } ] } ] }";
			strBody = getBody(base64String).toString();
			System.out.println(strBody);
			
			
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

			result.put(Constants.VisionResponse.body, output);
			
			conn.disconnect();
		}
		catch(Exception e){
			System.err.print("inside error in vision catch"+e);
		}

		return result;
	}
	
	public JSONObject getBody(String base64String){
        JSONObject Body = new JSONObject();
        JSONArray requests = new JSONArray();
        
        JSONObject requestBody = new JSONObject();
        
        JSONObject content = new JSONObject();
        content.put("content", base64String);
        requestBody.put("image", content);
        
        
        JSONArray features = new JSONArray();
        features.put(new JSONObject().put("type", "TEXT_DETECTION").put("maxResults", 1000));
        requestBody.put("features",features);
 
        requests.put(requestBody);
        
        Body.put("requests", requests);
 
        return Body;
 }

}
