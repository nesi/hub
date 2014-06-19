package hub.actions;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import hub.types.dynamic.Group;
import hub.types.dynamic.Person;
import hub.types.dynamic.PersonProperty;
import hub.types.dynamic.Project;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingAction;
import things.thing.ThingControl;

import javax.inject.Inject;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 13/06/14.
 */
public class UserImporter implements ThingAction {

    private ThingControl tc;
    private ProjectDbUtils projectUtils;
    private UserManagement userManagement;

    @Override
    public Observable<? extends Thing<?>> execute(String actionName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

//        projectUtils.getAllResearchers();

//        for (Person p : userManagement.getAllPersons().values()) {
//            System.out.println(p);
//        }

//        for (String key : userManagement.getPossibleDuplicates().keySet() ) {
//            System.out.println(key);
//        }
        Date now = new Date();
        System.out.println("Start");

        for ( String key : userManagement.getAllPersons().keySet() ) {
            if ( !key.toLowerCase().contains("markus")) {
                continue;
            }
            System.out.println("KEY: "+key);
//            for (PersonProperty prop : userManagement.getAllPersons().get(key).getProperties() ) {
//                    System.out.println("\t" +prop.getService()+" -> "+prop.getKey()+": "+prop.getValue());
//            }
//            for ( String group : userManagement.getAllPersons().get(key).getRoles().keySet() ) {
//                System.out.println("GROUP: "+group);
//                for ( String role: userManagement.getAllPersons().get(key).getRoles().get(group)) {
//                    System.out.println("\t"+role);
//                }
//            }
        }

        Date end = new Date();

        System.out.println("Time: "+(end.getTime() - now.getTime()));


        for ( Group g : userManagement.getAllGroups().values() ) {
            System.out.println(g);
        }

//        for ( Project p : projectUtils.getAllProjects().values() ) {
//            System.out.println("Project: "+p.getProjectCode());
//            System.out.println("Members:");
//            for ( String role : p.getMembers().keySet() ) {
//                System.out.println("\tRole: "+role);
//                for ( Person pers : p.getMembers().get(role) ) {
//                    System.out.println("\t\t"+pers.toString());
//                }
//            }
//        }

        return null;
    }




    @Override
    public Set<String> getSupportedActionNames() {
        return ImmutableSet.<String>builder().add("update_users").build();
    }


    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Inject
    public void setProjectUtils(ProjectDbUtils projectUtils) {
        this.projectUtils = projectUtils;
    }

    @Inject
    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
}
