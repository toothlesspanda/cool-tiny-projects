package facade.tos;

import business.Role;

public interface IUserData {

    /**
     * @return The username
     */
    String getUsername();

    /**
     * @return The user's first name
     */
    String getFirstName();

    /**
     * @return The user's last name
     */
    String getLastName();

    Role getRole();

    void setRole(Role role);

}
