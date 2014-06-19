package hub.actions;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import hub.types.dynamic.Group;
import hub.types.dynamic.Person;
import hub.types.dynamic.PersonProperty;
import hub.types.dynamic.Project;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

/**
 * Created by markus on 18/06/14.
 */
public class UserManagement {

    @Autowired
    private ProjectDbUtils projectDbUtils;

    private Map<String, Person> allPersons;

    private Map<String, Group> allGroups;

    public String getGroup(Integer projectId) {
        return projectDbUtils.getProjectIdMap().get(projectId);
    }

    public Integer getProjectId(String projectCode) {
        return projectDbUtils.getProjectIdMap().inverse().get(projectCode);
    }

    public String getResearcher(Integer researcherId) {
        return createIdentifier(projectDbUtils.getAllResearchersMap().get(researcherId));
    }

    public String getAdviser(Integer adviserId) {
        return createIdentifier(projectDbUtils.getAllAdvisersMap().get(adviserId));
    }

    public Optional<Integer> getResearcherId(String researcher) {
        Person p = getAllPersons().get(researcher);
        if ( p == null ) {
            return Optional.empty();
        } else {
            Set<PersonProperty> prop = p.getProperties(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.RESEARCHER_ID);
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
            Set<PersonProperty> prop = p.getProperties(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.ADVISER_ID);
            if ( prop.size() == 0 ) {
                return Optional.empty();
            } else if ( prop.size() > 1 ) {
                throw new RuntimeException("More than one researcher ids found for person: "+p);
            }
            return Optional.of(Integer.parseInt(prop.iterator().next().getValue()));
        }
    }

    public Multimap<String, Person> getPossibleDuplicates() {

        Collection<Person> researchers = projectDbUtils.getAllResearchers();
        Collection<Person> advisers = projectDbUtils.getAllAdvisers();

        List<Person> all = Lists.newArrayList(researchers);
        all.addAll(advisers);

        Multimap<String, Person> duplicates = HashMultimap.create();


        for ( Person adv : all ) {
            String id = projectDbUtils.createIdentifier(adv);
            Set<Person> allMatches = all.stream().filter(p -> adv.equals(p) && adv != p).collect(Collectors.toSet());
            if ( allMatches.size() == 0 ) {
                continue;
            } else {
                duplicates.put(id, adv);
                duplicates.putAll(id, allMatches);
            }

        }

        return duplicates;
    }

    public String createIdentifier(Person p) {
        return projectDbUtils.createIdentifier(p);
    }

    private Group createGroup(Project proj) {
        Group g = new Group(proj.getProjectCode());
        g.addMembers(proj.getMembers());
        return g;
    }

    public Map<String, Group> getAllGroups() {

        if (allGroups == null) {
            Collection<Project> projects = projectDbUtils.getAllProjects().values();
            allGroups = projects.stream().map(proj -> createGroup(proj)).collect(toMap(Group::getGroupName, g -> g));
        }
        return allGroups;
    }

    public Map<String, Person> getAllPersons() {

        if ( allPersons == null ) {

            Collection<Person> researchers = projectDbUtils.getAllResearchers();
            Collection<Person> advisers = projectDbUtils.getAllAdvisers();

            allPersons = Maps.newHashMap();
            for ( Person p : researchers ) {
                allPersons.put(projectDbUtils.createIdentifier(p), p);
            }

            for ( Person adv : advisers ) {
                Person match = allPersons.get(projectDbUtils.createIdentifier(adv));
                if ( match == null ) {
                    allPersons.put(projectDbUtils.createIdentifier(adv), adv);
                } else {
                    match.addProperties(adv.getProperties());
                    match.addRoles(adv.getRoles());

                    match.addEmails(adv.getEmails());
                }
            }
        }

        return allPersons;

    }

}
