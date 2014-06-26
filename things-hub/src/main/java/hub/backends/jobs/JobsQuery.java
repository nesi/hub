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

package hub.backends.jobs;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import hub.backends.jobs.types.JobStatus;
import hub.backends.jobs.types.Jobs;
import hub.backends.users.types.Person;
import hub.backends.users.types.Property;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.exceptions.QueryRuntimeException;
import things.exceptions.TypeRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by markus on 19/05/14.
 */
public class JobsQuery implements ThingQuery {

    private final String host_name;
    private final int host_port;
    private JSch jsch = new JSch();
    private final String site;
    private final String ssh_username;
    @Autowired
    private ThingControl tc;
    @Autowired
    private TypeRegistry typeRegistry;

    public JobsQuery(String sitename, String ssh_username, String host, int port, String ssh_key_file, String known_hosts_file) {

        this.site = sitename;

        if ( MatcherUtils.isGlob(site) ) {
            throw new TypeRuntimeException("Specified site '" + site
                    + "' can't be glob");
        }

        this.host_name = host;
        this.host_port = port;

        this.ssh_username = ssh_username;

        try {
            jsch.setKnownHosts(known_hosts_file);
            jsch.setConfig("PreferredAuthentications", "publickey");

            jsch.addIdentity(ssh_key_file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        switch ( queryName ) {
            case "jobs":
                Observable<Thing<Jobs>> obs = things.filter(t -> typeRegistry.equals(Person.class, t.getThingType())).flatMap(t -> lookupJobs(t));
                return obs;
            default:
                throw new QueryRuntimeException("Query " + queryName + " not supported");
        }
    }

    private Jobs getJobs(String username) {

        final List<JobStatus> result = Lists.newArrayList();

        try {

            final Session session = jsch.getSession(ssh_username,
                    host_name, host_port);
            session.connect();

            List<JobStatus> jobs = LoadLeveler.retrieveJobs(session, username);
            result.addAll(jobs);

            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Jobs jobs = new Jobs(result, username, site);
        return jobs;
    }

    private Observable<Thing<Jobs>> getJobsForPerson(Thing<Person> person) {

        Observable<Property> usernames = Observable.from(person.getValue().getProperties("University of Auckland", "linuxUsername"));

        return usernames.map(u -> getJobs(u.getValue())).map(j -> wrapJobs(person, j));

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("jobs").build();
    }

    private Observable<Thing<Jobs>> lookupJobs(Thing username_or_person) {
        if ( typeRegistry.equals(Person.class, username_or_person.getThingType()) ) {
            return getJobsForPerson(username_or_person);
        } else {
            return Observable.empty();
        }
    }

    private Thing<Jobs> wrapJobs(Thing<Person> person, Jobs jobs) {
        Thing<Jobs> t = new Thing();
        t.setId("jobs:"+person.getId());
        t.setKey(person.getKey());
        t.setValue(jobs);
        return t;
    }


}
