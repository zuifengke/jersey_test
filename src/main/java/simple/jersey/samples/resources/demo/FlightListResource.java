package simple.jersey.samples.resources.demo;

import com.sun.jersey.spi.resource.Singleton;
import simple.jersey.samples.model.FlightType;
import simple.jersey.samples.model.Flights;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Singleton
@Path(value = "/flights")
public class FlightListResource {
    private Flights myFlights;

    public FlightListResource() {
        myFlights = new Flights();
        FlightType flight123 = new FlightType();
        flight123.setCompany("中国航空");
        flight123.setNumber(123);
        flight123.setFlightId("OK123");
        flight123.setAircraft("B737");
        FlightType flight124 = new FlightType();
        flight124.setCompany("中国航空");
        flight124.setNumber(124);
        flight124.setFlightId("OK124");
        flight124.setAircraft("AB115");
        myFlights.getFlight().add(flight123);
        myFlights.getFlight().add(flight124);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public synchronized Flights getFlightList() {
        System.out.println("进入getFlightList方法");
        return myFlights;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public synchronized void putFlightList(Flights flights) {
        System.out.println("进入putFlightList方法");
        myFlights = flights;
    }
}
