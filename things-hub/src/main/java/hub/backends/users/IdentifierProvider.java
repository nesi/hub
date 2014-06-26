package hub.backends.users;

import hub.backends.users.repositories.IdentityRepository;
import hub.backends.users.types.Identity;
import hub.backends.users.types.Person;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

/**
 * Created by markus on 23/06/14.
 */
public class IdentifierProvider {

    @Inject
    private IdentityRepository idRepository;

    @Transactional(readOnly = false)
    public Person createIdentifier(Person p) {

        Optional<String> advId = p.getPropertyValue(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.ADVISER_ID);
        Optional<String> resId = p.getPropertyValue(ProjectDbUtils.PROJECT_DB_SERVICENAME, ProjectDbUtils.RESEARCHER_ID);

        List<Identity> resIds;
        if ( resId.isPresent() ) {
            resIds = idRepository.findByResearcherId(Integer.parseInt(resId.get()));
            if ( resIds.size() > 1 ) {
                throw new RuntimeException("Multiple persons found for researcher id: "+resId.get());
            } else if ( resIds.size() == 1 ) {
                p.setAlias(resIds.get(0).getAlias());
                return p;
            }
        }

        List<Identity> advIds;
        if ( advId.isPresent() ) {
            advIds = idRepository.findByAdviserId(Integer.parseInt(advId.get()));
            if ( advIds.size() > 1 ) {
                throw new RuntimeException("Multiple persons found for adviser id: "+advId.get());
            } else if ( advIds.size() == 1 ) {
                p.setAlias(advIds.get(0).getAlias());
                return p;
            }
        }

        String id = (p.getFirst_name()+"_"+p.getLast_name()).toLowerCase();
        List<Identity> ids = idRepository.findByAlias(id);

        if ( ids.size() == 0 ) {
            // not stored yet
            p.setAlias(id);
            Identity newId = new Identity(id);
            if ( resId.isPresent() ) {
                newId.setResearcherId(Integer.parseInt(resId.get()));
            }
            if ( advId.isPresent() ) {
                newId.setAdviserId(Integer.parseInt(advId.get()));
            }
//            newId.setEmails(p.getEmails());
//            newId.setFirstName(p.getFirst_name());
//            newId.setLastName(p.getLast_name());
//            newId.setMiddleNames(p.getMiddle_names());

            idRepository.saveAndFlush(newId);

        } else if ( ids.size() == 1 ) {
            p.setAlias(id);
        } else {
            throw new RuntimeException("More than one Identities found for: "+id);
        }

        return p;
    }
}
