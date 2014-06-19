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

import hub.types.dynamic.Person;
import org.hibernate.validator.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.StringConverter;
import things.model.types.attributes.Subordinate;
import things.model.types.attributes.UniqueKeyAsChild;
import things.model.types.attributes.UniqueValueForKey;

import java.util.Objects;

/**
 * A username is the identifying token for a user on a system.
 * <p>
 * It is linked to the {@link hub.types.dynamic.Person} object. When adding a Username to a Person, the key that is used is
 * the name of the system where the username is used.
 * <p>
 * A {@link hub.types.dynamic.Person}} object can have multiple Usernames with the same keys, since a user can have multiple
 * accounts on a system.
 */
@UniqueKeyAsChild(unique = false)
@Subordinate(parentClass = Person.class)
@Value(typeName = "username")
@StringConverter(value = UsernameStringConverter.class)
@UniqueValueForKey(unique = true)
public class Username {

    @NotEmpty
    private String username;

    public Username(String username) {
        this.username = username;
    }

    public Username() {
    }

    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Username other = (Username) obj;
            return Objects.equals(getUsername(), other.getUsername());
        } else {
            return false;
        }
    }

    public String getUsername() {
        return username;
    }

    public int hashCode() {
        return Objects.hashCode(getUsername());
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return getUsername();
    }

}
