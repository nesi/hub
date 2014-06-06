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

package types;


import org.apache.bval.constraints.NotEmpty;
import things.model.types.Value;
import things.model.types.attributes.StringConverter;
import things.model.types.attributes.Subordinate;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/04/14
 * Time: 7:39 PM
 */
@Subordinate( parentClass = Person.class )
@Value( typeName = "role" )
@StringConverter( value = RoleStringConverter.class )
public class Role {

    public String id;

    @NotEmpty
    private String role;

    private Role() {

    }

    public Role(String role) {
        setRole(role);
    }

    public String getRole() {
        return role;
    }

    public String getValue() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setValue(String value) {
        setRole(role);
    }

    @Override
    public String toString() {
        return "Role{" +
                "role='" + role + '\'' +
                '}';
    }
}
