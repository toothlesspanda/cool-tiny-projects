package business;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Version;

import facade.exceptions.InvalidUsernameOrPasswordException;
import facade.tos.IUser;

/**
 * A user
 *
 * @author fmartins
 * @since 1.0 (11/8/2014)
 * 
 */
@Entity 
@NamedQueries({
	@NamedQuery(name = User.FIND_ALL, query="Select u from User u"),
	@NamedQuery(name = User.FIND_BY_USERNAME, query="Select u from User u where u.username = ?1")
})
public class User implements IUser {

	public static final String FIND_ALL = "User.findAll";
	public static final String FIND_BY_USERNAME = "User.findByUsername";
	private static final String ENCODE_ALGORITHM = "SHA-1";

	/**
	 * User name. This name is used for the authentication 
	 * login by the user. It should equals to the e-mail address of the user.
	 * This attribute is never sent to the user and is equals to the e-email
	 * as long as the user is enabled. When the user is disabled, this 
	 * attribute is field with the id and the user can never login again 
	 * with it.
	 */
	@Column(unique = true)
	@Id private String username;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;


	/**
	 * The password is never returned by the user.
	 * It is checked by the security context from the application server.
	 * The password is hashed according to the authentication mechanism used
	 * in at the web level. Currently we support BASIC and DIGEST. See the
	 * user's catalog for more information how to assemble the password hash.
	 */
	@Column(nullable = false)
	private String password;

	/**
	 * The group of the user 
	 */
	@Enumerated(EnumType.STRING)
	private Role role;

	/**
	 * Optimistic locking
	 */
	@Version
	private Timestamp version;

	/**
	 * If the user is enabled. A disabled user cannot login no more.
	 */
	private boolean enabled;


	/**
	 * Needed by JPA 
	 */
	User() {
	}

	public User(String username, String password, String firstName, String lastName,
			Role groupRole) {
		this.username = username;
		this.password = encode(ENCODE_ALGORITHM, password);
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = groupRole;
		this.enabled = true;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public String getLastName() {
		return lastName;
	}

	public String getUsername() {
		return username;
	}

	public String getRoleDescription() {
		return role.toString();
	}

	public void changePassword(String oldPassword, String newPassword) throws InvalidUsernameOrPasswordException {
		checkPassword(oldPassword);
		password = encode(ENCODE_ALGORITHM, newPassword);
	}	

	public void checkPassword(String password)
			throws InvalidUsernameOrPasswordException {

		password = encode(ENCODE_ALGORITHM, password);

		if (!this.password.equals(password))
			throw new InvalidUsernameOrPasswordException("Invalid username or password");
	}


	private String encode(String algorithm, String password) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			md.update(password.getBytes("UTF-8"));
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			return null;
		}
	}

	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void disable() {
		enabled = false;
	}

	@Override
	public boolean hasRole(Role role) {
		return role == this.role;
	}
	
	public static void main(String[] args) {
		User u = new User("fmartins", "123", "Francisco", "Martins",
    			Role.ADMIN);
		System.out.println(u.password);
	}

}
