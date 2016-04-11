package simple.jersey.samples.resources.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

public class DateParameter {
    String dateStr;

    //1、有参数构造器
    public DateParameter(String dateStr) {
        System.out.println("有参数构造器");
        this.dateStr = dateStr;
    }

//    //2、静态工厂方法valueOf
//    public static DateParameter valueOf(String dateStr) {
//        System.out.println("静态工厂方法valueOf");
//        return new DateParameter(dateStr);
//    }

//    //3、静态工厂方法fromString
//    public static DateParameter fromString(String dateStr) {
//        System.out.println("静态工厂方法fromString");
//        return new DateParameter(dateStr);
//    }

    public Date getDateValue() {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            throw new WebApplicationException(
                    Response.status(BAD_REQUEST).entity(e.getMessage()).build());
        }
    }

    @Override
    public String toString() {
        return dateStr;
    }
}