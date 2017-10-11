package facade.exceptions;

public class InvalidTokenException extends TokenException {

    /**
     * 
     */
    private static final long serialVersionUID = 8557778126059978946L;

    public InvalidTokenException(String message, Exception e) {
        super(message, e);
    }

    public InvalidTokenException(Exception e) {
        super(e);
    }

    public InvalidTokenException() {
    }
    
}
