package hub.backends.users;

import hub.backends.users.types.Group;
import hub.backends.users.types.Person;

import java.util.Collection;
import java.util.Set;

/**
 * Created by markus on 11/07/14.
 */
public interface UserManagement {
    Collection<Group> getAllGroups();

    Set<String> getAllPersonAliases();

    Collection<Person> getAllPersons();

    String getGroup(Integer projectId);

    Group getGroup(String groupAlias);

    Person getPerson(String personAlias);

    boolean isAdmin(String username, String password);

    void recreate();
}
