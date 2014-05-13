package things.thing;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
import things.exceptions.TypeRuntimeException;
import things.utils.json.ThingsObjectMapper;

import javax.inject.Singleton;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Map;
import java.util.Set;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 23/04/14
 * Time: 10:22 AM
 */
@Component
@Singleton
public class ThingUtils {

    private ObjectMapper objectMapper;

    private ImmutableMap<String, JsonSchema> schemaMap = null;
    private ImmutableMap<String, Map<String, String>> typePropertiesMap = null;
    
    private ThingsObjectMapper tom;
    private ThingControl tc;

    public ThingUtils() {
    }

//    @Inject
    public void setThingControl(ThingControl tc) {
        this.tc = tc;
        tom = new ThingsObjectMapper(this.tc);
    }

    /**
     * Returns a list of all types that where registered on startup (by being annotated
     * with the {@link things.model.types.Value} annotation).
     *
     * @return the list of types
     */
    public static Set<String> getAllRegisteredTypes() {
        return TypeRegistry.getTypeMap().keySet();
    }


    public Map<String, JsonSchema> getRegisteredTypeSchemata() {

        if (schemaMap == null) {
            Map<String, JsonSchema> temp = Maps.newTreeMap();
            for (String type : TypeRegistry.getTypeMap().keySet()) {
                Class typeClass = TypeRegistry.getTypeMap().get(type);

                SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
                try {
                    objectMapper.acceptJsonFormatVisitor(objectMapper.constructType(typeClass), visitor);
                } catch (JsonMappingException e) {
                    throw new TypeRuntimeException("Can't get schema for type: " + type, type, e);
                }
                JsonSchema jsonSchema = visitor.finalSchema();
                temp.put(type, jsonSchema);
            }
            schemaMap = ImmutableMap.copyOf(temp);
        }
        return schemaMap;
    }

    public Map<String, Map<String, String>> getRegisteredTypeProperties() {

        if ( typePropertiesMap == null ) {
            Map<String, Map<String, String>> temp = Maps.newTreeMap();
            for ( String type : TypeRegistry.getTypeMap().keySet() ) {
                Class typeClass = TypeRegistry.getTypeMap().get(type);
                BeanInfo info = null;
                try {
                    info = Introspector.getBeanInfo(typeClass);
                } catch (IntrospectionException e) {
                    throw new TypeRuntimeException("Can't generate info for type: "+type, type, e);
                }

                Map<String, String> properties = Maps.newTreeMap();

                for ( PropertyDescriptor desc : info.getPropertyDescriptors() ) {
                    String name = desc.getName();
                    if ( "class".equals(name) || "id".equals(name) ) {
                        continue;
                    }
                    Class propClass = desc.getPropertyType();
                    properties.put(name, propClass.getSimpleName());
                }
                temp.put(type, properties);
            }
            typePropertiesMap = ImmutableMap.copyOf(temp);
        }
        return typePropertiesMap;

    }


}
