package business;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;

import facade.exceptions.ExpiredTokenException;
import facade.exceptions.InvalidTokenException;
import facade.handlers.IAppTokens;

@Singleton
@Startup
public class Tokens implements IAppTokens {

    private static final String AUTH_TOKEN_SUB = "auth_token";

    // expires after 30 minutes
    private static final int LOGIN_EXPIRATION_LEASE = 60 * 30;
    private static final long MILLISECONDS = 1000L;
    
    private JWTSigner signer;
    private String password;
    private static JWTVerifier verifier;

    public Tokens() {
        password = "my big secret";
        signer = new JWTSigner(password);
        verifier = new JWTVerifier(password);
    }

    @Override
    public String createLoginToken(String username, String role) {
        long issuedAt = System.currentTimeMillis() / MILLISECONDS;
        long expiresAt = issuedAt + LOGIN_EXPIRATION_LEASE; 
        Map<String, Object> claims = new HashMap<>();
        claims.put("iss", "sale-sys:1.0");
        claims.put("sub", AUTH_TOKEN_SUB);
        claims.put("usr", username);
        claims.put("rol", role);
        claims.put("exp", expiresAt);
        claims.put("iat", issuedAt);
        return signer.sign(claims);
    }

    @Override
    public Map<String, Object> validateAndDecodeLoginToken(String token) 
            throws ExpiredTokenException, InvalidTokenException {
        Map<String, Object> payload;
        try {
            payload = verifier.verify(token);
            if (new Date().getTime()/MILLISECONDS > (int) payload.get("exp")) {
                throw new ExpiredTokenException();
            }
            if (!"sale-sys:1.0".equals(payload.get("iss")) ||
                    !AUTH_TOKEN_SUB.equals(payload.get("sub"))) {
                throw new InvalidTokenException();
            }
            return payload;
        } catch (Exception e) {
            throw new InvalidTokenException(e);
        }
    }

}
