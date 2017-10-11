package facade.exceptions;

import facade.exceptions.ApplicationException;

public class InvalidUsernameOrPasswordException extends ApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = 8557778126059978946L;
    
    public InvalidUsernameOrPasswordException(String message) {
        super(message);
    }

    public InvalidUsernameOrPasswordException(String message, Exception exception) {
        super(message, exception);
    }

}
