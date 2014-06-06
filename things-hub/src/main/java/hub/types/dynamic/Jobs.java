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

package hub.types.dynamic;

import things.model.types.Value;

import java.time.Instant;
import java.util.List;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 28/03/14
 * Time: 2:32 PM
 */
@Value(typeName = "jobs")
public class Jobs {


    private List<JobStatus> jobs;
    private String site;
    private Instant timestamp;
    private String username;

    public Jobs(List<JobStatus> jobs, String username, String site) {
        this.jobs = jobs;
        timestamp = Instant.now();
        this.username = username;
        this.site = site;
    }

    public List<JobStatus> getJobs() {
        return jobs;
    }

    public String getSite() {
        return site;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setDate(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setJobs(List<JobStatus> jobs) {
        this.jobs = jobs;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
