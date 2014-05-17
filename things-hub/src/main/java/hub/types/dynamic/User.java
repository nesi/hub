package hub.types.dynamic;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import hub.types.persistent.Person;
import things.model.types.Value;

import java.io.Serializable;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner Date: 31/03/14 Time: 4:21 PM
 */
@Value(typeName = "user")
public class User implements Serializable {

    //    private String uniqueId;
    private Person person;
    private Multimap<String, String> roles = ArrayListMultimap.create();
    private Multimap<String, String> usernames = ArrayListMultimap.create();

    public User() {
    }

    public void addRole(String key, String role) {
        this.roles.put(key, role);
    }

    public void addUsername(String key, String id) {
        this.usernames.put(key, id);
    }

    public Person getPerson() {
        return person;
    }

//    public String getUniqueId() {
//        return uniqueId;
//    }

//    public void setUniqueId(String uniqueId) {
//        this.uniqueId = uniqueId;
//    }

    public Multimap<String, String> getRoles() {
        return roles;
    }

    public Multimap<String, String> getUsernames() {
        return usernames;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setRoles(Multimap<String, String> roles) {
        this.roles = roles;
    }

    public void setUsernames(Multimap<String, String> usernames) {
        this.usernames = usernames;
    }

    @Override
    public String toString() {
        return "User{" +
                "person=" + person +
                ", usernames=" + usernames +
                ", roles=" + roles +
                '}';
    }
}
