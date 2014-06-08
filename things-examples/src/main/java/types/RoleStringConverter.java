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

import things.types.SingleStringConverter;

/**
 * Created by markus on 20/05/14.
 */
public class RoleStringConverter implements SingleStringConverter<Role> {
    @Override
    public Role convertFromString(String valueString) {
        return new Role(valueString);
    }

    @Override
    public String convertToString(Role value) {
        return value.getRole();
    }
}
