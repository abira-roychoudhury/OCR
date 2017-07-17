package apiClass;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

public class TimestampLogging {
	
	String logFilePath,logs;
	
	public TimestampLogging()
	{
		File logFile = new File("Log_File.txt");
		
		//File logFile = new File("Log_File_IE.txt");
		
		try {
			logFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		System.out.println("LogFile path :"+logFile.getAbsolutePath());
		logFilePath = logFile.getPath();
		logs = "";
	}
	
	public int fileLog(String functionName, Date start, Date end){
		int diff = (int) (end.getTime() - start.getTime());
		logs = logs +"\n"+ functionName +"    "+diff;
		return diff;
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
