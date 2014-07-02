package hub.backends.users.types;

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.Set;


/**
 * Created by markus on 23/06/14.
 */
@Value(typeName = "identity")
@Entity(name = "identity")
public class Identity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @NotEmpty
    private String alias;

//    @ElementCollection
//    private Set<String> emails;

    private Integer researcherId;
    private Integer adviserId;

//    private String firstName;
//    private String lastName;
//    private String middleNames;

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getMiddleNames() {
//        return middleNames;
//    }
//
//    public void setMiddleNames(String middleNames) {
//        this.middleNames = middleNames;
//    }

    public Identity() {
    }

    public Identity(String alias) {
        this.alias = alias;
    }

    public Identity(String id, String alias) {
        this.id = id;
        this.alias = alias;
    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if ( obj == this ) return true;
//        if ( obj == null ) return false;
//
//        if ( getClass().equals(obj.getClass()) ) {
//            final Identity other = (Identity) obj;
//            Objects.equals(getId(), other.getId());
//        } else {
//            return false;
//        }
//    }


//    public Set<String> getEmails() {
//        return emails;
//    }
//
//    public void setEmails(Set<String> emails) {
//        this.emails = emails;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getResearcherId() {
        return researcherId;
    }

    public void setResearcherId(Integer researcherId) {
        this.researcherId = researcherId;
    }

    public Integer getAdviserId() {
        return adviserId;
    }

    public void setAdviserId(Integer adviserId) {
        this.adviserId = adviserId;
    }
}
