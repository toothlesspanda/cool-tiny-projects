package facade.tos;

import business.Role;
import facade.exceptions.InvalidUsernameOrPasswordException;

/**
 * A user
 *
 * @author fmartins
 * @since 1.0 (12/12/2014)
 * 
 */
public interface IUser {
    String getRoleDescription();
    String getFirstName();
    String getLastName();
    String getUsername();
    void disable();
    void checkPassword(String password) throws InvalidUsernameOrPasswordException;
    void changePassword(String oldPassword, String newPassword) throws InvalidUsernameOrPasswordException;
    boolean hasRole(Role role);
    boolean isEnabled();
}
