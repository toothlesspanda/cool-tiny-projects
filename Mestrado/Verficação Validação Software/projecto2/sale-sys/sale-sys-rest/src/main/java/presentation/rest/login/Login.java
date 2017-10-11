package presentation.rest.login;

import java.util.Base64;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import facade.exceptions.InvalidUsernameOrPasswordException;
import facade.handlers.IAppTokens;
import facade.interfaces.IUserHandler;
import facade.tos.IUser;

@Path(Login.HREF)
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Stateless
public class Login {

    public static final String REL = "login";
    public static final String HREF = "v1/login";

    @EJB private IAppTokens tokens;
    @EJB private IUserHandler userHandler;
    private String username;
    private String password;

    @POST
    // any user can login!
    public Response login(@Valid LoginData loginData) throws InvalidUsernameOrPasswordException {
        decodeUsernameAndPassword(loginData.getCredentials());
    
        IUser user = userHandler.login(username, password);
        return Response.ok().entity(
        		Json.createObjectBuilder().
        			add("token", tokens.createLoginToken(user.getUsername(), user.getRoleDescription())).build().toString()
                ).build();
    }

    private void decodeUsernameAndPassword(String base64Credentials) throws InvalidUsernameOrPasswordException {
        try {
            String credentials = new String(Base64.getDecoder().decode(base64Credentials.getBytes("UTF-8")));
     
            // credentials = username:password
            final String[] values = credentials.split(":",2);
            username = values[0];
            password = values[1];
        } catch (Exception e) { 
            throw new InvalidUsernameOrPasswordException("", e); 
        }
    }
}
