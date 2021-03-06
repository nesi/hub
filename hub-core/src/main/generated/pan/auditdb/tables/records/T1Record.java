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

/**
 * This class is generated by jOOQ
 */
package pan.auditdb.tables.records;

/**
 * This class is generated by jOOQ.
 * <p>
 * InnoDB free: 8458240 kB
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class T1Record extends org.jooq.impl.TableRecordImpl<pan.auditdb.tables.records.T1Record> implements org.jooq.Record2<java.lang.String, java.lang.String> {

    private static final long serialVersionUID = -38745933;

    /**
     * Create a detached T1Record
     */
    public T1Record() {
        super(pan.auditdb.tables.T1.T1);
    }

    /**
     * Create a detached, initialised T1Record
     */
    public T1Record(java.lang.String user, java.lang.String account) {
        super(pan.auditdb.tables.T1.T1);

        setValue(0, user);
        setValue(1, account);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Field<java.lang.String> field1() {
        return pan.auditdb.tables.T1.T1.USER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Field<java.lang.String> field2() {
        return pan.auditdb.tables.T1.T1.ACCOUNT;
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Row2<java.lang.String, java.lang.String> fieldsRow() {
        return (org.jooq.Row2) super.fieldsRow();
    }

    /**
     * Getter for <code>pandora_audit.t1.account</code>.
     */
    public java.lang.String getAccount() {
        return (java.lang.String) getValue(1);
    }

    /**
     * Getter for <code>pandora_audit.t1.user</code>.
     */
    public java.lang.String getUser() {
        return (java.lang.String) getValue(0);
    }

    /**
     * Setter for <code>pandora_audit.t1.account</code>.
     */
    public void setAccount(java.lang.String value) {
        setValue(1, value);
    }

    /**
     * Setter for <code>pandora_audit.t1.user</code>.
     */
    public void setUser(java.lang.String value) {
        setValue(0, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.lang.String value1() {
        return getUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T1Record value1(java.lang.String value) {
        setUser(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.lang.String value2() {
        return getAccount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T1Record value2(java.lang.String value) {
        setAccount(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public T1Record values(java.lang.String value1, java.lang.String value2) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Row2<java.lang.String, java.lang.String> valuesRow() {
        return (org.jooq.Row2) super.valuesRow();
    }
}
