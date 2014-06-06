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
package projectdb.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ResearcherStatusRecord extends org.jooq.impl.UpdatableRecordImpl<projectdb.tables.records.ResearcherStatusRecord> implements org.jooq.Record2<java.lang.Integer, java.lang.String> {

    private static final long serialVersionUID = -954826286;

    /**
     * Create a detached ResearcherStatusRecord
     */
    public ResearcherStatusRecord() {
        super(projectdb.tables.ResearcherStatus.RESEARCHER_STATUS);
    }

    /**
     * Create a detached, initialised ResearcherStatusRecord
     */
    public ResearcherStatusRecord(java.lang.Integer id, java.lang.String name) {
        super(projectdb.tables.ResearcherStatus.RESEARCHER_STATUS);

        setValue(0, id);
        setValue(1, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Field<java.lang.Integer> field1() {
        return projectdb.tables.ResearcherStatus.RESEARCHER_STATUS.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Field<java.lang.String> field2() {
        return projectdb.tables.ResearcherStatus.RESEARCHER_STATUS.NAME;
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Row2<java.lang.Integer, java.lang.String> fieldsRow() {
        return (org.jooq.Row2) super.fieldsRow();
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>projectdb.researcher_status.id</code>.
     */
    public java.lang.Integer getId() {
        return (java.lang.Integer) getValue(0);
    }

    /**
     * Getter for <code>projectdb.researcher_status.name</code>.
     */
    public java.lang.String getName() {
        return (java.lang.String) getValue(1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Record1<java.lang.Integer> key() {
        return (org.jooq.Record1) super.key();
    }

    /**
     * Setter for <code>projectdb.researcher_status.id</code>.
     */
    public void setId(java.lang.Integer value) {
        setValue(0, value);
    }

    /**
     * Setter for <code>projectdb.researcher_status.name</code>.
     */
    public void setName(java.lang.String value) {
        setValue(1, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.lang.Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearcherStatusRecord value1(java.lang.Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.lang.String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearcherStatusRecord value2(java.lang.String value) {
        setName(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public ResearcherStatusRecord values(java.lang.Integer value1, java.lang.String value2) {
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Row2<java.lang.Integer, java.lang.String> valuesRow() {
        return (org.jooq.Row2) super.valuesRow();
    }
}
