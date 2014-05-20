package hub.queries.jobs;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.Session;
import hub.types.dynamic.JobStatus;

import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

/**
 * Created by markus on 19/05/14.
 */
public class LoadLeveler {

        public static JobStatus createJobStatus(String line) {

        String[] tokens = line.split("!");

        if ( tokens.length != 7 ) {
            return null;
        }

        JobStatus js = new JobStatus();
        js.setJobid(tokens[0]);
        js.setJobname(tokens[1]);
        js.setQueueDate(tokens[2]);
        js.setDispatchDate(tokens[3]);
        js.setHost(tokens[4]);
        js.setUsername(tokens[5]);
        js.setJobstatus(tokens[6]);

        return js;

    }

    public static List<JobStatus> retrieveJobs(Session session, String username) throws Exception {

        Channel channel = session.openChannel("exec");
        List<JobStatus> result = Lists.newArrayList();
        try {
            String command = "llq -r %id %jn %dq %dd %h %o %st";
            if ( ! Strings.isNullOrEmpty(username) ) {
                command = command+" -u "+username;
            }
            ((ChannelExec) channel).setCommand(command);
            channel.setInputStream(null);

            ((ChannelExec) channel).setErrStream(System.err);

            InputStream in = channel.getInputStream();
            channel.connect();
            Scanner sc = new Scanner(in);

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                JobStatus js = createJobStatus(line);
                if (js != null) {
                    result.add(js);
                }
            }
        } finally {
            channel.disconnect();
        }

        return result;
    }
}
