package presentation.rest.customer;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerData implements Serializable {

    /**
     * Id for serialization 
     */
    private static final long serialVersionUID = -5679590895412845110L;

    private int vat;

    @NotNull
    private String denomination;

    private int phoneNumber;
    
    private int discountType;
    
    public CustomerData() {
    	// Needed by JAXB
    }

    public int getVAT() {
        return vat;
    }

    public String getDenomination() {
		return denomination;
	}
    
    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int discountType() {
        return discountType;
    }

}
