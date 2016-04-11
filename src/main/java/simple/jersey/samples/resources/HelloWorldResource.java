package simple.jersey.samples.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

//本Java类将会匹配的URI路径是"/helloworld"
@Path("/helloworld")
public class HelloWorldResource {

    //这个方法将会处理HTTP GET请求
    @GET
    //这个方法将会返回指定的MIME Media类型的内容
    //这里的MIME类型是"text/plain"
    @Produces(MediaType.TEXT_PLAIN)
    public String getClichedMessage() {
        System.out.println(uriInfo.getAbsolutePath());
        System.out.println(uriInfo.getBaseUri());
        System.out.println(uriInfo.getPath());
        return "Hello World";
    }

    @Context
    UriInfo uriInfo;
}