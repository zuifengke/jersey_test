package simple.jersey.samples.resources.sub;

import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class NotARootResource {
    @GET
    public String doGet() {
        System.out.println("进入了NotARootResource类的doGet方法");
        System.out.println(uriInfo == null ? "uriInfo为空" : "uriInfo不为空");
        return "Not a root resource.";
    }

    @Context
    UriInfo uriInfo;
}
