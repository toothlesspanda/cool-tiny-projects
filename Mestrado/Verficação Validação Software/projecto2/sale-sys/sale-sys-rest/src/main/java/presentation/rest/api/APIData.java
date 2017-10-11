package presentation.rest.api;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import presentation.rest.Link;

@XmlRootElement(name="api")
@XmlAccessorType(XmlAccessType.FIELD)
public class APIData {

    @XmlElement(name = "link")
    @XmlElementWrapper private List<Link> links;
    @SuppressWarnings("unused") private ProductInfo productInfo;

    public APIData() {
    }

    public APIData (ProductInfo productInfo, List<Link> links) {
        this.productInfo = productInfo;
        this.links = links;
    }
}
