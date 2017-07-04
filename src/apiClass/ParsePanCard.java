package apiClass;

import apiClass.EntityType;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import templates.PanCard;

public class ParsePanCard {
	
	public PanCard parsePanCard(String content){
		PanCard obj = new PanCard();
		String splitDesc[] = content.split("\\n");
		int i;
		for(i = 0;i<splitDesc.length;i++){
			if(splitDesc[i].indexOf("INDIA")>-1)
			  break;
		}		
		String name,fname,dob,pan;
		
		//extracting name
		name=splitDesc[++i];
		if(EntityType.getType(name).equals("Non"))
			name=splitDesc[++i]; 
		
		//extracting father's name
		fname=splitDesc[++i];
		if(EntityType.getType(fname).equals("Non"))
			fname=splitDesc[++i];
		
		//extracting dob
		dob=splitDesc[++i];
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar cal = Calendar.getInstance();
		try{
			cal.setTime(sdf.parse(dob));
			//System.out.println("date:"+cal.getTime().toString());
		}catch(Exception e){
			System.err.println(e);	}	
		
		//extracting pan
		pan=splitDesc[i+2];
		
		//setting the PanCard object
		obj.setName(name);
		obj.setFatherName(fname);
		obj.setDob(cal);
		obj.setPanNumber(pan);
		
		return obj;
	}
	
	public static void main(String args[]){
		ParsePanCard ppc = new ParsePanCard();
		//String content = "rear farHT\nINCOME TAX DEPARTMENT\nI\nHRG THREATE\nGOVTOF INDIA\nMANOJ KUMARYADAV\nBEER SINGH YADAV\n25/02/1981\nPermanent Account Number\nADZPY0209R\nMany leader\nSignature\n";
		String content = "area farHPT\nHRH THRER\nA INCOME TAX DEPARTMENT\nA GOVTOF INDIA\nKOSHTIANKIT\nJAGDISH KOSHTI\n11/12/1989\nPermanent Account Number\nBYVPK0200Q\nSignature\n";
		//String content = "SIST farHPST HURT (Raar\nINCOME TAX DEPARTMENT AS GOVTOF INDIA\nADITYA MEHENDALE\nPRAKASH MEHENDALE\n02/06/1976\nPerimianent Account Number\nBODPM4264E\nSignature SALES\n";
		//String content = "Such CHPTHIRG HEREIR\nINCOME TAX DEPARTMENT GOVTOF INDIA\nPRAMOD KUMAR MAHTO\nTO\nVASUDEV MAHTO\n03/04/1982\nPermanent Account Number\nANRPM2537J\nO\nSignature\n";
		PanCard obj = ppc.parsePanCard(content);
		
		System.out.println("Name "+obj.getName());
		System.out.println("Father's Name "+obj.getFatherName());
		System.out.println("DOB "+obj.getDob().getTime().toString());
		System.out.println("PanNumber "+obj.getPanNumber());
	}
	
}


