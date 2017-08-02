package customExceptions;

/*
 * Exception that is generated when the QR Code does not contain the entire information
 * */
public class BarCodeException extends Exception  {
	
	public BarCodeException(){
		super("Bar Code Found");
	}

}
