package simple.jersey.samples.model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContactInfo {
    @JsonProperty
    public String name;
    @JsonProperty
    public String phoneNo;

    public ContactInfo() {
    }

    public ContactInfo(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
    }
}
