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

package hub.types.persistent;

import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.*;

import java.util.Objects;

/**
 * A role is just an arbitrary String which will be used to determine
 * permissions by an authorization engine.
 */
@UniqueKeyAsChild(unique = false)
@Subordinate(parentClass = Person.class)
@Value(typeName = "role")
@UniqueValueForKey(unique = true)
@UniqueValueForKeyAsChild(unique = true)
@StringConverter(value = RoleStringConverter.class)
public class Role {

    @NotEmpty
    private String role;

    public Role(String role) {
        this.role = role;
    }

    public Role() {
    }

    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Role other = (Role) obj;
            return Objects.equals(getRole(), other.getRole());
        } else {
            return false;
        }
    }

    public String getRole() {
        return role;
    }

    public int hashCode() {
        return Objects.hashCode(getRole());
    }

    public void setRole(String value) {
        this.role = value;
    }

    @Override
    public String toString() {
        return getRole();
    }
}
