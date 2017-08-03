package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class VisionAPICall {
	
	/* DESCRIPTION : Makes the call to Vision API and returns the response
	 * INPUT : String base64String of the image on which OCR is to be performed
	 * OUTPUT : JSONObject of the response from Vision API
	 * */
	public JSONObject performOCR(String base64String){
		
		String apiUrl = Constants.visionApiUrl;
		String apiKey = Constants.visionApiKey;
		JSONObject result = new JSONObject();
		String strBody = "";
		
		try{
			Date d1 = new Date();
			URL url = new URL(apiUrl+apiKey);
			
			System.setProperty("jdk.http.auth.tunneling.disabledSchemes",""); 
			try{
				System.out.println("System proxy : "+ System.setProperty("https.proxyHost", Constants.proxyHost));
				System.out.println("System port : "+ System.setProperty("https.proxyPort", Constants.proxyPort));
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
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
			Date d2 = new Date();
			
			
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			Date d3 = new Date();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			Date d4 = new Date();
			//StringBuffer output = new StringBuffer();
			StringBuilder output = new StringBuilder();
			
			String op;
			while ((op = br.readLine()) != null) {
				//output.append(op);
				output.append(op);
				//System.out.println(output);
			}
			Date d5 = new Date();

			result.put(Constants.VisionResponse.body, output.toString());
			
			conn.disconnect();
			Date d6 = new Date();


			System.out.println("d2"+ (d2.getTime()-d1.getTime() ) + "\n d3"+ (d3.getTime()-d2.getTime() ) + "\n d4"+ (d4.getTime()-d3.getTime() ) + "\n d5"+ (d5.getTime()-d4.getTime() ) + "\n d6"+ (d6.getTime()-d5.getTime() ) );
		}
		catch(Exception e){
			System.err.print("inside error in vision catch"+e);
		}
		return result;
	}
	
	/* DESCRIPTION : Creates the JSON body that needs to be sent to Vision API as Request
	 * INPUT : String base64String of the image on which OCR is to be performed
	 * OUTPUT : JSONObject of Request body
	 * */
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
