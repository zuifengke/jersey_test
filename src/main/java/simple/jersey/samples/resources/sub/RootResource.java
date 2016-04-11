package simple.jersey.samples.resources.sub;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

@Path("sub-resource-test")
public class RootResource {
    @GET
    public void subResourceMethod() {
        System.out.println("进入了子资源方法subResourceMethod");
        System.out.println(uriInfo == null ? "uriInfo为空" : "uriInfo不为空");
    }

    @Path("sub-resource-locator")
    public NotARootResource subResourceLocator() {
        System.out.println("进入了子资源定位器方法subResourceLocator");
        System.out.println(uriInfo == null ? "uriInfo为空" : "uriInfo不为空");
        return new NotARootResource();
    }

    @Context
    UriInfo uriInfo;

}
