package hub.backends.users;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import hub.Constants;
import hub.backends.users.types.Person;
import hub.backends.users.types.Property;
import hub.backends.users.types.Project;
import hub.backends.users.types.Username;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import projectdb.Tables;
import things.thing.ThingControl;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by markus on 13/06/14.
 */
public class ProjectDbUtils {

    public static final String RESEARCHER_ID = "researcher_id";
    public static final String ADVISER_ID = "adviser_id";

    public static final String PROJECT_DB_SERVICENAME = "projectdb";

    public static final String PROJECT_DB_ADMIN_ROLENAME = "admin";
    public static final String PROJECT_DB_ADVISER_ROLENAME = "adviser";
    public static final String PROJECT_DB_RESEARCHER_ROLENAME = "researcher";

    public static final String PROJECT_ID = "project_id";
    public static final String TUAKIRI_SERVICE_NAME = "tuakiri";
    private static Logger myLogger = LoggerFactory.getLogger(ProjectDbUtils.class);

    public static String[] parseName(String fullName) {

        String[] result = new String[3];
        String[] tokens = fullName.split(" ");

        if ( tokens.length == 2 ) {
            result[0] = tokens[0];
            result[1] = "";
            result[2] = tokens[1];
        } else if ( tokens.length > 2 ) {
            result[0] = tokens[0];
            result[1] = Joiner.on(" ").join(Arrays.copyOfRange(tokens, 1, tokens.length - 1));
            result[2] = tokens[tokens.length - 1];
        } else {
            myLogger.error("Could not parse: " + fullName);
            return null;
        }

        return result;
    }

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DefaultDSLContext jooq;
    private ThingControl tc;
    private IdentifierProvider idProvider;

    private Map<Integer, String> institutionalRoleMap = null;
    private BiMap<Integer, String> statusMap = null;
    private BiMap<Integer, String> researcherRoleMap = null;
    private BiMap<Integer, String> adviserRoleMap = null;
    private BiMap<Integer, String> siteMap = null;
    private BiMap<Integer, String> projectIdMap = null;

    private Map<Integer, Person> allResearchers = null;
    private Map<Integer, Person> allAdvisers = null;
    private Map<Integer, Project> allProjects = null;

    private Result<Record3<Integer, Integer, Integer>> researcherProjectLinks = null;
    private Map<Integer, Multimap<String, String>> researchersForProjectMap = null;
    private Result<Record3<Integer, Integer, Integer>> adviserProjectLinks = null;
    private Map<Integer, Multimap<String, String>> advisersForProjectMap = null;

    private Map<Integer, Multimap<String, String>> projectsForResearchersMap = null;
    private Map<Integer, Multimap<String, String>> projectsForAdvisersMap = null;

    private Multimap<Integer, Property> personPropertiesForAdvisers = null;
    private Multimap<Integer, Property> personPropertiesForResearchers = null;

    public void recreate() {
        institutionalRoleMap = null;
        statusMap = null;
        researcherRoleMap = null;
        adviserRoleMap = null;
        siteMap = null;
        projectIdMap = null;

        allResearchers = null;
        allAdvisers = null;
        allProjects = null;

        researcherProjectLinks = null;
        researchersForProjectMap = null;
        adviserProjectLinks = null;
        advisersForProjectMap = null;

        projectsForResearchersMap = null;
        projectsForAdvisersMap = null;

        personPropertiesForAdvisers = null;
        personPropertiesForResearchers = null;
    }

    private Person generatePersonFromAdviser(Record rec) {
        Integer id = rec.getValue(Tables.ADVISER.ID, Integer.class);
        String fullName = rec.getValue(Tables.ADVISER.FULLNAME, String.class);

        String[] nameTokens = parseName(fullName);
        if ( nameTokens == null ) {
            myLogger.debug("Could not parse name: " + fullName);
            return null;
        }

        String preferredName = "";
        String email = rec.getValue(Tables.ADVISER.EMAIL, String.class);
        String phone = rec.getValue(Tables.ADVISER.PHONE, String.class);
        String institution = rec.getValue(Tables.ADVISER.INSTITUTION, String.class);
        String instiationRole = null;
        String departement = rec.getValue(Tables.ADVISER.DEPARTMENT, String.class);
        String startDateString = rec.getValue(Tables.ADVISER.STARTDATE, String.class);
        LocalDate startDate = null;
        if ( !Strings.isNullOrEmpty(startDateString) ) {
            startDate = LocalDate.parse(startDateString, dateFormatter);
        }
        String endDateString = rec.getValue(Tables.ADVISER.ENDDATE, String.class);
        LocalDate endDate = null;
        if ( !Strings.isNullOrEmpty(endDateString) ) {
            endDate = LocalDate.parse(endDateString, dateFormatter);
        }
        String status = null;
//        String tuakiriToken = rec.getValue(Tables.ADVISER.TUAKIRITOKEN);
        String tuakiriUniqueId = rec.getValue(Tables.ADVISER.TUAKIRIUNIQUEID);

        Instant lastModified = rec.getValue(Tables.ADVISER.LASTMODIFIED, Timestamp.class).toInstant();

        Person p = generatePerson(nameTokens[0], nameTokens[1], nameTokens[2], preferredName, email, lastModified, institution, instiationRole, departement, tuakiriUniqueId);
        Property prop = new Property();
        prop.setService(PROJECT_DB_SERVICENAME);
        prop.setKey(ADVISER_ID);
        prop.setValue(id.toString());
        p.addProperty(prop);

        Byte isAdmin = rec.getValue(Tables.ADVISER.ISADMIN, Byte.class);
        if ( isAdmin.intValue() != 0 ) {
            p.addRole(PROJECT_DB_SERVICENAME, PROJECT_DB_ADMIN_ROLENAME);
        }
        p.addRole(PROJECT_DB_SERVICENAME, PROJECT_DB_ADVISER_ROLENAME);

        Collection<Property> props = getPersonPropertiesForAdviser(id);
        p.addProperties(props);

        Multimap<String, String> usernames = extractUsernames(props);
        p.addUsernames(usernames);

        Optional<Multimap<String, String>> roles = getProjectsForAdviser(id);
        if ( roles.isPresent() ) {
            p.addRoles(roles.get());
        }

        return p;

    }

    private String getSite(Integer id) {
        return getSiteMap().get(id);
    }

    private Map<Integer, String> getSiteMap() {
        if ( siteMap == null ) {
            Result<Record> query = jooq.select().from(Tables.SITE).fetch();
            siteMap = HashBiMap.create();
            for ( Record rec : query ) {
                Integer id = rec.getValue(Tables.SITE.ID, Integer.class);
                String name = rec.getValue(Tables.SITE.NAME, String.class);
                siteMap.put(id, name);
            }
        }
        return siteMap;
    }

    private Multimap<Integer, Property> getPersonPropertiesForResearcherMap() {
        if ( personPropertiesForResearchers == null ) {
            Result<Record> query = jooq.select().from(Tables.RESEARCHER_PROPERTIES).fetch();
            personPropertiesForResearchers = HashMultimap.create();
            query.forEach(rec -> {
                String key = rec.getValue(Tables.RESEARCHER_PROPERTIES.PROPNAME, String.class);
                String value = rec.getValue(Tables.RESEARCHER_PROPERTIES.PROPVALUE, String.class);
                Integer siteId = rec.getValue(Tables.RESEARCHER_PROPERTIES.SITEID, Integer.class);
                Integer researcherId = rec.getValue(Tables.RESEARCHER_PROPERTIES.RESEARCHERID, Integer.class);
                String site = getSite(siteId);


                Property p = new Property();
                p.setService(site);
                p.setValue(value);
                p.setKey(key);

                personPropertiesForResearchers.put(researcherId, p);

            });
        }
        return personPropertiesForResearchers;
    }

    private Multimap<Integer, Property> getPersonPropertiesForAdvisersMap() {
        if ( personPropertiesForAdvisers == null ) {
            Result<Record> query = jooq.select().from(Tables.ADVISER_PROPERTIES).fetch();
            personPropertiesForAdvisers = HashMultimap.create();
            query.forEach(rec -> {
                String key = rec.getValue(Tables.ADVISER_PROPERTIES.PROPNAME, String.class);
                String value = rec.getValue(Tables.ADVISER_PROPERTIES.PROPVALUE, String.class);
                Integer siteId = rec.getValue(Tables.ADVISER_PROPERTIES.SITEID, Integer.class);
                Integer adviserId = rec.getValue(Tables.ADVISER_PROPERTIES.ADVISERID, Integer.class);
                String site = getSite(siteId);

                Property p = new Property();
                p.setService(site);
                p.setValue(value);
                p.setKey(key);

                personPropertiesForAdvisers.put(adviserId, p);

            });
        }
        return personPropertiesForAdvisers;
    }

    private Collection<Property> getPersonPropertiesForResearcher(int researcherId) {
        return getPersonPropertiesForResearcherMap().get(researcherId);
    }

    private Collection<Property> getPersonPropertiesForAdviser(int adviserId) {
        return getPersonPropertiesForAdvisersMap().get(adviserId);
    }


    private Person generatePersonFromResearcher(Record rec) {
        Integer id = rec.getValue(Tables.RESEARCHER.ID, Integer.class);
        String fullName = rec.getValue(Tables.RESEARCHER.FULLNAME, String.class);

        String[] nameTokens = parseName(fullName);
        if ( nameTokens == null ) {
            myLogger.debug("Could not parse name: " + fullName);
            return null;
        }

        String preferredName = rec.getValue(Tables.RESEARCHER.PREFERREDNAME, String.class);
        String email = rec.getValue(Tables.RESEARCHER.EMAIL, String.class);
        String phone = rec.getValue(Tables.RESEARCHER.PHONE, String.class);
        String institution = rec.getValue(Tables.RESEARCHER.INSTITUTION, String.class);
        String instiationRole = getInstitutionalRoleName(rec.getValue(Tables.RESEARCHER.INSTITUTIONALROLEID, Integer.class));
        String departement = rec.getValue(Tables.RESEARCHER.DEPARTMENT, String.class);
        String startDateString = rec.getValue(Tables.RESEARCHER.STARTDATE, String.class);
        LocalDate startDate = null;
        if ( !Strings.isNullOrEmpty(startDateString) ) {
            startDate = LocalDate.parse(startDateString, dateFormatter);
        }
        String endDateString = rec.getValue(Tables.RESEARCHER.ENDDATE, String.class);
        LocalDate endDate = null;
        if ( !Strings.isNullOrEmpty(endDateString) ) {
            try {
                endDate = LocalDate.parse(endDateString, dateFormatter);
            } catch (DateTimeParseException e) {
                myLogger.debug("Could not parse enddate: " + endDateString);
            }
        }
        String status = getStatusName(rec.getValue(Tables.RESEARCHER.STATUSID));

        Instant lastModified = rec.getValue(Tables.RESEARCHER.LASTMODIFIED, Timestamp.class).toInstant();

        Person p = generatePerson(nameTokens[0], nameTokens[1], nameTokens[2], preferredName, email, lastModified, institution, instiationRole, departement, null);
        Property prop = new Property();
        prop.setService(PROJECT_DB_SERVICENAME);
        prop.setKey(RESEARCHER_ID);
        prop.setValue(id.toString());
        p.addProperty(prop);

        Collection<Property> props = getPersonPropertiesForResearcher(id);
        p.addProperties(props);

        Multimap<String, String> usernames = extractUsernames(props);
        p.addUsernames(usernames);

        p.addRole(PROJECT_DB_SERVICENAME, PROJECT_DB_RESEARCHER_ROLENAME);

        Optional<Multimap<String, String>> roles = getProjectsForResearcher(id);
        if ( roles.isPresent() ) {
            p.addRoles(roles.get());
        }

        return p;

    }

    private Multimap<String, String> extractUsernames(Collection<Property> properties) {

        Multimap<String, String> result = HashMultimap.create();

        properties.stream()
                .map(p -> generateUsername(p))
                .filter(Optional::isPresent)
                .forEach(p -> {
                    result.put(p.get().getService(), p.get().getUsername());
                });

        return result;

    }

    private Optional<Username> generateUsername(Property pp) {

        if ( pp.getKey().equals("linuxUsername") ) {
            Username un = new Username(pp.getService(), pp.getValue());
            return Optional.of(un);
        } else {
            return Optional.absent();
        }

    }

    private Person generatePerson(String firstName, String middleNames, String lastName, String preferredName, String email, Instant lastModified, String institution, String instiationRole, String departement, String tuakiriUniqueId) {

        myLogger.debug("Generating person: "+firstName+" "+lastName);

        Person p = new Person();
        p.setFirst_name(firstName);
        p.setMiddle_names(middleNames);
        p.setLast_name(lastName);
        p.addEmail(email);
        p.setPreferred_name(preferredName);
        p.setLastModified(lastModified.toEpochMilli());
        if ( !Strings.isNullOrEmpty(institution) ) {
            if ( Strings.isNullOrEmpty(instiationRole) ) {
                p.addRole(institution, Constants.DEFAULT_ROLE_NAME);
            } else {
                p.addRole(institution, instiationRole);
            }
        }
        if ( !Strings.isNullOrEmpty(departement) ) {
            p.addRole(departement, Constants.DEFAULT_ROLE_NAME);
        }
        if ( !Strings.isNullOrEmpty(tuakiriUniqueId) ) {
            p.addUsername(Constants.TUAKIRI_UNIQUE_ID_SERVICE, tuakiriUniqueId);
            p.addRole(Constants.TUAKIRI_SERVICE_NAME, Constants.DEFAULT_ROLE_NAME);
        }

        return p;
    }

    private Project generateProject(Record rec) {

        Project p = new Project();

        Integer id = rec.getValue(Tables.PROJECT.ID, Integer.class);
        String projectCode = rec.getValue(Tables.PROJECT.PROJECTCODE);
        String name = rec.getValue(Tables.PROJECT.NAME);
        LocalDateTime lastModified = rec.getValue(Tables.PROJECT.LASTMODIFIED, Timestamp.class).toLocalDateTime();

        p.setProjectCode(projectCode);
        p.setName(name);
        p.setLastModified(lastModified);

        p.addProperty(PROJECT_ID, id.toString());
        Optional<Multimap<String, String>> res = getResearchersForProject(id);
        if ( res.isPresent() ) {
            p.addMembers(res.get());
        }
        Optional<Multimap<String, String>> adv = getAdvisersForProject(id);
        if ( adv.isPresent() ) {
            p.addMembers(adv.get());
        }


        return p;
    }

    private Optional<Multimap<String, String>> getProjectsForAdviser(Integer adviserId) {
        return Optional.fromNullable(getProjectsForAdviserMap().get(adviserId));
    }

    private Optional<Multimap<String, String>> getProjectsForResearcher(Integer researcherId) {
        return Optional.fromNullable(getProjectsForResearchersMap().get(researcherId));
    }

    private synchronized Map<Integer, Multimap<String, String>> getProjectsForAdviserMap() {
        if ( projectsForAdvisersMap == null ) {
            Result<Record3<Integer, Integer, Integer>> query = getAdviserProjectLinks();
            projectsForAdvisersMap = Maps.newHashMap();

            query.forEach(rec -> {
                String role = getAdviserRoleMap().get(rec.getValue(Tables.ADVISER_PROJECT.ADVISERROLEID));
                Integer id = rec.getValue(Tables.ADVISER_PROJECT.ADVISERID);
                Integer projectId = rec.getValue(Tables.ADVISER_PROJECT.PROJECTID);

                Multimap<String, String> temp = projectsForAdvisersMap.get(id);
                if ( temp == null ) {
                    temp = TreeMultimap.create();
                    projectsForAdvisersMap.put(id, temp);
                }

                String projectCode = getProjectIdMap().get(projectId);
                temp.put(projectCode, role);

            });
        }
        return projectsForAdvisersMap;
    }

    private synchronized Map<Integer, Multimap<String, String>> getProjectsForResearchersMap() {
        if ( projectsForResearchersMap == null ) {
            Result<Record3<Integer, Integer, Integer>> query = getResearcherProjectLinks();
            projectsForResearchersMap = Maps.newHashMap();

            query.forEach(rec -> {
                String role = getResearcherRoleMap().get(rec.getValue(Tables.RESEARCHER_PROJECT.RESEARCHERROLEID));
                Integer id = rec.getValue(Tables.RESEARCHER_PROJECT.RESEARCHERID);
                Integer projectId = rec.getValue(Tables.RESEARCHER_PROJECT.PROJECTID);

                Multimap<String, String> temp = projectsForResearchersMap.get(id);
                if ( temp == null ) {
                    temp = TreeMultimap.create();
                    projectsForResearchersMap.put(id, temp);
                }

                String projectCode = getProjectIdMap().get(projectId);
                temp.put(projectCode, role);

            });
        }
        return projectsForResearchersMap;
    }

    private synchronized Result<Record3<Integer, Integer, Integer>> getResearcherProjectLinks() {
        if ( researcherProjectLinks == null ) {
            researcherProjectLinks = jooq.select(Tables.RESEARCHER_PROJECT.PROJECTID, Tables.RESEARCHER_PROJECT.RESEARCHERID, Tables.RESEARCHER_PROJECT.RESEARCHERROLEID).from(Tables.RESEARCHER_PROJECT).fetch();
        }
        return researcherProjectLinks;
    }

    private Map<Integer, Multimap<String, String>> getResearchersForProjectMap() {

        if ( researchersForProjectMap == null ) {
            Result<Record3<Integer, Integer, Integer>> query = getResearcherProjectLinks();

            researchersForProjectMap = Maps.newHashMap();

            query.forEach(rec -> {
                String role = getResearcherRoleMap().get(rec.getValue(Tables.RESEARCHER_PROJECT.RESEARCHERROLEID));
                Integer id = rec.getValue(Tables.RESEARCHER_PROJECT.RESEARCHERID);
                Integer projectId = rec.getValue(Tables.RESEARCHER_PROJECT.PROJECTID);

                Multimap<String, String> temp = researchersForProjectMap.get(projectId);
                if ( temp == null ) {
                    temp = TreeMultimap.create();
                    researchersForProjectMap.put(projectId, temp);
                }

                Person p = getAllResearchersMap().get(id);
                if ( p != null ) {
                    if ( Strings.isNullOrEmpty(p.getAlias() ))  {
                        p = idProvider.createIdentifier(p);
                    }
                    temp.put(role, p.getAlias());
                } else {
                    throw new RuntimeException("Can't find researcher with id: " + id);
                }
            });
        }
        return researchersForProjectMap;

    }

    private synchronized Result<Record3<Integer, Integer, Integer>> getAdviserProjectLinks() {
        if ( adviserProjectLinks == null ) {
            adviserProjectLinks = jooq.select(Tables.ADVISER_PROJECT.PROJECTID, Tables.ADVISER_PROJECT.ADVISERID, Tables.ADVISER_PROJECT.ADVISERROLEID).from(Tables.ADVISER_PROJECT).fetch();
        }
        return adviserProjectLinks;
    }


    private Map<Integer, Multimap<String, String>> getAdvisersForProjectMap() {

        if ( advisersForProjectMap == null ) {
            Result<Record3<Integer, Integer, Integer>> query = getAdviserProjectLinks();

            advisersForProjectMap = Maps.newHashMap();

            query.forEach(rec -> {
                String role = getAdviserRoleMap().get(rec.getValue(Tables.ADVISER_PROJECT.ADVISERROLEID));
                Integer id = rec.getValue(Tables.ADVISER_PROJECT.ADVISERID);
                Integer projectId = rec.getValue(Tables.ADVISER_PROJECT.PROJECTID);

                Multimap<String, String> temp = advisersForProjectMap.get(projectId);
                if ( temp == null ) {
                    temp = TreeMultimap.create();
                    advisersForProjectMap.put(projectId, temp);
                }

                Person p = getAllAdvisersMap().get(id);
                if ( p != null ) {
                    if ( Strings.isNullOrEmpty(p.getAlias()) ) {
                        p = idProvider.createIdentifier(p);
                    }
                    temp.put(role, p.getAlias());
                } else {
                    throw new RuntimeException("Can't find researcher with id: " + id);
                }
            });
        }
        return advisersForProjectMap;

    }

    private Optional<Multimap<String, String>> getResearchersForProject(Integer pId) {

        return Optional.fromNullable(getResearchersForProjectMap().get(pId));
    }

    private Optional<Multimap<String, String>> getAdvisersForProject(Integer pId) {
        return Optional.fromNullable(getAdvisersForProjectMap().get(pId));
    }

    public synchronized BiMap<Integer, String> getProjectIdMap() {
        if ( projectIdMap == null ) {
            Result<Record2<Integer, String>> query = jooq.select(Tables.PROJECT.ID, Tables.PROJECT.PROJECTCODE).from(Tables.PROJECT).fetch();
            projectIdMap = HashBiMap.create();
            query.forEach(rec -> {
                Integer id = rec.getValue(Tables.PROJECT.ID, Integer.class);
                String code = rec.getValue(Tables.PROJECT.PROJECTCODE, String.class);
                projectIdMap.put(id, code);
            });
        }

        return projectIdMap;
    }

    public Map<Integer, Project> getAllProjects() {

        if ( allProjects == null ) {

            Result<Record> query = jooq.select().from(Tables.PROJECT).fetch();
            allProjects = Maps.newTreeMap();
            for ( Record rec : query ) {
                Project p = generateProject(rec);
                Integer id = rec.getValue(Tables.PROJECT.ID, Integer.class);
                allProjects.put(id, p);
            }

        }

        return allProjects;
    }

    public synchronized Map<Integer, Person> getAllResearchersMap() {
        if ( allResearchers == null ) {
            Result<Record> query = jooq.select().from(Tables.RESEARCHER).fetch();
            List<Person> all = query.map(rec -> generatePersonFromResearcher(rec));
            allResearchers = Maps.newHashMap();
            for ( Person p : all ) {
                Optional<String> id = p.getPropertyValue(PROJECT_DB_SERVICENAME, RESEARCHER_ID);
                if ( !id.isPresent() ) {
                    throw new RuntimeException("Can't find researcher with id: " + id);
                }
                int id_int = Integer.parseInt(id.get());
                if ( allResearchers.get(id_int) != null ) {
                    throw new RuntimeException("Already researcher with id in researcher map: " + id);
                }
                allResearchers.put(id_int, p);
            }
        }
        return allResearchers;
    }

    public Collection<Person> getAllAdvisers() {
        return getAllAdvisersMap().values();
    }

    public synchronized Map<Integer, Person> getAllAdvisersMap() {
        if ( allAdvisers == null ) {
            Result<Record> query = jooq.select().from(Tables.ADVISER).fetch();
            List<Person> all = query.map(rec -> generatePersonFromAdviser(rec));
            allAdvisers = Maps.newHashMap();
            for ( Person p : all ) {
                Optional<String> id = p.getPropertyValue(PROJECT_DB_SERVICENAME, ADVISER_ID);
                if ( !id.isPresent() ) {
                    throw new RuntimeException("Can't find adviser with id: " + id);
                }
                int id_int = Integer.parseInt(id.get());
                if ( allAdvisers.get(id_int) != null ) {
                    throw new RuntimeException("Already adviser with id in adviser map: " + id);
                }
                allAdvisers.put(id_int, p);
            }
        }
        return allAdvisers;
    }

    private String getAdviserRole(Integer roleId) {
        return getAdviserRoleMap().get(roleId);
    }

    public synchronized Collection<Person> getAllResearchers() {
        return getAllResearchersMap().values();
    }

    private String getResearcherRole(int id) {
        return getResearcherRoleMap().get(id);
    }

    private synchronized BiMap<Integer, String> getResearcherRoleMap() {
        if ( researcherRoleMap == null ) {
            Result<Record> result = jooq.select().from(Tables.RESEARCHERROLE).fetch();
            researcherRoleMap = HashBiMap.create();
            result.forEach(rec -> {
                        Integer id = rec.getValue(Tables.RESEARCHERROLE.ID, Integer.class);
                        String name = rec.getValue(Tables.RESEARCHERROLE.NAME, String.class);
                        researcherRoleMap.put(id, name);
                    }
            );
        }
        return researcherRoleMap;
    }

    private synchronized BiMap<Integer, String> getAdviserRoleMap() {
        if ( adviserRoleMap == null ) {
            Result<Record> result = jooq.select().from(Tables.ADVISERROLE).fetch();
            adviserRoleMap = HashBiMap.create();
            result.forEach(rec -> {
                        Integer id = rec.getValue(Tables.ADVISERROLE.ID, Integer.class);
                        String name = rec.getValue(Tables.ADVISERROLE.NAME, String.class);
                        adviserRoleMap.put(id, name);
                    }
            );
        }
        return adviserRoleMap;
    }

    private synchronized Map<Integer, String> getInstitutionalRoleMap() {
        if ( institutionalRoleMap == null ) {
            Result<Record2<Integer, String>> all = jooq.select(Tables.INSTITUTIONALROLE.ID, Tables.INSTITUTIONALROLE.NAME)
                    .from(Tables.INSTITUTIONALROLE).fetch();
            institutionalRoleMap = Maps.newHashMap();
            all.forEach(rec -> {
                Integer id = rec.getValue(Tables.INSTITUTIONALROLE.ID, Integer.class);
                String name = rec.getValue(Tables.INSTITUTIONALROLE.NAME, String.class);
                institutionalRoleMap.put(id, name);
            });
        }
        return institutionalRoleMap;
    }

    private String getInstitutionalRoleName(Integer roleId) {
        String name = getInstitutionalRoleMap().get(roleId);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "No institutional rolename for id: " + roleId);
        return name;
    }

    private synchronized BiMap<Integer, String> getStatusMap() {
        if ( statusMap == null ) {
            Result<Record2<Integer, String>> result = jooq.select(Tables.RESEARCHER_STATUS.ID, Tables.RESEARCHER_STATUS.NAME).from(Tables.RESEARCHER_STATUS).fetch();
            statusMap = HashBiMap.create();
            result.forEach(rec -> {
                Integer id = rec.getValue(Tables.RESEARCHER_STATUS.ID, Integer.class);
                String name = rec.getValue(Tables.RESEARCHER_STATUS.NAME, String.class);
                statusMap.put(id, name);
            });
        }
        return statusMap;
    }

    private String getStatusName(int id) {
        String name = getStatusMap().get(id);
        Preconditions.checkArgument(!Strings.isNullOrEmpty(name), "No status name for id: " + id);
        return name;
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
    public void setIdProvider(IdentifierProvider idProvider) {
        this.idProvider = idProvider;
    }

}
