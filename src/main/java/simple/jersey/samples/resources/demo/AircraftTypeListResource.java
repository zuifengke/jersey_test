package simple.jersey.samples.resources.demo;

import simple.jersey.samples.model.AircraftType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.LinkedList;
import java.util.List;

@Path("/aircrafts")
public class AircraftTypeListResource {
    static final List<AircraftType> aircraftTypes = new LinkedList<>();

    static {
        aircraftTypes.add(new AircraftType("B737", 42.1, 204));
        aircraftTypes.add(new AircraftType("A330", 58.8, 253));
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<AircraftType> getAircraftTypes() {
        System.out.println("进入getAircraftTypes方法");
        return aircraftTypes;
    }
}
