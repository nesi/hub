/*
 * Things
 *
 * Copyright (c) 2014, Markus Binsteiner. All rights reserved.
 *
 * Things is free software; you can redistribute it and/or
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

package things.types;

import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Created by markus on 18/05/14.
 */
@Value(typeName = "noRestrictionsType")
@UniqueKey(unique = false)
@Entity
public class NoRestrictionsType {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String property1;
    private String property2;

    public boolean equals(Object o) {
        if ( !(o instanceof NoRestrictionsType) ) {
            return false;
        }
        NoRestrictionsType other = (NoRestrictionsType) o;
        return Objects.equals(getProperty1(), other.getProperty1()) && Objects.equals(getProperty2(), other.getProperty2());
    }

    public String getId() {
        return id;
    }

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    public int hashCode() {
        return Objects.hash(getProperty1(), getProperty2());
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }
}
