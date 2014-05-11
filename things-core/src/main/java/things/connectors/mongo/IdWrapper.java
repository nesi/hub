package things.connectors.mongo;


/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 6/05/14
 * Time: 10:52 PM
 */
public class IdWrapper {


    private String id;
    private Object value;

    public IdWrapper(String id) {
        this.id = id;
    }

    public IdWrapper(Object value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
