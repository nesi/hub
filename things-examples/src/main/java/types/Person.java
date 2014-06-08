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

import org.apache.bval.constraints.Email;
import org.apache.bval.constraints.NotEmpty;
import org.hibernate.annotations.GenericGenerator;
import things.model.types.Value;
import things.model.types.attributes.UniqueKey;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 12/04/14
 * Time: 7:38 PM
 */
@UniqueKey(unique = true)
@Value(typeName = "person")
@Entity
public class Person {

    @Email
    private String email;
    @NotEmpty
    private String firstName;
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private Boolean isActive = false;
    @NotEmpty
    private String lastName;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Person() {

    }

    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;

        if ( getClass().equals(obj.getClass()) ) {
            final Person other = (Person) obj;
            return Objects.equals(getFirstName(), other.getFirstName()) && Objects.equals(getLastName(), other.getLastName())
                    && Objects.equals(getEmail(), other.getEmail());
        } else {
            return false;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public String getLastName() {
        return lastName;
    }

    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
//                ", id='" + id + '\'' +
                '}';
    }
}
