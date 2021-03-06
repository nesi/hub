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
package pan.auditdb.tables;

/**
 * This class is generated by jOOQ.
 * <p>
 * InnoDB free: 8458240 kB
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class T1 extends org.jooq.impl.TableImpl<pan.auditdb.tables.records.T1Record> {

    /**
     * The singleton instance of <code>pandora_audit.t1</code>
     */
    public static final pan.auditdb.tables.T1 T1 = new pan.auditdb.tables.T1();
    private static final long serialVersionUID = 1337825043;
    /**
     * The column <code>pandora_audit.t1.account</code>.
     */
    public final org.jooq.TableField<pan.auditdb.tables.records.T1Record, java.lang.String> ACCOUNT = createField("account", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "");
    /**
     * The column <code>pandora_audit.t1.user</code>.
     */
    public final org.jooq.TableField<pan.auditdb.tables.records.T1Record, java.lang.String> USER = createField("user", org.jooq.impl.SQLDataType.VARCHAR.length(32), this, "");

    /**
     * Create a <code>pandora_audit.t1</code> table reference
     */
    public T1() {
        this("t1", null);
    }

    /**
     * Create an aliased <code>pandora_audit.t1</code> table reference
     */
    public T1(java.lang.String alias) {
        this(alias, pan.auditdb.tables.T1.T1);
    }

    private T1(java.lang.String alias, org.jooq.Table<pan.auditdb.tables.records.T1Record> aliased) {
        this(alias, aliased, null);
    }

    private T1(java.lang.String alias, org.jooq.Table<pan.auditdb.tables.records.T1Record> aliased, org.jooq.Field<?>[] parameters) {
        super(alias, pan.auditdb.PandoraAudit.PANDORA_AUDIT, aliased, parameters, "InnoDB free: 8458240 kB");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public pan.auditdb.tables.T1 as(java.lang.String alias) {
        return new pan.auditdb.tables.T1(alias, this);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public java.lang.Class<pan.auditdb.tables.records.T1Record> getRecordType() {
        return pan.auditdb.tables.records.T1Record.class;
    }

    /**
     * Rename this table
     */
    public pan.auditdb.tables.T1 rename(java.lang.String name) {
        return new pan.auditdb.tables.T1(name, null);
    }
}
