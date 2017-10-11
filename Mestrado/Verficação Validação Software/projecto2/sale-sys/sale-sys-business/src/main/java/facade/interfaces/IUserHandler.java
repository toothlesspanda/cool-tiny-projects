package facade.interfaces;

import javax.ejb.Local;

import facade.exceptions.InvalidUsernameOrPasswordException;
import facade.tos.IUser;

/**
 * @author fmartins
 * @version 1.0 (31/12/2014)
 */
@Local
public interface IUserHandler {

    IUser login(String username, String password) throws InvalidUsernameOrPasswordException;

}
