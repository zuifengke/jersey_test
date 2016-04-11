package simple.jersey.samples.config;

import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static org.codehaus.jackson.map.AnnotationIntrospector.Pair;

@Provider
public class ObjectMapperResolver implements ContextResolver<ObjectMapper> {
    final ObjectMapper defaultObjectMapper;
    final ObjectMapper combinedObjectMapper;

    public ObjectMapperResolver() {
        defaultObjectMapper = createDefaultMapper();
        combinedObjectMapper = createCombinedObjectMapper();
    }

    @Override
    public ObjectMapper getContext(Class<?> aClass) {
        if(aClass.isAnnotationPresent(XmlRootElement.class) || aClass.isAnnotationPresent(XmlType.class)) {
            return combinedObjectMapper;
        } else {
            return defaultObjectMapper;
        }
    }

    private static ObjectMapper createDefaultMapper() {
        ObjectMapper result = new ObjectMapper();
        result.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);

        return result;
    }

    private static ObjectMapper createCombinedObjectMapper() {
        Pair combinedIntrospector = createJaxbJacksonAnnotationIntrospector();
        ObjectMapper result = new ObjectMapper();
        result.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, true);
        result.setDeserializationConfig(result.getDeserializationConfig().withAnnotationIntrospector(combinedIntrospector));
        result.setSerializationConfig(result.getSerializationConfig().withAnnotationIntrospector(combinedIntrospector));

        return result;
    }

    private static Pair createJaxbJacksonAnnotationIntrospector() {

        AnnotationIntrospector jaxbIntrospector = new JaxbAnnotationIntrospector();
        AnnotationIntrospector jacksonIntrospector = new JacksonAnnotationIntrospector();

        return new Pair(jacksonIntrospector, jaxbIntrospector);
    }
}
