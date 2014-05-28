package hub.queries.users;

import com.google.common.collect.ImmutableSet;
import hub.actions.UserUtils;
import hub.types.dynamic.AuditRecord;
import hub.types.persistent.Person;
import hub.types.persistent.Username;
import org.jooq.Record2;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import pan.auditdb.Tables;
import rx.Observable;
import things.exceptions.QueryRuntimeException;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 19/05/14.
 */
public class PanAuditQuery implements ThingQuery {

    @Resource(name = "panAuditContext")
    private DefaultDSLContext jooq;
    @Autowired
    private ThingControl tc;
    @Autowired
    private TypeRegistry typeRegistry;
    @Autowired
    private UserUtils utils;

    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {

        switch ( queryName ) {
            case "audit_data":
                Observable<Thing<AuditRecord>> obs = things.flatMap(t -> lookupAudit(t));
                return obs;
            default:
                throw new QueryRuntimeException("Query " + queryName + " not supported");
        }

    }

    private Observable<Thing<AuditRecord>> getAuditForPerson(Thing<Person> person) {

        return utils.convertToUsername(person).map(u -> getAuditRecord(u)).map(j -> wrapJobs(person, j));

    }

    private Observable<Thing<AuditRecord>> getAuditForUsername(Thing<Username> username) {

        Thing<Person> p = utils.convertToPerson(username).toBlockingObservable().single();

        AuditRecord jobs = getAuditRecord(username);
        return Observable.just(wrapJobs(p, jobs));

    }

    private AuditRecord getAuditRecord(Thing<Username> un) {

        SelectConditionStep<Record2<Integer, BigDecimal>> query = jooq
                .select(Tables.AUDIT_USER.DONE, Tables.AUDIT_USER.CORE_HOURS)
                .from(Tables.AUDIT_USER)
                .where(Tables.AUDIT_USER.USER.equal(tc.getValue(un).getUsername()));

        Result<Record2<Integer, BigDecimal>> result = query.fetch();

        final AuditRecord ar = new AuditRecord(un.getValue().getUsername());

        result.stream()
                .forEach(r -> ar.addJob(r.getValue(Tables.AUDIT_USER.DONE).toString(), r.getValue(Tables.AUDIT_USER.CORE_HOURS)));

        return ar;
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>builder().add("audit_data").build();
    }


    private Observable<Thing<AuditRecord>> lookupAudit(Thing username_or_person) {
        if ( typeRegistry.equals(Person.class, username_or_person.getThingType()) ) {
            return getAuditForPerson(username_or_person);
        } else {
            return getAuditForUsername(username_or_person);
        }
    }

    private Thing<AuditRecord> wrapJobs(Thing<Person> person, AuditRecord auditRecord) {
        Thing<AuditRecord> t = new Thing();
        t.setId("audit_data:person:"+person.getId());
        t.setKey(person.getKey());
        t.setValue(auditRecord);
        return t;
    }
}
