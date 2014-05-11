package things.types;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import java.util.Objects;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/04/14
 * Time: 7:38 PM
 */
@UniqueKey(unique = true)
@Value(typeName = "person")
public class Person {

    private String id;

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @Email
    private String email;

    private Boolean isActive = false;

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }


    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Person() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;

        if (getClass().equals(obj.getClass())) {
            final Person other = (Person) obj;
            return Objects.equals(getFirstName(), other.getFirstName()) && Objects.equals(getLastName(), other.getLastName())
                    && Objects.equals(getEmail(), other.getEmail());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
//                ", id='" + id + '\'' +
                '}';
    }
}
