package simple.jersey.samples.resources.mime;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

@Path("mime-test")
public class MIMETestResource {
    public static final File DIRECTORY = new File("C:\\images");

    @Produces("image/jpeg")
    @GET
    public DataSource getImageRep() {
        URL jpgURL = this.getClass().getResource("java.jpg");
        if(jpgURL == null) {
            throw new WebApplicationException(404);
        }
        return new FileDataSource(jpgURL.getFile());
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadImage(@FormDataParam("picture") FormDataContentDisposition pictureFormData,
                                @FormDataParam("picture") InputStream pictureData,
                                @Context UriInfo uriInfo) throws Exception{
        System.out.println(uriInfo.getAbsolutePath());

        if(!DIRECTORY.exists()) {
            DIRECTORY.mkdir();
        }
        String fileName = pictureFormData.getFileName();
        File jpgFile = new File(DIRECTORY, fileName);

        FileOutputStream fileOutputStream = new FileOutputStream(jpgFile);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(pictureData);

        byte[] buffer = new byte[1024];
        int count = -1;
        while ((count = bufferedInputStream.read(buffer)) != -1) {
            fileOutputStream.write(buffer, 0, count);
        }

        fileOutputStream.flush();
        fileOutputStream.close();
        bufferedInputStream.close();

        Runtime.getRuntime().exec("explorer /select," + DIRECTORY.getAbsolutePath() + "\\" + fileName);

        return Response.ok().build();
    }
}
