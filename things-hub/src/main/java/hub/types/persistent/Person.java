package hub.types.persistent;

import com.google.common.base.Strings;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import java.util.Objects;

/**
 * A Person is an object that holds person details, like first, middle and last name, email.
 */
@UniqueKey(unique = true)
@Value(typeName = "person")
public class Person {

    @Email
    private String email;
    @NotEmpty
    private String first_name;
    //    @Id
    String id;
    @NotEmpty
    private String last_name;
    private String middle_names = "";

    public Person(String first_name, String last_name, String email) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
    }

    public Person() {

    }

    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;

        if (getClass().equals(obj.getClass())) {
            final Person other = (Person) obj;
            return Objects.equals(getFirst_name(), other.getFirst_name()) && Objects.equals(getLast_name(), other.getLast_name());
        } else {
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {

        return first_name;
    }

    public String getId() {
        return id;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMiddle_names() {
        return middle_names;
    }

    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name());
    }

    /**
     * Convenience method, outputs the persons' name.
     * <p>
     * Format depends on whether it contains a middle name or not.
     *
     * @return the name string
     */
    public String nameToString() {
        if (Strings.isNullOrEmpty(middle_names)) {
            return first_name + " " + last_name;
        } else {
            return first_name + " " + middle_names + " " + last_name;
        }
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMiddle_names(String middle_names) {
        this.middle_names = middle_names;
    }

    @Override
    public String toString() {
        return "Person{" +
                "first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_names='" + middle_names + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
