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
package pan.auditdb.tables.pojos;

/**
 * This class is generated by jOOQ.
 * <p>
 * InnoDB free: 8458240 kB
 */
@javax.annotation.Generated(value = {"http://www.jooq.org", "3.3.2"},
        comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AuditProjectPrev implements java.io.Serializable {

    private static final long serialVersionUID = -438764012;
    private java.math.BigDecimal coreHours;
    private java.math.BigInteger gridJobs;
    private java.lang.Long jobs;
    private java.lang.String month;
    private java.math.BigDecimal parallelCoreHours;
    private java.math.BigInteger parallelJobs;
    private java.lang.String project;
    private java.math.BigDecimal serialCoreHours;
    private java.math.BigInteger serialJobs;
    private java.math.BigInteger totalCores;
    private java.math.BigDecimal totalGridCoreHours;
    private java.lang.String user;
    private java.math.BigDecimal waitingTime;
    private java.lang.String year;

    public AuditProjectPrev() {
    }

    public AuditProjectPrev(
            java.lang.String project,
            java.lang.String user,
            java.lang.Long jobs,
            java.math.BigInteger gridJobs,
            java.math.BigDecimal totalGridCoreHours,
            java.math.BigInteger totalCores,
            java.math.BigDecimal coreHours,
            java.math.BigDecimal waitingTime,
            java.lang.String month,
            java.lang.String year,
            java.math.BigInteger serialJobs,
            java.math.BigInteger parallelJobs,
            java.math.BigDecimal serialCoreHours,
            java.math.BigDecimal parallelCoreHours
    ) {
        this.project = project;
        this.user = user;
        this.jobs = jobs;
        this.gridJobs = gridJobs;
        this.totalGridCoreHours = totalGridCoreHours;
        this.totalCores = totalCores;
        this.coreHours = coreHours;
        this.waitingTime = waitingTime;
        this.month = month;
        this.year = year;
        this.serialJobs = serialJobs;
        this.parallelJobs = parallelJobs;
        this.serialCoreHours = serialCoreHours;
        this.parallelCoreHours = parallelCoreHours;
    }

    public java.math.BigDecimal getCoreHours() {
        return this.coreHours;
    }

    public java.math.BigInteger getGridJobs() {
        return this.gridJobs;
    }

    public java.lang.Long getJobs() {
        return this.jobs;
    }

    public java.lang.String getMonth() {
        return this.month;
    }

    public java.math.BigDecimal getParallelCoreHours() {
        return this.parallelCoreHours;
    }

    public java.math.BigInteger getParallelJobs() {
        return this.parallelJobs;
    }

    public java.lang.String getProject() {
        return this.project;
    }

    public java.math.BigDecimal getSerialCoreHours() {
        return this.serialCoreHours;
    }

    public java.math.BigInteger getSerialJobs() {
        return this.serialJobs;
    }

    public java.math.BigInteger getTotalCores() {
        return this.totalCores;
    }

    public java.math.BigDecimal getTotalGridCoreHours() {
        return this.totalGridCoreHours;
    }

    public java.lang.String getUser() {
        return this.user;
    }

    public java.math.BigDecimal getWaitingTime() {
        return this.waitingTime;
    }

    public java.lang.String getYear() {
        return this.year;
    }

    public void setCoreHours(java.math.BigDecimal coreHours) {
        this.coreHours = coreHours;
    }

    public void setGridJobs(java.math.BigInteger gridJobs) {
        this.gridJobs = gridJobs;
    }

    public void setJobs(java.lang.Long jobs) {
        this.jobs = jobs;
    }

    public void setMonth(java.lang.String month) {
        this.month = month;
    }

    public void setParallelCoreHours(java.math.BigDecimal parallelCoreHours) {
        this.parallelCoreHours = parallelCoreHours;
    }

    public void setParallelJobs(java.math.BigInteger parallelJobs) {
        this.parallelJobs = parallelJobs;
    }

    public void setProject(java.lang.String project) {
        this.project = project;
    }

    public void setSerialCoreHours(java.math.BigDecimal serialCoreHours) {
        this.serialCoreHours = serialCoreHours;
    }

    public void setSerialJobs(java.math.BigInteger serialJobs) {
        this.serialJobs = serialJobs;
    }

    public void setTotalCores(java.math.BigInteger totalCores) {
        this.totalCores = totalCores;
    }

    public void setTotalGridCoreHours(java.math.BigDecimal totalGridCoreHours) {
        this.totalGridCoreHours = totalGridCoreHours;
    }

    public void setUser(java.lang.String user) {
        this.user = user;
    }

    public void setWaitingTime(java.math.BigDecimal waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setYear(java.lang.String year) {
        this.year = year;
    }
}
