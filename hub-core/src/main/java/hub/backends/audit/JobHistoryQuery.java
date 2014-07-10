package hub.backends.audit;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import hub.Constants;
import hub.backends.users.UserManagement;
import hub.backends.users.types.Group;
import hub.backends.users.types.Person;
import hub.backends.users.types.Usage;
import hub.backends.users.types.UsageRecords;
import nesi.jobs.Tables;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.exceptions.QueryRuntimeException;
import things.exceptions.TypeRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;
import things.utils.MatcherUtils;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;
import java.util.stream.Stream;

/**
 * Created by markus on 3/06/14.
 */
public class JobHistoryQuery implements ThingQuery {

    @Resource( name = "dimJobContext" )
    private DefaultDSLContext jooq;
    @Autowired
    private ThingControl tc;
    @Autowired
    private TypeRegistry typeRegistry;
    @Autowired
    private UserManagement usermanagement;

    public UsageRecords getJobHistory(String timeUnit, Thing group_or_person, final Set<String> projectFilters) {

        final UsageRecords result = new UsageRecords();

        if ( typeRegistry.equals(Person.class, group_or_person.getThingType()) ) {
            Person person = ((Person) group_or_person.getValue());
            for ( String username : person.getUsernames().get("University of Auckland") ) {
                result.addUsageRecords(getJobHistoryForUsername(timeUnit, username, projectFilters));
            }
        } else if ( typeRegistry.equals(Group.class, group_or_person.getThingType()) ) {

            if ( projectFilters.size() > 0 ) {
                throw new QueryRuntimeException("Can't filter by project when querying groups.");
            }

            Group group = (Group) group_or_person.getValue();
            Stream<Person> persons = group.getMembers().values().stream().map(alias -> usermanagement.getPerson(alias));

            final Set<String> tempFilters = Sets.newHashSet();
            tempFilters.add(group.getGroupName());


            persons.forEach(p -> {
                        p.getUsernames().get("University of Auckland").stream().forEach(un -> {
                                    UsageRecords ur = getJobHistoryForUsername(timeUnit, un, tempFilters);
                                    result.addUsageRecords(ur);
                                }
                        );
                    }
            );

        } else {
            throw new TypeRuntimeException("Can't query usage for type: " + group_or_person.getThingType());
        }

        return result;
    }

    public UsageRecords getJobHistoryForUsername(String timeUnit, String username, Set<String> projectFilters) {

        Set<Condition> conditions = Sets.newLinkedHashSet();
        SelectJoinStep<Record> joinStep;

        switch ( timeUnit ) {
            case "hour":
                joinStep = jooq.select().from(Tables.HOURLY_RECORD);

                if ( StringUtils.isNotBlank(username) ) {
                    conditions.add(Tables.HOURLY_RECORD.USERNAME.eq(username));
                }

                for ( String project : projectFilters ) {
                    if ( MatcherUtils.isGlob(project) ) {
                        conditions.add(Tables.HOURLY_RECORD.PROJECTCODE.likeRegex(MatcherUtils.convertGlobToRegex(project)));
                    } else {
                        conditions.add(Tables.HOURLY_RECORD.PROJECTCODE.eq(project));
                    }
                }

                break;
            case "day":
                joinStep = jooq.select().from(Tables.DAILY_RECORD);

                if ( StringUtils.isNotBlank(username) ) {
                    conditions.add(Tables.DAILY_RECORD.USERNAME.eq(username));
                }

                for ( String project : projectFilters ) {
                    if ( MatcherUtils.isGlob(project) ) {
                        conditions.add(Tables.DAILY_RECORD.PROJECTCODE.likeRegex(MatcherUtils.convertGlobToRegex(project)));
                    } else {
                        conditions.add(Tables.DAILY_RECORD.PROJECTCODE.eq(project));
                    }
                }

                break;
            case "month":
                joinStep = jooq.select().from(Tables.MONTHLY_RECORD);

                if ( StringUtils.isNotBlank(username) ) {
                    conditions.add(Tables.MONTHLY_RECORD.USERNAME.eq(username));
                }

                for ( String project : projectFilters ) {
                    if ( MatcherUtils.isGlob(project) ) {
                        conditions.add(Tables.MONTHLY_RECORD.PROJECTCODE.likeRegex(MatcherUtils.convertGlobToRegex(project)));
                    } else {
                        conditions.add(Tables.MONTHLY_RECORD.PROJECTCODE.eq(project));
                    }
                }

                break;
            default:
                throw new QueryRuntimeException("Timeunit can only be: 'hour', 'day', 'month'");
        }

        if ( conditions.size() == 0 ) {
            throw new QueryRuntimeException("Can't query job history. Either username, project or both need to be specified.");
        }

        Iterator<Condition> i = conditions.iterator();
        Condition c = i.next();
        SelectConditionStep<Record> query = joinStep.where(c);

        while ( i.hasNext() ) {
            query = query.and(i.next());
        }

        Result<Record> result = query.fetch();

        UsageRecords usageRecords = new UsageRecords();
        usageRecords.setUsername(username);

        for ( Record rec : result ) {

            String projectCode = null;
            Timestamp start = null;
            Long cpusRunning = null;
            Long cpusWaiting = null;
            Long jobsWaiting = null;
            Long usageRunning = null;
            Long usageWaiting = null;
            Long jobsRunning = null;
            switch ( timeUnit ) {
                case "hour":
                    projectCode = rec.getValue(Tables.HOURLY_RECORD.PROJECTCODE);
                    start = rec.getValue(Tables.HOURLY_RECORD.HOUR);
                    cpusRunning = rec.getValue(Tables.HOURLY_RECORD.NUM_CPUS_RUNNING);
                    cpusWaiting = rec.getValue(Tables.HOURLY_RECORD.NUM_CPUS_WAITING);
                    jobsRunning = rec.getValue(Tables.HOURLY_RECORD.NUM_JOBS_RUNNING);
                    jobsWaiting = rec.getValue(Tables.HOURLY_RECORD.NUM_JOBS_WAITING);
                    usageRunning = rec.getValue(Tables.HOURLY_RECORD.USAGE_RUNNING);
                    usageWaiting = rec.getValue(Tables.HOURLY_RECORD.USAGE_WAITING);
                    break;
                case "day":
                    projectCode = rec.getValue(Tables.DAILY_RECORD.PROJECTCODE);
                    start = rec.getValue(Tables.DAILY_RECORD.DAY);
                    cpusRunning = rec.getValue(Tables.DAILY_RECORD.NUM_CPUS_RUNNING);
                    cpusWaiting = rec.getValue(Tables.DAILY_RECORD.NUM_CPUS_WAITING);
                    jobsRunning = rec.getValue(Tables.DAILY_RECORD.NUM_JOBS_RUNNING);
                    jobsWaiting = rec.getValue(Tables.DAILY_RECORD.NUM_JOBS_WAITING);
                    usageRunning = rec.getValue(Tables.DAILY_RECORD.USAGE_RUNNING);
                    usageWaiting = rec.getValue(Tables.DAILY_RECORD.USAGE_WAITING);
                    break;
                case "month":
                    projectCode = rec.getValue(Tables.MONTHLY_RECORD.PROJECTCODE);
                    start = rec.getValue(Tables.MONTHLY_RECORD.MONTH);
                    cpusRunning = rec.getValue(Tables.MONTHLY_RECORD.NUM_CPUS_RUNNING);
                    cpusWaiting = rec.getValue(Tables.MONTHLY_RECORD.NUM_CPUS_WAITING);
                    jobsRunning = rec.getValue(Tables.MONTHLY_RECORD.NUM_JOBS_RUNNING);
                    jobsWaiting = rec.getValue(Tables.MONTHLY_RECORD.NUM_JOBS_WAITING);
                    usageRunning = rec.getValue(Tables.MONTHLY_RECORD.USAGE_RUNNING);
                    usageWaiting = rec.getValue(Tables.MONTHLY_RECORD.USAGE_WAITING);
                    break;
                default:
                    // already checked for that
            }

            Usage stat = new Usage();
            stat.setUsername(username);
            stat.setProjectCode(projectCode);
            stat.setTimeUnit(timeUnit);
            stat.setCpusRunning(cpusRunning);
            stat.setCpusWaiting(cpusWaiting);
            stat.setJobsRunning(jobsRunning);
            stat.setJobsWaiting(jobsWaiting);
            stat.setUsageRunning(usageRunning);
            stat.setUsageWaiting(usageWaiting);
            stat.setStartHour(start.toInstant());

            usageRecords.addRecord(stat);

        }

        return usageRecords;

    }


    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        String timeUnit = parameters.getOrDefault(Constants.UNIT_KEY, "day");

        String project = parameters.get(Constants.PROJECT_FILTER);
        Set<String> projectFilters;
        if ( StringUtils.isBlank(project)) {
            projectFilters = Sets.newLinkedHashSet();
        } else {
            projectFilters = Sets.newLinkedHashSet(Splitter.on(Constants.SPLITTER_CHAR).splitToList(project));
        }

        return things
                .filter(t -> typeRegistry.equals(Person.class, t.getThingType()) || typeRegistry.equals(Group.class, t.getThingType()))
                .map(p -> getJobHistory(timeUnit, p, projectFilters))
                .map(hist -> Thing.createThingPoJo(typeRegistry, hist.getUsername(), hist));
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>of("job_history");
    }
}
