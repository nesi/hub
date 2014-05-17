package hub.actions;

import com.google.common.collect.Maps;
import hub.types.persistent.Person;
import hub.types.persistent.Role;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectdb.Tables;
import rx.Observable;
import things.exceptions.ThingException;
import things.exceptions.ThingRuntimeException;
import things.exceptions.ValueException;
import things.thing.Thing;
import things.thing.ThingAction;
import things.thing.ThingControl;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author: Markus Binsteiner
 */
public class ImportRoleAndGroupAction implements ThingAction {

    public static final String PROJECT_DB_ADMIN_VALUE = "admin";
    public static final String PROJECT_DB_ADVISER_VALUE = "adviser";
    public static final String PROJECT_DB_KEY = "projectdb";
    private static final Logger myLogger = LoggerFactory.getLogger(ImportRoleAndGroupAction.class);
    private DefaultDSLContext jooq;
    private Map<Integer, String> projectMap = null;
    private Map<Integer, String> roleMap = null;
    private Map<String, Thing<Role>> roles = Maps.newConcurrentMap();
    private ThingControl tc;
    private UserUtils userUtils;

    private void checkAdviserAndAdminRole(Thing<Person> person) {

        try {
            Person p = tc.getValue(person);

            SelectConditionStep<Record3<String, Integer, Byte>> condition = null;

            condition = jooq.select(Tables.ADVISER.FULLNAME, Tables.ADVISER.ID, Tables.ADVISER.ISADMIN).from(Tables.ADVISER)
                    .where(Tables.ADVISER.FULLNAME.contains(p.getFirst_name()))
                    .and(Tables.ADVISER.FULLNAME.contains(p.getLast_name()));
            if ( !StringUtils.isEmpty(p.getMiddle_names()) ) {
                condition = condition.and(Tables.ADVISER.FULLNAME.contains(p.getMiddle_names()));

            }

            Result<Record3<String, Integer, Byte>> result = condition.fetch();

            if ( result.size() == 0 ) {
                return;
            } else if ( result.size() > 1 ) {
                System.out.println("More than one match found for: " + p.nameToString() + ". Ignoring...");
            }

            String fullname = result.get(0).getValue(Tables.ADVISER.FULLNAME);
            Integer id = result.get(0).getValue(Tables.ADVISER.ID);
            Byte isAdmin = result.get(0).getValue(Tables.ADVISER.ISADMIN);

            Thing<Role> adviserRole = getRole(PROJECT_DB_KEY, PROJECT_DB_ADVISER_VALUE);
            myLogger.debug("Adding adviser role' to " + p.nameToString());
            tc.addChildThing(person, adviserRole);


            if ( isAdmin.intValue() != 0 ) {
                Thing<Role> adminRole = getRole(PROJECT_DB_KEY, PROJECT_DB_ADMIN_VALUE);
                myLogger.debug("Adding admin role to " + p.nameToString());
                tc.addChildThing(person, adminRole);
            }

        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }

    }

    private void checkProjectDb(Thing person) {
        checkResearcherRole(person);
        checkAdviserAndAdminRole(person);
    }

    private void checkResearcherRole(Thing<Person> person) {

        try {
            Person p = tc.getValue(person);

            SelectConditionStep<Record2<Integer, String>> condition = null;

            condition = jooq.select(Tables.RESEARCHER.ID, Tables.RESEARCHER.FULLNAME)
                    .from(Tables.RESEARCHER)
                    .where(Tables.RESEARCHER.FULLNAME.contains(p.getFirst_name()))
                    .and(Tables.RESEARCHER.FULLNAME.contains(p.getLast_name()));
            if ( !StringUtils.isEmpty(p.getMiddle_names()) ) {
                condition = condition.and(Tables.RESEARCHER.FULLNAME.contains(p.getMiddle_names()));
            }

            Result<Record2<Integer, String>> result = condition.fetch();

            if ( result == null || result.size() == 0 ) {
                // means no researcher
                myLogger.debug("Could not find researcher role for: " + p.nameToString());
                System.out.println("Could not find researcher role for: " + p.nameToString());
                return;
            } else if ( result.size() > 1 ) {
                myLogger.debug("Found multiple results for person: " + p.nameToString());
                System.out.println("Found multiple results for person: " + p.nameToString());
                return;
            }

            Integer id = result.get(0).getValue(Tables.RESEARCHER.ID);

            SelectConditionStep<Record2<Integer, Integer>> condition2 = jooq.select(Tables.RESEARCHER_PROJECT.PROJECTID, Tables.RESEARCHER_PROJECT.RESEARCHERROLEID)
                    .from(Tables.RESEARCHER_PROJECT)
                    .where(Tables.RESEARCHER_PROJECT.RESEARCHERID.equal(id));

            Result<Record2<Integer, Integer>> projectsForResearcher = condition2.fetch();

            if ( projectsForResearcher.size() == 0 ) {
                System.out.println("No projects found for " + p.nameToString());
            } else {
                System.out.println(projectsForResearcher.size() + " projects found for " + p.nameToString());
            }

            for ( Record2<Integer, Integer> rec : projectsForResearcher ) {

                Integer projectId = rec.getValue(Tables.RESEARCHER_PROJECT.PROJECTID);
                Integer roleId = rec.getValue(Tables.RESEARCHER_PROJECT.RESEARCHERROLEID);

                String projectName = getProjectMap().get(projectId);
                if ( StringUtils.isEmpty(projectName) ) {
                    throw new ThingRuntimeException("Can't find project for id: " + projectId);
                }

                String roleName = getRoleMap().get(roleId);
                if ( StringUtils.isEmpty(roleName) ) {
                    throw new ThingRuntimeException("Can't find role for id: " + roleId);
                }

                Thing<Role> role = getRole(projectName, roleName);
                tc.addChildThing(person, role);

            }

        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }

    }

    @Override
    public String execute(String s, Observable<? extends Thing<?>> things, Map<String, String> stringStringMap) {

        Observable<Thing<Person>> persons = userUtils.convertToPerson(things);

        persons.toBlockingObservable().forEach(p -> checkProjectDb(p));
        return null;
    }

    public synchronized Map<Integer, String> getProjectMap() {
        if ( projectMap == null ) {
            SelectJoinStep<Record2<Integer, String>> condition = jooq.select(Tables.PROJECT.ID, Tables.PROJECT.PROJECTCODE)
                    .from(Tables.PROJECT);
            projectMap = Maps.newHashMap();
            Result<Record2<Integer, String>> result = condition.fetch();
            result.forEach(rec -> projectMap.put(rec.getValue(Tables.PROJECT.ID), rec.getValue(Tables.PROJECT.PROJECTCODE)));
        }
        return projectMap;
    }

    private Thing<Role> getRole(String key, String rolename) throws ValueException, ThingException {

        if ( roles.get(key + "_" + rolename) == null ) {
            Role temp = new Role(rolename);
            try {
                Thing thing = tc.observeUniqueThingMatchingKeyAndValue(key, temp).toBlockingObservable().single();
                roles.put(key + "_" + rolename, thing);
            } catch (NoSuchElementException nsee) {
                Thing t = tc.createThing(key, temp);
                roles.put(key + "_" + rolename, t);
            }

        }

        return roles.get(key + "_" + rolename);
    }

    public synchronized Map<Integer, String> getRoleMap() {
        if ( roleMap == null ) {
            SelectJoinStep<Record> condition = jooq.select().from(Tables.RESEARCHERROLE);
            roleMap = Maps.newHashMap();
            Result<Record> result = condition.fetch();
            result.forEach(rec -> roleMap.put(rec.getValue(Tables.RESEARCHERROLE.ID), rec.getValue(Tables.RESEARCHERROLE.NAME)));
        }
        return roleMap;
    }

    @Resource( name = "projectDbContext" )
    public void setJooq(DefaultDSLContext jooq) {
        this.jooq = jooq;
    }

    @Inject
    public void setTc(ThingControl tc) {
        this.tc = tc;
    }

    @Inject
    public void setUserUtils(UserUtils userUtils) {
        this.userUtils = userUtils;
    }
}
