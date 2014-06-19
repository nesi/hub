package hub.types.dynamic;

import com.google.common.collect.ComparisonChain;
import things.model.types.Value;

import java.util.Comparator;
import java.util.Objects;

@Value(typeName = "person_property")
public class PersonProperty implements Comparable<PersonProperty> {

    private String service;
    private String key;
    private String value;

    @Override
    public int compareTo(PersonProperty o2) {
        return ComparisonChain.start()
                .compare(getService(), o2.getService())
                .compare(getKey(), o2.getKey())
                .compare( getValue(), o2.getValue())
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final PersonProperty other = (PersonProperty) obj;
            return Objects.equals(getService(), other.getService()) && Objects.equals(getKey(), other.getKey()) && Objects.equals(getValue(), other.getValue());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(getService(), getKey(), getValue());
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "PersonProperty{" +
                "service='" + service + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
