package hub.config;

import org.springframework.context.annotation.Configuration;

/**
 * @author: Markus Binsteiner
 */
@Configuration
public class Queries {

//    @Autowired
//    private Environment env;
//
//    @Bean
//    public UserUtils userUtils() {
//        return new UserUtils();
//    }
//
//    @Bean
//    public UserLookup userLookup() {
//        return new UserLookup();
//    }
//
//    @Bean
//    public PanAuditQuery panAuditQuery() {
//        return new PanAuditQuery();
//    }
//
//    @Bean
//    public SshJobsLister sshJobsLister() throws Exception {
//
//        String ssh_username = env.getProperty("ssh.username", System.getProperty("user.name"));
//        String host_name = env.getRequiredProperty("ssh.host");
//        Integer host_port = env.getProperty("ssh.port", Integer.class, 22);
//
//        String ssh_key = env.getProperty("ssh.key.path", System.getProperty("user.home")+ File.separator+".ssh/id_rsa");
//
//        if ( ! new File(ssh_key).exists() ) {
//            throw new Exception("ssh key does not exist: "+ssh_key);
//        }
//        if ( ! new File(ssh_key).canRead() ) {
//            throw new Exception("ssh key is not readable: "+ssh_key);
//        }
//
//        String known_hosts = env.getProperty("ssh.known_hosts.path", System.getProperty("user.home")+File.separator+".ssh/known_hosts");
//
//        if ( ! new File(known_hosts).exists() ) {
//            throw new Exception("known_hosts file does not exist: "+known_hosts);
//        }
//        if ( ! new File(known_hosts).canRead() ) {
//            throw new Exception("known_hosts file is not readable: "+known_hosts);
//        }
//
//        return new SshJobsLister("uoa", ssh_username, host_name, host_port, ssh_key, known_hosts);
//    }
//
//    @Bean
//    public TreeMap<String, ThingQuery> thingQueries() throws Exception {
//
//        TreeMap<String, ThingQuery> queryMap = Maps.newTreeMap();
//        queryMap.put("details", userLookup());
//        queryMap.put("audit_data", panAuditQuery());
//        queryMap.put("jobs", sshJobsLister());
//        return queryMap;
//    }
}
