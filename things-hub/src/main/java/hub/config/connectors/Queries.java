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

package hub.config.connectors;

import hub.queries.jobs.JobsQuery;
import hub.queries.users.JobHistoryQuery;
import hub.queries.users.PanAuditQuery;
import hub.queries.users.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import things.config.ThingQueries;

import java.io.File;

/**
 * @author: Markus Binsteiner
 */
@Configuration
public class Queries {

    @Autowired
    private Environment env;

    @Bean
    public JobsQuery jobsQuery() throws Exception {
        String ssh_username = env.getProperty("ssh.username", System.getProperty("user.name"));
        String host_name = env.getRequiredProperty("ssh.host");
        Integer host_port = env.getProperty("ssh.port", Integer.class, 22);

        String ssh_key = env.getProperty("ssh.key.path", System.getProperty("user.home") + File.separator + ".ssh/id_rsa");

        if ( !new File(ssh_key).exists() ) {
            throw new Exception("ssh key does not exist: " + ssh_key);
        }
        if ( !new File(ssh_key).canRead() ) {
            throw new Exception("ssh key is not readable: " + ssh_key);
        }

        String known_hosts = env.getProperty("ssh.known_hosts.path", System.getProperty("user.home") + File.separator + ".ssh/known_hosts");

        if ( !new File(known_hosts).exists() ) {
            throw new Exception("known_hosts file does not exist: " + known_hosts);
        }
        if ( !new File(known_hosts).canRead() ) {
            throw new Exception("known_hosts file is not readable: " + known_hosts);
        }

        return new JobsQuery("uoa", ssh_username, host_name, host_port, ssh_key, known_hosts);

    }

    @Bean
    public JobHistoryQuery jobHistoryQuery() {
        return new JobHistoryQuery();
    }

    @Bean
    public PanAuditQuery panAuditQuery() {
        return new PanAuditQuery();
    }

    @Bean
    public ThingQueries thingQueries() throws Exception {
        ThingQueries tq = new ThingQueries();
        tq.addQuery(userQuery());
        tq.addQuery(jobsQuery());
        tq.addQuery(panAuditQuery());
        tq.addQuery(jobHistoryQuery());
        return tq;
    }

    @Bean
    public UserQuery userQuery() {
        return new UserQuery();
    }
}
