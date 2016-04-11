package simple.jersey.samples.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("path-test")
public class PathTestResource {
    @GET
    public Response simple() {
        System.out.println("进入了simple方法");
        return Response.noContent().build();
    }

    @GET
    @Path("{name:[a-z]*}")
    public Response doNameWithLowerCase(@PathParam("name") String name) {
        System.out.println("进入了doNameWithLowerCase方法");
        System.out.println("传入的名字是:" + name);
        return Response.ok().entity(name).build();
    }

    @GET
    @Path("{name:[a-zA-Z]*}")
    public Response doNameWithMultiCase(@PathParam("name") String name) {
        System.out.println("进入了doNameWithMultiCase方法");
        System.out.println("传入的名字是:" + name);
        return Response.ok().entity(name).build();
    }

    @GET
    @Path("{number:[0-9]*}")
    public Response doNumber(@PathParam("number") String number) {
        System.out.println("进入了doNumber方法");
        System.out.println("传入的数字是：" + number);
        return Response.ok().entity(number).build();
    }

    @GET
    @Path("{date:\\d{4}-\\d{2}-\\d{2}}")
    public Response doDate(@PathParam("date") String date) {
        System.out.println("进入了doDate方法");
        System.out.println("传入的日期是：" + date);
        return Response.ok().entity(date).build();
    }

    @GET
    @Path("{other}")
    public Response doOther(@PathParam("other") String other) {
        System.out.println("进入了doOther方法");
        System.out.println("传入的参数是：" + other);
        return Response.ok().entity(other).build();
    }

    @Context
    UriInfo uriInfo;
}
