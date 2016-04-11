package simple.jersey.samples;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.core.header.MediaTypes;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.test.framework.JerseyTest;
import org.junit.Ignore;
import org.junit.Test;
import simple.jersey.samples.model.AircraftType;
import simple.jersey.samples.model.ContactInfo;
import simple.jersey.samples.model.Flights;

import javax.activation.FileDataSource;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.File;
import java.net.URL;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.junit.matchers.JUnitMatchers.hasItems;

public class MainTest extends JerseyTest {

    public MainTest() throws Exception {
        super("simple.jersey.samples");
    }

    public WebResource resource() {
        return Client.create().resource(UriBuilder.fromUri("http://localhost").port(getPort(9998)).build());
    }

    /**
     * Test to see that the message "Hello World" is sent in the response.
     */
    @Test
    public void testHelloWorld() {
        WebResource webResource = resource();
        System.out.println(webResource.getURI());

        webResource = webResource.path("helloworld");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertEquals("Hello World", responseMsg);
    }

    @Test
    public void testRootResource() {
        WebResource webResource  = resource().path("root-resource");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertEquals("无论package有多深，用@Path注释的类都是根。", responseMsg);
    }

    @Test
    public void testSubResourceMethod() {
        WebResource webResource  = resource().path("sub-resource-test");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void testSubResourceLocator() {
        WebResource webResource  = resource().path("sub-resource-test").path("sub-resource-locator");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertEquals("Not a root resource.", responseMsg);
    }

    @Test
    public void testPathSimple() {
        WebResource webResource = resource().path("path-test");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.NO_CONTENT.getStatusCode()));
    }

    @Test
    public void testPathDoNameWithLowerCase() {
        WebResource webResource = resource().path("path-test").path("jack");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(String.class), is("jack"));
    }

    @Test
    public void testPathDoNameWithMultiCase() {
        WebResource webResource = resource().path("path-test").path("Jack");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(String.class), is("Jack"));
    }

    @Test
    public void testPathDoNumber() {
        WebResource webResource = resource().path("path-test").path("123");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(String.class), is("123"));
    }

    @Test
    public void testPathDoDate() {
        WebResource webResource = resource().path("path-test").path("2013-01-31");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(String.class), is("2013-01-31"));
    }

    @Test
    public void testPathDoOther() {
        WebResource webResource = resource().path("path-test").path("什么情况");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(response.getEntity(String.class), is("什么情况"));
    }

    @Test
    public void testPathParam() {
        WebResource webResource = resource().path("/param-test/").path("/Jack/");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertThat(responseMsg, is("Jack"));
    }

    @Test
    public void testQueryParamWithTheWholeParam() {
        WebResource webResource = resource().path("param-test")
                .queryParam("name", "Rose")
                .queryParam("age", "26")
                .queryParam("location", "北京")
                .queryParam("location", "上海")
                .queryParam("birthday", "1988-01-01")
                .queryParam("working-days", "2013-01-21")
                .queryParam("working-days", "2013-01-22")
                .queryParam("working-days", "2013-01-23");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertThat(responseMsg, is("Rose"));
    }

    @Test
    public void testQueryParamWithEmptyParam() {
        WebResource webResource = resource().path("param-test");
        System.out.println(webResource.getURI());

        String responseMsg = webResource.get(String.class);
        assertThat(responseMsg, is("anonymous"));
    }

    @Test
    public void testFormParam() {
        WebResource webResource = resource().path("param-test");
        System.out.println(webResource.getURI());

        Form formData = new Form();
        formData.add("name", "John");
        formData.add("password", "26");

        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED_TYPE).post(ClientResponse.class, formData);
        assertThat(response.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(response.getEntity(String.class), is("John"));
        assertThat(response.getHeaders().get("Location"), hasItems(containsString("http://localhost:9998/param-test/John")));
    }

    @Test
    public void testHTTPMethodGet() {
        WebResource webResource = resource().path("http-method");
        System.out.println(webResource.getURI());

        GenericType<List<ContactInfo>> genericType =
                new GenericType<List<ContactInfo>>() {};

        //得到JSON格式的表现（representation）
        List<ContactInfo> contactInfos = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        //检查有没有两个初始的contactInfos类型的对象
        assertEquals("预期的contactInfos的初始值没有得到",
                2, contactInfos.size());
    }

    @Test
    public void testHTTPMethodGetByName() {
        WebResource webResource = resource().path("http-method").path("Jack");
        System.out.println(webResource.getURI());

        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        ContactInfo contactInfo = clientResponse.getEntity(ContactInfo.class);

        assertThat(clientResponse.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(contactInfo.name, is("Jack"));
        assertThat(contactInfo.phoneNo, is("12345"));
    }

    @Test
    public void testHTTPMethodPost() {
        WebResource webResource = resource().path("http-method");
        System.out.println(webResource.getURI());

        ContactInfo contactInfo = new ContactInfo("John", "5678");
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, contactInfo);

        assertThat(clientResponse.getStatus(), is(Response.Status.CREATED.getStatusCode()));
        assertThat(clientResponse.getHeaders().get("Location"), hasItems(containsString("http://localhost:9998/http-method/John")));

        GenericType<List<ContactInfo>> genericType =
                new GenericType<List<ContactInfo>>() {};

        //得到JSON格式的表现（representation）
        List<ContactInfo> contactInfos = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        //检查有没有两个初始的contactInfos类型的对象
        assertEquals("预期的contactInfos的添加后的值没有得到",
                3, contactInfos.size());
    }

    @Test
    public void testHTTPMethodPut() {
        WebResource webResource = resource().path("http-method");
        System.out.println(webResource.getURI());

        ContactInfo contactInfo = new ContactInfo("Josh", "56789");
        ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_JSON)
                .put(ClientResponse.class, contactInfo);

        assertThat(clientResponse.getStatus(), is(Response.Status.OK.getStatusCode()));

        GenericType<List<ContactInfo>> genericType =
                new GenericType<List<ContactInfo>>() {};

        //得到JSON格式的表现（representation）
        List<ContactInfo> contactInfos = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        assertThat(contactInfos.get(1).phoneNo, is("56789"));

    }

    @Test
    public void testHTTPMethodDelete() {
        WebResource webResource = resource().path("http-method");
        System.out.println(webResource.getURI());

        ClientResponse clientResponse = webResource.delete(ClientResponse.class);

        assertThat(clientResponse.getStatus(), is(Response.Status.OK.getStatusCode()));

        GenericType<List<ContactInfo>> genericType =
                new GenericType<List<ContactInfo>>() {};

        //得到JSON格式的表现（representation）
        List<ContactInfo> contactInfos = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        assertThat(contactInfos.size(), is(0));
    }

    @Test
    public void testHTTPMethodDeleteByName() {
        WebResource webResource = resource().path("http-method");
        System.out.println(webResource.getURI());

        ClientResponse clientResponse = webResource.path("Jack").delete(ClientResponse.class);

        assertThat(clientResponse.getStatus(), is(Response.Status.OK.getStatusCode()));

        GenericType<List<ContactInfo>> genericType =
                new GenericType<List<ContactInfo>>() {};

        //得到JSON格式的表现（representation）
        List<ContactInfo> contactInfos = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        assertThat(contactInfos.size(), is(1));
        assertThat(contactInfos.get(0).name, not("Jack"));
    }

    @Test
    public void testMIMEJPEGPicture() {
        WebResource webResource = resource().path("mime-test");
        System.out.println(webResource.getURI());

        ClientResponse response = webResource.get(ClientResponse.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void testUploadMIMEJPEGPicture() throws Exception{

        WebResource webResource = resource().path("mime-test");
        System.out.println(webResource.getURI());

        FormDataMultiPart mp = new FormDataMultiPart();
        FormDataBodyPart p = new FormDataBodyPart(FormDataContentDisposition.name("picture").fileName("dog.jpg").build(),
                new FileDataSource(this.getClass().getResource("dog.jpg").getFile()), MediaType.valueOf("image/jpeg"));
        mp.bodyPart(p);

        ClientResponse response = webResource.type(MediaType.MULTIPART_FORM_DATA_TYPE).post(ClientResponse.class, mp);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void testGetOnAircraftsJSONFormat() {
        WebResource webResource = resource().path("aircrafts");
        System.out.println(webResource.getURI());

        GenericType<List<AircraftType>> genericType =
                new GenericType<List<AircraftType>>() {};

        //得到JSON格式的表现（representation）
        List<AircraftType> aircraftTypes = webResource.
                accept(MediaType.APPLICATION_JSON).get(genericType);
        //检查有没有两个初始的aircraft类型的对象
        assertEquals("预期的aircraft的初始值没有得到",
                2, aircraftTypes.size());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_JSON).get(String.class));
    }

    @Test
    public void testGetOnAircraftsXMLFormat() {
        WebResource webResource = resource().path("aircrafts");
        System.out.println(webResource.getURI());

        GenericType<List<AircraftType>> genericType =
                new GenericType<List<AircraftType>>() {};

        //得到XML格式的表现（representation）
        List<AircraftType> aircraftTypes = webResource.
                accept(MediaType.APPLICATION_XML).get(genericType);
        //检查有没有两个初始的aircraft类型的对象
        assertEquals("预期的aircraft的初始值没有得到",
                2, aircraftTypes.size());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_XML).get(String.class));
    }

    @Test
    public void testGetOnFlightsJSONFormat() {
        WebResource webResource = resource().path("flights");
        System.out.println(webResource.getURI());

        //得到JSON格式的表现（representation）
        Flights flights = webResource.
                accept(MediaType.APPLICATION_JSON).get(Flights.class);
        //检查有没有两个初始的flight类型的对象
        assertEquals("预期的初始值没有得到",
                2, flights.getFlight().size());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_JSON).get(String.class));
    }

    @Test
    public void testPutOnFlightsJSONFormat() {
        WebResource webResource = resource().path("flights");
        System.out.println(webResource.getURI());

        //得到JSON格式的表现（representation
        Flights flights = webResource.
                accept(MediaType.APPLICATION_JSON).get(Flights.class);
        //检查有没有两个初始的flight类型的对象
        assertEquals("预期的初始值没有得到",
                2, flights.getFlight().size());

        //删掉第二个flight对象
        if (flights.getFlight().size() > 1) {
            flights.getFlight().remove(1);
        }

        //更新第一个对象
        flights.getFlight().get(0).setNumber(125);
        flights.getFlight().get(0).setFlightId("OK125");

        //将更新结果返回到服务器
        webResource.type(MediaType.APPLICATION_JSON).put(flights);

        //得到服务器端的更新列表:
        Flights updatedFlights = webResource.
                accept(MediaType.APPLICATION_JSON).get(Flights.class);
        //检查是不是有且只有一个记录
        assertEquals("剩余的记录数跟预期的不一样",
                1, updatedFlights.getFlight().size());
        // check that the flight entry in retrieved list has FlightID OK!@%
        assertEquals("得到的flight ID跟预期的不一样",
                "OK125", updatedFlights.getFlight().get(0).getFlightId());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_JSON).get(String.class));
    }

    @Test
    public void testGetOnFlightsXMLFormat() {
        WebResource webResource = resource().path("flights");
        System.out.println(webResource.getURI());

        //得到XML格式的表现（representation）
        Flights flights = webResource.
                accept(MediaType.APPLICATION_XML).get(Flights.class);
        //检查有没有两个初始的flight类型的对象
        assertEquals("预期的初始值没有得到",
                2, flights.getFlight().size());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_XML).get(String.class));
    }

    @Test
    public void testPutOnFlightsXMLFormat() {
        WebResource webResource = resource().path("flights");
        System.out.println(webResource.getURI());

        //得到XML格式的表现（representation
        Flights flights = webResource.
                accept(MediaType.APPLICATION_XML).get(Flights.class);
        //检查有没有两个初始的flight类型的对象
        assertEquals("预期的初始值没有得到",
                2, flights.getFlight().size());

        //删掉第二个flight对象
        if (flights.getFlight().size() > 1) {
            flights.getFlight().remove(1);
        }

        //更新第一个对象
        flights.getFlight().get(0).setNumber(125);
        flights.getFlight().get(0).setFlightId("OK125");

        //将更新结果返回到服务器
        webResource.type(MediaType.APPLICATION_XML).put(flights);

        //得到服务器端的更新列表:
        Flights updatedFlights = webResource.
                accept(MediaType.APPLICATION_XML).get(Flights.class);
        //检查是不是有且只有一个记录
        assertEquals("剩余的记录数跟预期的不一样",
                1, updatedFlights.getFlight().size());
        // check that the flight entry in retrieved list has FlightID OK!@%
        assertEquals("得到的flight ID跟预期的不一样",
                "OK125", updatedFlights.getFlight().get(0).getFlightId());

        System.out.println(webResource.
                accept(MediaType.APPLICATION_XML).get(String.class));
    }

    @Test
    public void testApplicationWadl() {
        WebResource webResource = resource();
        String serviceWadl = webResource.path("application.wadl").
                accept(MediaTypes.WADL).get(String.class);

        assertTrue(serviceWadl.length() > 0);
    }
}
