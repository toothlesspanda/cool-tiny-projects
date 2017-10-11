package business;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import facade.exceptions.InvalidUsernameOrPasswordException;

/**
 * Session Bean implementation class CatalogueGardens
 *
 */
@Stateless
public class UsersCatalog {

    @PersistenceContext
    private EntityManager em;

    public UsersCatalog() {
    	// needed by EJB
    }

    public User login(String username, String password) throws InvalidUsernameOrPasswordException {
        if (existsUsername(username)) {
            User user = getUserByUsername(username);
            user.checkPassword(password);
            return user;
        } else {
            throw new InvalidUsernameOrPasswordException("Username " + username + " or password does not exist.");
        }
    }

    public User getUserByUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_USERNAME, User.class);
        query.setParameter(1, username);
        return query.getSingleResult();
    }

    public boolean existsUsername(String username) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_USERNAME, User.class);
        query.setParameter(1, username);
        return !query.getResultList().isEmpty();
    }
}