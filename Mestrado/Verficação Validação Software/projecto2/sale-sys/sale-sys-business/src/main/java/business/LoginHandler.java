package business;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import facade.exceptions.InvalidUsernameOrPasswordException;
import facade.handlers.IAppTokens;
import facade.interfaces.IUserHandler;
import facade.tos.IUser;

/**
 * Session Bean implementation class CustomerHandler
 */
@Stateless
public class LoginHandler implements IUserHandler {

    @EJB private UsersCatalog usersCatalog;
    @EJB private IAppTokens tokens;

    public LoginHandler() {
    	// needed by EJB
    }

    @Override
    public IUser login(String username, String password) throws InvalidUsernameOrPasswordException {
        return usersCatalog.login(username, password);
    }
}