package presentation.rest.customer;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import facade.interfaces.ICustomer;

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerGet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7243549697187713023L;

    @XmlAttribute private int id;
    @XmlAttribute private String href;
    @SuppressWarnings("unused") private String designation;
    @SuppressWarnings("unused") private int vatNumber;

    public CustomerGet() {
        // Needed by JAXB
    }

    public CustomerGet(String href, ICustomer customer) {
        this.id = customer.getId();
        this.href = href;
        this.designation = customer.getDesignation();
        this.vatNumber = customer.getVATNumber();
    }
}
