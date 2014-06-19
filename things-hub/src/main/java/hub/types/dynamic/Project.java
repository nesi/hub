package hub.types.dynamic;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import things.model.types.Value;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Created by markus on 16/06/14.
 */
@Value(typeName = "project")
public class Project {

    private String projectCode;

    private String projectType;
    private String name;
    private String description;
    private String hostInstitution;

    private LocalDateTime lastModified;

    private Map<String, String> properties = Maps.newHashMap();

    private Multimap<String, String> members = HashMultimap.create();

    public void addMembers(Multimap<String, String> members) {
        this.members.putAll(members);
    }

    public void addMember(String role, String member) {
        members.put(role, member);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Multimap<String, String> getMembers() {
        return members;
    }

    public void setMembers(Multimap<String, String> members) {
        this.members = members;
    }

    public void addProperty(String key, String value) {
        properties.put(key, value);
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHostInstitution() {
        return hostInstitution;
    }

    public void setHostInstitution(String hostInstitution) {
        this.hostInstitution = hostInstitution;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }
}
