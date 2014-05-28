package hub.queries.jobs;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import hub.actions.UserUtils;
import hub.types.dynamic.JobStatus;
import hub.types.dynamic.Jobs;
import hub.types.persistent.Person;
import hub.types.persistent.Username;
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
    @Autowired
    private UserUtils utils;

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
                Observable<Thing<Jobs>> obs = things.flatMap(t -> lookupJobs(t));
                return obs;
            default:
                throw new QueryRuntimeException("Query " + queryName + " not supported");
        }
    }

    private Jobs getJobs(Thing<Username> username) {

        final List<JobStatus> result = Lists.newArrayList();

        try {

            final Session session = jsch.getSession(ssh_username,
                    host_name, host_port);
            session.connect();

            List<JobStatus> jobs = LoadLeveler.retrieveJobs(session, username.getValue().getUsername());
            result.addAll(jobs);

            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Jobs jobs = new Jobs(result, username.getValue().getUsername(), site);
        return jobs;
    }

    private Observable<Thing<Jobs>> getJobsForPerson(Thing<Person> person) {

        return utils.convertToUsername(person).map(u -> getJobs(u)).map(j -> wrapJobs(person, j));

    }

    private Observable<Thing<Jobs>> getJobsForUsername(Thing<Username> username) {

        Thing<Person> p = utils.convertToPerson(username).toBlockingObservable().single();

        Jobs jobs = getJobs(username);
        return Observable.just(wrapJobs(p, jobs));

    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("jobs").build();
    }

    private Observable<Thing<Jobs>> lookupJobs(Thing username_or_person) {
        if ( typeRegistry.equals(Person.class, username_or_person.getThingType()) ) {
            return getJobsForPerson(username_or_person);
        } else {
            return getJobsForUsername(username_or_person);
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
