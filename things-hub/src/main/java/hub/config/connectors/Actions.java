package hub.config.connectors;

import hub.actions.ClearMongoDatabase;
import hub.actions.ImportRoleAndGroupAction;
import hub.actions.LdapImporter;
import hub.config.mongo.HubConfigMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import things.config.ThingActions;

/**
 * @author: Markus Binsteiner
 */
@Configuration
@Import(HubConfigMongo.class)
public class Actions {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Bean
    public ClearMongoDatabase clearMongoDatabase() {
        return new ClearMongoDatabase();
    }

    @Bean
    public ImportRoleAndGroupAction importRoleAndGroupAction() throws Exception {
        ImportRoleAndGroupAction i = new ImportRoleAndGroupAction();
        return i;
    }

    @Bean
    public LdapImporter ldapImporter() throws Exception {
        LdapImporter ldapImporter = new LdapImporter();
        return ldapImporter;
    }

    @Bean
    ThingActions thingActions() throws Exception {
        ThingActions ta = new ThingActions();
        ta.addAction("import_uoa_ldap", ldapImporter());
        ta.addAction("import_projectdb", importRoleAndGroupAction());
        ta.addAction("clear_mongo", clearMongoDatabase());
        return ta;
    }

}
