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

package hub.types.dynamic;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import things.model.types.Value;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Created by markus on 28/05/14.
 */
@Value(typeName = "group")
public class Group {

    public static final String DEFAULT_GROUP_TYPE = "general";

    private String groupType = DEFAULT_GROUP_TYPE;

    private final String groupName;

    private Multimap<String, String> members = LinkedHashMultimap.create();

//    public Group() {
//    }


    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

//    public void setGroupName(String groupName) {
//        this.groupName = groupName;
//    }

    public Multimap<String, String> getMembers() {
        return members;
    }

    public void addMember(Set<String> roles, String member) {

        for ( String role : roles ) {
            members.put(role, member);
        }

    }

    public void addMember(String role, String member) {
        members.put(role, member);
    }

    public void addMembers(Multimap<String, String> members) {
        this.members.putAll(members);
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

    @Override
    public String toString() {
        return "Group{" +
                "groupName='" + groupName + '\'' +
                ", members=" + members +
                '}';
    }
}
