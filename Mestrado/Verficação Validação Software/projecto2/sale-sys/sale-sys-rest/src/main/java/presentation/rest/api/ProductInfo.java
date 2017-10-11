package presentation.rest.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductInfo {

    @SuppressWarnings("unused") private String name; 
    @SuppressWarnings("unused") private String vendor; 
    @XmlElement private Version version;

    public ProductInfo() {
    }

    public ProductInfo (String name, String vendor, Version version) {
        this.name = name;
        this.vendor = vendor;
        this.version = version;
    }
}
