package facade.exceptions;

public class ExpiredTokenException extends TokenException {

    /**
     * 
     */
    private static final long serialVersionUID = 8557778126059978946L;

    public ExpiredTokenException(String message) {
        super(message);
    }

    public ExpiredTokenException() {
    }
    
}
