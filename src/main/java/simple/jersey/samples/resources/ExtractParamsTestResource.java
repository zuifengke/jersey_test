package simple.jersey.samples.resources;

import simple.jersey.samples.resources.util.DateParameter;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED;

@Path("/param-test")
public class ExtractParamsTestResource {
    @Context
    UriInfo uriInfo;

    @Context
    HttpHeaders httpHeaders;

    @GET
    @Path("{path-param}")
    public String testPathParam(@PathParam("path-param") String param) {
        System.out.println("传入param是：" + param);

        MultivaluedMap<String, String> params = uriInfo.getPathParameters();
        System.out.println("使用UriInfo得到：" + params.get("path-param"));

        MultivaluedMap<String, String> requestHeaders = httpHeaders.getRequestHeaders();

        Map<String,Cookie> cookies = httpHeaders.getCookies();

        return param;
    }

    @GET
    public String testQueryParam(
            @DefaultValue("anonymous") @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("location") List<String> location,
            @QueryParam("birthday") DateParameter birthday,
            @QueryParam("working-days") List<DateParameter> workingDays) {
        System.out.println("传入name是：" + name);
        System.out.println("传入age是：" + age);
        System.out.println("传入location是：" + location);
        System.out.println("传入birthday是：" +
                (birthday == null ? null : birthday.getDateValue()));
        System.out.println("传入working-days是：" + workingDays);

        MultivaluedMap<String, String> params = uriInfo.getQueryParameters();

        return name;
    }


    @POST
    @Consumes(APPLICATION_FORM_URLENCODED)
    public Response testFormParam(@Context UriInfo uriInfo,
            @DefaultValue("anonymous") @FormParam("name") String name,
            @FormParam("password") String password,
            MultivaluedMap<String, String> params) {
        System.out.println("传入name是：" + name);
        System.out.println("传入password是：" + password);

        System.out.println(params.get("name"));
        System.out.println(params.get("password"));

        if(!"anonymous".equals(name) && password != null) {
            URI uri = uriInfo.getBaseUriBuilder()
                        .path(ExtractParamsTestResource.class)
                        .path(ExtractParamsTestResource.class,"testPathParam")
                        .build(name);
            System.out.println(uri);
            return Response.created(uri).entity(name).build();
        } else {
            return Response.noContent().build();
        }
    }

}
