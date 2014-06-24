/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */

package hub.backends.users.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Strings;
import com.google.common.collect.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.utils.MatcherUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A Person is an object that holds person details, like first, middle and last name, emails.
 */
@Value(typeName = "person")
public class Person implements Comparable<Person> {


    @Email
    private Set<String> emails = Sets.newTreeSet();
    @NotEmpty
    private String first_name;
    @JsonIgnore
    String id;
    private Instant lastModified;
    @NotEmpty
    private String last_name;
    private String middle_names = "";
    private String preferred_name;

    private String uniqueUsername;

    public String getUniqueUsername() {
        return uniqueUsername;
    }

    public void setUniqueUsername(String uniqueUsername) {
        this.uniqueUsername = uniqueUsername;
    }

    private Set<PersonProperty> properties = Sets.newTreeSet();
    private SetMultimap<String, String> roles = TreeMultimap.create();

    public Person(String first_name, String last_name) {
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Person() {

    }

    public boolean matchesProperty(PersonProperty prop) {
        Set<PersonProperty> value = getProperties(prop.getService(), prop.getKey());
        if ( value.size() > 0 ) {
            String propValue = prop.getValue();
            if ( MatcherUtils.isGlob(propValue)) {
                return value.stream().anyMatch(p -> MatcherUtils.wildCardMatch(p.getValue(), propValue));
            } else {
                return value.stream().anyMatch(p -> p.getValue().equals(prop.getValue()));
            }
        } else {
            return false;
        }
    }

    public synchronized void addEmail(String email) {
        getEmails().add(email);
    }

    public synchronized void addEmails(Collection<String> emails) {
        getEmails().addAll(emails);
    }

    public void addProperties(Collection<PersonProperty> props) {
        properties.addAll(props);
    }

    public void addRoles(Multimap<String, String> roles) {
        this.roles.putAll(roles);
    }

    public void addProperty(PersonProperty value) {
        properties.add(value);
    }

    public void addRole(String group, String role) {
        roles.put(group, role);
    }

    @Override
    public int compareTo(Person o2) {
        return ComparisonChain.start()
                .compare(getLast_name(), o2.getLast_name())
                .compare(getFirst_name(), o2.getFirst_name())
                .result();
    }

    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Person other = (Person) obj;
            return Objects.equals(getFirst_name(), other.getFirst_name()) && Objects.equals(getLast_name(), other.getLast_name());
        } else {
            return false;
        }
    }

    public Set<String> getEmails() {
        return emails;
    }

    public String getFirst_name() {

        return first_name;
    }

    public String getId() {

        return id;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getMiddle_names() {
        return middle_names;
    }

    public String getPreferred_name() {
        return preferred_name;
    }

    public Set<PersonProperty> getProperties() {
        return properties;
    }

    public Set<PersonProperty> getPropertiesForKey(String key) {
        return properties.stream().filter(p -> p.getKey().equals(key)).collect(Collectors.toSet());
    }

    public Optional<PersonProperty> getProperty(String servicename, String key) {
        Set<PersonProperty> props = getProperties(servicename, key);
        if ( props.size() == 0 ) {
            return Optional.empty();
        } else if ( props.size() > 1 ) {
            throw new RuntimeException("More than one property found for service "+servicename+" and key "+key);
        } else {
            return Optional.of(props.iterator().next());
        }
    }

    public Optional<String> getPropertyValue(String service, String key) {
        Optional<PersonProperty> p = getProperty(service, key);

        if ( p.isPresent() ) {
            return Optional.of(p.get().getValue());
        } else {
            return Optional.empty();
        }
    }

    public Set<String> getPropertyValues(String service, String key) {
        return properties.stream().filter(p -> p.getService().equals(service) && p.getKey().equals(key) ).map(p -> p.getValue()).collect(Collectors.toSet());
    }

    public Set<PersonProperty> getProperties(String service) {
        return properties.stream().filter(p -> p.getService().equals(service)).collect(Collectors.toSet());
    }

    public Set<PersonProperty> getProperties(String service, String key) {
        return properties.stream().filter(p -> p.getService().equals(service) && p.getKey().equals(key) ).collect(Collectors.toSet());
    }

    public Multimap<String, String> getRoles() {
        return roles;
    }

    public int hashCode() {
        return Objects.hash(getFirst_name(), getLast_name());
    }

    /**
     * Convenience method, outputs the persons' name.
     * <p>
     * Format depends on whether it contains a middle name or not.
     *
     * @return the name string
     */
    public String nameToString() {
        if ( Strings.isNullOrEmpty(middle_names) ) {
            return first_name + " " + last_name;
        } else {
            return first_name + " " + middle_names + " " + last_name;
        }
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setMiddle_names(String middle_names) {
        this.middle_names = middle_names;
    }

    public void setPreferred_name(String preferred_name) {
        this.preferred_name = preferred_name;
    }

    public void setProperties(Set<PersonProperty> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Person{" +
                "emails=" + emails +
                ", first_name='" + first_name + '\'' +
                ", lastModified=" + lastModified +
                ", roles=" + roles +
                ", preferred_name='" + preferred_name + '\'' +
                ", properties=" + properties +
                ", id='" + id + '\'' +
                ", last_name='" + last_name + '\'' +
                ", middle_names='" + middle_names + '\'' +
                '}';
    }
}
