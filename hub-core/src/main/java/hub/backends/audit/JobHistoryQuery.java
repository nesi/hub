package hub.backends.audit;

import com.google.common.collect.ImmutableSet;
import nesi.jobs.Tables;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingQuery;
import things.types.TypeRegistry;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Created by markus on 3/06/14.
 */
public class JobHistoryQuery implements ThingQuery {

    @Resource(name = "dimJobContext")
    private DefaultDSLContext jooq;
    @Autowired
    private ThingControl tc;
    @Autowired
    private TypeRegistry typeRegistry;

    public void getJobs() {

//        System.out.println("XXX");
//
//        SelectConditionStep<Record2<Timestamp, Timestamp>> query = jooq.select(Tables.DIM_JOB.START, Tables.DIM_JOB.FINISH).from(Tables.DIM_JOB).where(Tables.DIM_JOB.USERNAME.eq("mbin029"));
//
//        Result<Record2<Timestamp, Timestamp>> result = query.fetch();
//
//        for ( Record rec : result ) {
//            System.out.println(rec);
//        }
        System.out.println("Start");
        SelectConditionStep<Record> query = jooq.select().from(Tables.MONTHLY_RECORD).where(Tables.MONTHLY_RECORD.USERNAME.eq("mbin029"));

        Result<Record> result = query.fetch();
        for ( Record rec : result ) {
            System.out.println(rec);
        }


    }


    @Override
    public Observable<? extends Thing<?>> execute(String queryName, Observable<? extends Thing<?>> things, Map<String, String> parameters) {
        return null;
    }

    @Override
    public Set<String> getSupportedQueryNames() {
        return ImmutableSet.<String>of("job_history");
    }
}
