package simple.jersey.samples.resources.no.matter.how.deep.it.is;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("root-resource")
public class SimpleRootResourceClass {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String doGet() {
        System.out.println("进入了SimpleRootResourceClass类的doGet方法。");
        return "无论package有多深，用@Path注释的类都是根。";
    }
}
