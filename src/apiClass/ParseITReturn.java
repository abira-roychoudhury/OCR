package apiClass;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import templates.ITReturn;

public class ParseITReturn {
	
	/* DESCRIPTION : Takes the response from Vision API Call and parses it to create an AadharCard object
	 * INPUT : JSONArray response from Vision API Call and String filePath of the image file uploaded
	 * OUTPUT : AadharCard object
	 * */
	public ITReturn parseAadharCard(JSONArray textAnnotationArray,String filePath){
		ITReturn obj = new ITReturn();
		try{
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");
			obj = parseContent(descriptionStr,filePath,obj);		
			obj = parseCoord(textAnnotationArray,obj);	
		}catch(JSONException je){ je.printStackTrace();
		}catch(Exception e){ e.printStackTrace();
		}
		return obj;
	}

	private ITReturn parseCoord(JSONArray textAnnotationArray, ITReturn obj) {
		// TODO Auto-generated method stub
		return null;
	}

	private ITReturn parseContent(String descriptionStr, String filePath, ITReturn obj) {
		
		return obj;
	}

}
