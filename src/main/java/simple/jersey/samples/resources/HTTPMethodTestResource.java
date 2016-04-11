package simple.jersey.samples.resources;

import simple.jersey.samples.model.ContactInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Path("http-method")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HTTPMethodTestResource {
    static final List<ContactInfo> contactInfos = new ArrayList<>();

    static {
        contactInfos.add(new ContactInfo("Jack", "12345"));
        contactInfos.add(new ContactInfo("Josh", "12346"));
    }

    @GET
    public List<ContactInfo> doGet() {
        System.out.println("进入了doGet方法");
        return contactInfos;
    }

    @POST
    public Response doPost(@Context UriInfo uriInfo,ContactInfo contactInfo) {
        System.out.println("进入了doPost方法");
        if(saveContactInfo(contactInfo)) {
            URI newLocation = uriInfo.getRequestUriBuilder()
                    .path(contactInfo.name).build();
            System.out.println(newLocation);
            return Response.created(newLocation).entity(contactInfo).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PUT
    public Response doPut(ContactInfo contactInfo) {
        System.out.println("进入了doPut方法");
        if(updateContackInfo(contactInfo)) {
            return Response.ok().build();
        }
        return Response.noContent().build();
    }

    @DELETE
    public Response doDelete() {
        System.out.println("进入了doDelete方法");
        clearContackInfos();
        return Response.ok().build();
    }

    @GET
    @Path("{name}")
    public ContactInfo doGetByName(@PathParam("name") String name) {
        System.out.println("进入了doGetByName方法");

        if(name == null || "".equals(name)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("名字不能为空！").build());
        }

        ContactInfo contactInfo = getByName(name);
        if(contactInfo == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return contactInfo;
    }

    @DELETE
    @Path("{name}")
    public Response doDeleteByName(@PathParam("name") String name) {
        System.out.println("进入了doDeleteByName方法");

        if(name == null || "".equals(name)) {
            throw new WebApplicationException(
                    Response.status(Response.Status.BAD_REQUEST)
                            .entity("名字不能为空！").build());
        }

        if(deleteByName(name)) {
            return Response.ok().build();
        } else {
            return Response.noContent().build();
        }
    }

    private ContactInfo getByName(String name) {
        for(int i = 0; i < contactInfos.size(); i++) {
            if(name.equals(contactInfos.get(i).name)) {
                return contactInfos.get(i);
            }
        }
        return null;
    }

    private boolean deleteByName(String name) {
        for(int i = 0; i < contactInfos.size(); i++) {
            if(name.equals(contactInfos.get(i).name)) {
                contactInfos.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean saveContactInfo(ContactInfo contactInfo) {
        contactInfos.add(contactInfo);
        return true;
    }

    private boolean updateContackInfo(ContactInfo contactInfo) {
        for(int i = 0; i < contactInfos.size(); i++) {
            if(contactInfo.name.equals(contactInfos.get(i).name)) {
                contactInfos.get(i).phoneNo = contactInfo.phoneNo;
                return true;
            }
        }
        return false;
    }

    private boolean clearContackInfos() {
        for(int i = contactInfos.size() - 1; i >= 0; i--) {
            contactInfos.remove(i);
        }
        return true;
    }
}
