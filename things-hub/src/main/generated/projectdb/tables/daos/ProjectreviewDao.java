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
package projectdb.tables.daos;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ProjectreviewDao extends org.jooq.impl.DAOImpl<projectdb.tables.records.ProjectreviewRecord, projectdb.tables.pojos.Projectreview, java.lang.Integer> {

    /**
     * Create a new ProjectreviewDao without any configuration
     */
    public ProjectreviewDao() {
        super(projectdb.tables.Projectreview.PROJECTREVIEW, projectdb.tables.pojos.Projectreview.class);
    }

    /**
     * Create a new ProjectreviewDao with an attached configuration
     */
    public ProjectreviewDao(org.jooq.Configuration configuration) {
        super(projectdb.tables.Projectreview.PROJECTREVIEW, projectdb.tables.pojos.Projectreview.class, configuration);
    }

    /**
     * Fetch records that have <code>adviserId IN (values)</code>
     */
    public java.util.List<projectdb.tables.pojos.Projectreview> fetchByAdviserid(java.lang.Integer... values) {
        return fetch(projectdb.tables.Projectreview.PROJECTREVIEW.ADVISERID, values);
    }

    /**
     * Fetch records that have <code>date IN (values)</code>
     */
    public java.util.List<projectdb.tables.pojos.Projectreview> fetchByDate(java.lang.String... values) {
        return fetch(projectdb.tables.Projectreview.PROJECTREVIEW.DATE, values);
    }

    /**
     * Fetch records that have <code>id IN (values)</code>
     */
    public java.util.List<projectdb.tables.pojos.Projectreview> fetchById(java.lang.Integer... values) {
        return fetch(projectdb.tables.Projectreview.PROJECTREVIEW.ID, values);
    }

    /**
     * Fetch records that have <code>notes IN (values)</code>
     */
    public java.util.List<projectdb.tables.pojos.Projectreview> fetchByNotes(java.lang.String... values) {
        return fetch(projectdb.tables.Projectreview.PROJECTREVIEW.NOTES, values);
    }

    /**
     * Fetch records that have <code>projectId IN (values)</code>
     */
    public java.util.List<projectdb.tables.pojos.Projectreview> fetchByProjectid(java.lang.Integer... values) {
        return fetch(projectdb.tables.Projectreview.PROJECTREVIEW.PROJECTID, values);
    }

    /**
     * Fetch a unique record that has <code>id = value</code>
     */
    public projectdb.tables.pojos.Projectreview fetchOneById(java.lang.Integer value) {
        return fetchOne(projectdb.tables.Projectreview.PROJECTREVIEW.ID, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected java.lang.Integer getId(projectdb.tables.pojos.Projectreview object) {
        return object.getId();
    }
}
