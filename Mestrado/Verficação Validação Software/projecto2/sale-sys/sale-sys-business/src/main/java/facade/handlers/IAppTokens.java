package facade.handlers;

import java.util.Map;

import javax.ejb.Local;

import facade.exceptions.ExpiredTokenException;
import facade.exceptions.InvalidTokenException;

/**
 * @author fmartins
 * @version 1.0 (16/02/2015)
 */
@Local
public interface IAppTokens {

    String createLoginToken(String username, String role);

    Map<String, Object> validateAndDecodeLoginToken(String token)
            throws ExpiredTokenException, InvalidTokenException;
}
