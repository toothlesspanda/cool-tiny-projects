package facade.exceptions;

public class TokenException extends ApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = 8557778126059978946L;

    public TokenException(String message, Exception e) {
        super(message, e);
    }

    public TokenException(Exception e) {
        super("", e);
    }

    public TokenException() {
    	super("");
    }

    public TokenException(String message) {
        super(message);
    }
    
}
