package hub.types.dynamic;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import things.model.types.Value;

import java.util.Collection;
import java.util.Objects;

/**
 * Created by markus on 28/05/14.
 */
@Value(typeName = "group")
public class Group {

    private final String groupName;

    private Multimap<String, User> members = LinkedHashMultimap.create();

//    public Group() {
//    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }

    public Multimap<String, User> getMembers() {
        return members;
    }

    public void addMember(User member) {
        Collection<String> roles = member.getRoles().get(groupName);
        for ( String role : roles ) {
            members.put(role, member);
        }

    }


    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Group other = (Group) obj;
            return Objects.equals(getGroupName(), other.getGroupName());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(getGroupName());
    }
}
