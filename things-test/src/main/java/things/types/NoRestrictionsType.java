package things.types;

import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by markus on 18/05/14.
 */
@Value(typeName = "noRestrictionsType")
@UniqueKey(unique = false)
@Entity
public class NoRestrictionsType {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String property1;
    private String property2;

    public boolean equals(Object o) {
        if ( !(o instanceof NoRestrictionsType) ) {
            return false;
        }
        NoRestrictionsType other = (NoRestrictionsType) o;
        return Objects.equals(getProperty1(), other.getProperty1()) && Objects.equals(getProperty2(), other.getProperty2());
    }

    public String getId() {
        return id;
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    public int hashCode() {
        return Objects.hash(getProperty1(), getProperty2());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }
}
