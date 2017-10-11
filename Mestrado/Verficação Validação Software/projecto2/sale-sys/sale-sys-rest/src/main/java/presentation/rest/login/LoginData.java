package presentation.rest.login;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LoginData implements Serializable {

    /**
     * Id for serialization 
     */
    private static final long serialVersionUID = -5679590895412845110L;

    // logon info
    @NotNull
    private String credentials;

    public LoginData() {
    	// Needed by JAXB
    }

    public String getCredentials() {
        return credentials;
    }

}
