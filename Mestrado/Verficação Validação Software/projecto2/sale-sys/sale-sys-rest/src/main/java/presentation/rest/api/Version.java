package presentation.rest.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Version {

    @XmlAttribute private int revision;
    @XmlAttribute private int build; 
    @XmlAttribute private int minor;
    @XmlAttribute private int major;

    public Version() {
    }

    public Version (int major, int minor, int revision, int build) {
        this.revision = revision;
        this.build = build;
        this.minor = minor;
        this.major = major;
    }
}
