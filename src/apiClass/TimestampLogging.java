package apiClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class TimestampLogging {
	
	String logFilePath,logs;
	
	public TimestampLogging()
	{
		logFilePath = "D:\\OCRDemo\\Server_disk\\Log_File.txt";
		logs = "";
	}
	
	public void fileLog(String functionName, Date start, Date end){
		int diff = (int) (end.getTime() - start.getTime());
		logs = logs +"\n"+ functionName +"    "+diff;
	}
	
	public void fileDesc(String fileName, String fileType){
		logs = logs + "\n\n"+fileName+"     "+fileType;
		
	}
	
    public void fileWrite(){
    	try {
		    Files.write(Paths.get(logFilePath), logs.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
