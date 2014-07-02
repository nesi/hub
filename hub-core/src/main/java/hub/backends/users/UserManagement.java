package hub.backends.users;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import hub.Constants;
import hub.backends.users.types.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

import static java.util.stream.Collectors.toMap;

/**
 * Created by markus on 18/06/14.
 */
public class UserManagement  {

    public static final Logger myLogger = LoggerFactory.getLogger(UserManagement.class);

    private final Map<String, String> admins = Maps.newHashMap();

    public UserManagement() {
        Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream("/etc/hub/admins.conf");
			prop.load(input);

			for (final String name : prop.stringPropertyNames())
				admins.put(name, prop.getProperty(name));

		} catch (Exception e) {
			throw new RuntimeException(
					"Can't load users config file: " + e.getLocalizedMessage(),
					e);
		}

    }


    @Autowired
    private ProjectDbUtils projectDbUtils;

    @Autowired
    private IdentifierProvider idProvider;

    @Autowired
    private BCryptPasswordEncoder encoder;

    private Map<String, Person> allPersons;

    private Map<String, Group> allGroups;

    public Optional<Person> getAdmin(String username, String password) {
        if ( admins.get(username) == null ) {
            return Optional.empty();
        }

        if ( password.equals(admins.get(username)) ) {
            return Optional.of(getAllPersons().get(username));
        } else {
            return Optional.empty();
        }
    }

    public String getGroup(Integer projectId) {
        return projectDbUtils.getProjectIdMap().get(projectId);
    }

//    public Integer getProjectId(String projectCode) {
//        return projectDbUtils.getProjectIdMap().inverse().get(projectCode);
//    }
//
//    public String getResearcher(Integer researcherId) {
//        return idProvider.createIdentifier(projectDbUtils.getAllResearchersMap().get(researcherId));
//    }
//
//    public String getAdviser(Integer adviserId) {
//        return idProvider.createIdentifier(projectDbUtils.getAllAdvisersMap().get(adviserId));
//    }

    public void recreate() {
        this.projectDbUtils.recreate();
        this.allGroups = null;
        this.allPersons = null;
    }


    public Optional<Integer> getResearcherId(String researcher) {
        Person p = getAllPersons().get(researcher);
        if ( p == null ) {
            return Optional.empty();
        } else {
            Set<Property> prop = p.getProperties(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.RESEARCHER_ID);
            if ( prop.size() == 0 ) {
                return Optional.empty();
            } else if ( prop.size() > 1 ) {
                throw new RuntimeException("More than one researcher ids found for person: "+p);
            }
            return Optional.of(Integer.parseInt(prop.iterator().next().getValue()));
        }
    }

    public Optional<Integer> getAdviserId(String adviser) {
        Person p = getAllPersons().get(adviser);
        if ( p == null ) {
            return Optional.empty();
        } else {
            Set<Property> prop = p.getProperties(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.ADVISER_ID);
            if ( prop.size() == 0 ) {
                return Optional.empty();
            } else if ( prop.size() > 1 ) {
                throw new RuntimeException("More than one researcher ids found for person: "+p);
            }
            return Optional.of(Integer.parseInt(prop.iterator().next().getValue()));
        }
    }

//    public Multimap<String, Person> getPossibleDuplicates() {
//
//        Collection<Person> researchers = projectDbUtils.getAllResearchers();
//        Collection<Person> advisers = projectDbUtils.getAllAdvisers();
//
//        List<Person> all = Lists.newArrayList(researchers);
//        all.addAll(advisers);
//
//        Multimap<String, Person> duplicates = HashMultimap.create();
//
//
//        for ( Person adv : all ) {
//            String id = projectDbUtils.createIdentifier(adv);
//            Set<Person> allMatches = all.stream().filter(p -> adv.equals(p) && adv != p).collect(Collectors.toSet());
//            if ( allMatches.size() == 0 ) {
//                continue;
//            } else {
//                duplicates.put(id, adv);
//                duplicates.putAll(id, allMatches);
//            }
//
//        }
//
//        return duplicates;
//    }

    private Group createGroup(Project proj) {
        Group g = new Group(proj.getProjectCode());
        g.addMembers(proj.getMembers());
        g.setGroupType("project");
        return g;
    }

    public synchronized Map<String, Group> getAllGroups() {

        if (allGroups == null) {
            Collection<Project> projects = projectDbUtils.getAllProjects().values();
            allGroups = projects.stream().map(proj -> createGroup(proj)).collect(toMap(Group::getGroupName, g -> g));
        }
        return allGroups;
    }

    public synchronized Map<String, Person> getAllPersons() {

        if ( allPersons == null ) {

            Collection<Person> researchers = projectDbUtils.getAllResearchers();
            Collection<Person> advisers = projectDbUtils.getAllAdvisers();

            allPersons = Maps.newHashMap();
            for ( Person p : researchers ) {
                if ( Strings.isNullOrEmpty(p.getAlias()) ) {
                    p = idProvider.createIdentifier(p);
                }
                allPersons.put(p.getAlias(), p);
            }

            for ( Person adv : advisers ) {
                if ( Strings.isNullOrEmpty(adv.getAlias()) ) {
                    adv = idProvider.createIdentifier(adv);
                }
                Person match = allPersons.get(adv.getAlias());
                if ( match == null ) {
                    allPersons.put(adv.getAlias(), adv);
                } else {
                    match.addProperties(adv.getProperties());
                    match.addRoles(adv.getRoles());

                    match.addEmails(adv.getEmails());
                }
            }

            // adding 'nesi' usernames
            for ( Person p : allPersons.values() ) {
                Username un = new Username();
                un.setService(Constants.NESI_SERVICE_NAME);
                un.setUsername(p.getAlias());
                p.addUsername(un);

                // check whether this user is an admin
                if ( admins.keySet().contains(p.getAlias()) ) {
                    myLogger.debug("Creating hub admin user: "+p.getAlias());

                    p.addRole(Constants.HUB_SERVICE_NAME, Constants.HUB_SERVICE_ADMIN_ROLENAME);

                    un = new Username();
                    un.setService(Constants.HUB_SERVICE_NAME);
                    un.setUsername(p.getAlias());
                    p.addUsername(un);

                    Password pw = new Password();
                    pw.setService(Constants.HUB_SERVICE_NAME);
                    pw.setPerson(p.getAlias());
                    pw.setPassword(admins.get(p.getAlias()));
                }

            }
        }

        return allPersons;

    }

}
