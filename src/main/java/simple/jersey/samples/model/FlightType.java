package simple.jersey.samples.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Formatter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "flightType", propOrder = {
        "flightId",
        "company",
        "number",
        "aircraft"
})
public class FlightType {

    @XmlElement(required = true)
    protected String company;
    protected long number;
    @XmlElement(required = true)
    protected String aircraft;
    @XmlElement(required = true)
    protected String flightId;

    public String getCompany() {
        return company;
    }

    public void setCompany(String value) {
        this.company = value;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long value) {
        this.number = value;
    }

    public String getAircraft() {
        return aircraft;
    }

    public void setAircraft(String value) {
        this.aircraft = value;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String value) {
        this.flightId = value;
    }

    @Override
    public String toString() {
        return (new Formatter()).format(
                "{\"flightId\":\"%s\",\"company\":\"%s\",\"number\":%d,\"aircraft\":\"%s\"}",
                this.flightId, this.company, this.number, this.aircraft).toString();
    }

}
