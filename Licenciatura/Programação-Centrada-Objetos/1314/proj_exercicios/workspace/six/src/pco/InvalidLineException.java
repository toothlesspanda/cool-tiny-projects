package pco;

public class InvalidLineException extends RuntimeException{

	public InvalidLineException(String message) {
		super(message);
	}
	public InvalidLineException(){
		super();
	}

}
