package apiClass;

import java.util.Calendar;
import java.text.SimpleDateFormat;

import templates.AadharCard;

public class ParseAadharCard {
	
	public AadharCard parseAadhaeCard(String content){
		AadharCard obj = new AadharCard();
		String splitDesc[] = content.split("\\n");
		int i,year=0;
		Calendar cal = Calendar.getInstance();
		String name="",gender="",aadharNumber="";
		for(i = 0;i<splitDesc.length;i++)
		{
			if(splitDesc[i].contains("DOB"))
			{
				String dobstr = splitDesc[i].substring(splitDesc[i].lastIndexOf("DOB")+3).replace(":", "").trim();
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try{
					cal.setTime(sdf.parse(dobstr));
				}catch(Exception e){
					System.err.println(e);
				}
				aadharNumber = splitDesc[i+2];
				name = splitDesc[i-1];
				
			}
			else if(splitDesc[i].contains("Birth"))
			{
				String yearstr = splitDesc[i].substring(splitDesc[i].lastIndexOf("Birth")+5).replace(":", "").trim();
				year = Integer.parseInt(yearstr);
				aadharNumber = splitDesc[i+2];
				name = splitDesc[i-1];
				
			}
			else if(splitDesc[i].contains("Male")){ 
				gender = "Male";
				break; 
			}
			else if(splitDesc[i].contains("Female")){ 
				gender = "Female";
				break;
			}
		}
		obj.setFirstName(name);
		obj.setGender(gender);
		obj.setAadharNumber(aadharNumber);
		obj.setYearOfBirth(year);
		obj.setDob(cal);
		return obj;
	}
	
	public static void main(String args[]){
		ParseAadharCard pac = new ParseAadharCard();
		//String content = "D\nFree at\nSRG THRYER\nGOVERNMENT OF INDIAWARE\n3iard Frter the\nAnkit Jagdish Koshti\nGFH af/ Year of Birth: 1989\nyEY / Male\n2532 5045 6220\nCTER THE HUTCHICT CITIGER\n";
		//String content = "GOVERNMENT OF INDIA\na la RF\nVijender Singh\nGTCH at 1 Year of Birth : 1988\nO\n3379 7203 6560 LEFK\nATEIR – ATH HIGHit FT SHfereHR\n";
		//String content = "GOVERNMENT OF Ner\nSukhrajdeep Sig\nHe / Year of Birth : 1987\ness\n2336 8826 0737 IIIIIIIII\nTEE - HTH 3ft FT Afar,\n";
		//String content = "TRGT HR GAR\nGOVERNMENT OF INDIA\na\nofAaNT ACRT RIG\nShreenivas Nilesh Shinde\nUH aira I DOB: 06/09/2012\n|yfceij / MALE\nD\n|\n99999999 0099 RE\n3TTER - FAITH ATURITT 3fereMIR\n";
		String content = "TH/Name\nSurprit Kaur\nart anêtes/DOB: 09-12-1989\nge Male\n10 1200\n1800 1200 1301\nHTIR - HIra AT HufeSIR\nA DHAR CARD MAKER PRANK\n";
		AadharCard obj = pac.parseAadhaeCard(content);
		System.out.println("Name : "+obj.getFirstName());
		System.out.println("YEAR OF BIRTH : "+obj.getYearOfBirth());
		System.out.println("Date of Birth : "+obj.getDob().getTime().toString());
		System.out.println("Gender : "+obj.getGender());
		System.out.println("Aadhar Number : "+obj.getAadharNumber());
	}

}
