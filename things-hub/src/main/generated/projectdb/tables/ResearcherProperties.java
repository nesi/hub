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
package projectdb.tables;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ResearcherProperties extends org.jooq.impl.TableImpl<projectdb.tables.records.ResearcherPropertiesRecord> {

    /**
     * The singleton instance of <code>projectdb.researcher_properties</code>
     */
    public static final projectdb.tables.ResearcherProperties RESEARCHER_PROPERTIES = new projectdb.tables.ResearcherProperties();
    private static final long serialVersionUID = 1674945835;
    /**
     * The column <code>projectdb.researcher_properties.id</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");
    /**
     * The column <code>projectdb.researcher_properties.propname</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.String> PROPNAME = createField("propname", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false).defaulted(true), this, "");
    /**
     * The column <code>projectdb.researcher_properties.propvalue</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.String> PROPVALUE = createField("propvalue", org.jooq.impl.SQLDataType.VARCHAR.length(255), this, "");
    /**
     * The column <code>projectdb.researcher_properties.researcherId</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.Integer> RESEARCHERID = createField("researcherId", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");
    /**
     * The column <code>projectdb.researcher_properties.siteId</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.Integer> SITEID = createField("siteId", org.jooq.impl.SQLDataType.INTEGER.nullable(false).defaulted(true), this, "");
    /**
     * The column <code>projectdb.researcher_properties.timestamp</code>.
     */
    public final org.jooq.TableField<projectdb.tables.records.ResearcherPropertiesRecord, java.sql.Timestamp> TIMESTAMP = createField("timestamp", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

    /**
     * Create a <code>projectdb.researcher_properties</code> table reference
     */
    public ResearcherProperties() {
        this("researcher_properties", null);
    }

    /**
     * Create an aliased <code>projectdb.researcher_properties</code> table reference
     */
    public ResearcherProperties(java.lang.String alias) {
        this(alias, projectdb.tables.ResearcherProperties.RESEARCHER_PROPERTIES);
    }

    private ResearcherProperties(java.lang.String alias, org.jooq.Table<projectdb.tables.records.ResearcherPropertiesRecord> aliased) {
        this(alias, aliased, null);
    }

    private ResearcherProperties(java.lang.String alias, org.jooq.Table<projectdb.tables.records.ResearcherPropertiesRecord> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, projectdb.Projectdb.PROJECTDB, aliased, parameters, "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public projectdb.tables.ResearcherProperties as(java.lang.String alias) {
        return new projectdb.tables.ResearcherProperties(alias, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.Identity<projectdb.tables.records.ResearcherPropertiesRecord, java.lang.Integer> getIdentity() {
        return projectdb.Keys.IDENTITY_RESEARCHER_PROPERTIES;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.List<org.jooq.UniqueKey<projectdb.tables.records.ResearcherPropertiesRecord>> getKeys() {
        return java.util.Arrays.<org.jooq.UniqueKey<projectdb.tables.records.ResearcherPropertiesRecord>>asList(projectdb.Keys.KEY_RESEARCHER_PROPERTIES_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public org.jooq.UniqueKey<projectdb.tables.records.ResearcherPropertiesRecord> getPrimaryKey() {
        return projectdb.Keys.KEY_RESEARCHER_PROPERTIES_PRIMARY;
    }

    /**
     * The class holding records for this type
     */
    @Override
    public java.lang.Class<projectdb.tables.records.ResearcherPropertiesRecord> getRecordType() {
        return projectdb.tables.records.ResearcherPropertiesRecord.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public java.util.List<org.jooq.ForeignKey<projectdb.tables.records.ResearcherPropertiesRecord, ?>> getReferences() {
        return java.util.Arrays.<org.jooq.ForeignKey<projectdb.tables.records.ResearcherPropertiesRecord, ?>>asList(projectdb.Keys.RESEARCHER_PROPERTIES_IBFK_2, projectdb.Keys.RESEARCHER_PROPERTIES_IBFK_1);
    }

    /**
     * Rename this table
     */
    public projectdb.tables.ResearcherProperties rename(java.lang.String name) {
        return new projectdb.tables.ResearcherProperties(name, null);
    }
}
