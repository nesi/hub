package hub.actions;


import org.springframework.beans.factory.annotation.Autowired;

import rx.Observable;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.TypeRegistry;
import hub.types.dynamic.User;
import hub.types.persistent.Person;
import hub.types.persistent.Username;

/**
 * Utilities to help manage, convert and lookup users, usernames and persons.
 *
 * @author: Markus Binsteiner
 */
public class UserUtils {

    @Autowired
    private ThingControl tc;

    /**
     * Converts a {@link Username} or {@link Person} thing into a list of {@link Username} things.
     *
     * Since a user can have multiple accounts for the same system (= key), multiple outputs are possible for
     * a single input.
     *
     * @param usernameOrPerson a thing of type Username or Person
     * @return list of all usernames
     */
    public Observable<Thing<Username>> lookupUsernames(Observable<? extends Thing<?>> usernameOrPerson) {

        return usernameOrPerson
                .filter(t -> TypeRegistry.equalsType(t.getThingType(), Person.class) || TypeRegistry.equalsType(t.getThingType(), Username.class))
                .flatMap(t -> convertToUsername(t));
    }

    /**
     * Converts a {@link Username} or {@link Person} thing into a {@link Person} thing.
     *
     * In case of a Person input, it will just get passwed through. In case of a Username input,
     * the database will be searched for the specified username & key and the parent Person thing
     * will be returned.
     *
     * If more than one results are found, a {@link things.exceptions.ThingRuntimeException} will be thrown.
     *
     * @param usernameOrPerson a thing of type Username or Person
     * @return the Person thing
     */
    public Observable<Thing<Person>> convertToPerson(Thing<?> usernameOrPerson) {

        if ( TypeRegistry.equalsType(Person.class, usernameOrPerson.getThingType())) {
            return Observable.just((Thing<Person>) usernameOrPerson);
        } else {
            Observable<? extends Thing<?>> obs = tc.observeParents(usernameOrPerson);
            return tc.filterThingsOfType(Person.class, obs);
        }

    }

    public Observable<Thing<Username>> convertToUsername(Thing<?> usernameOrPerson) {

        if ( TypeRegistry.equalsType(Username.class, usernameOrPerson.getThingType())) {
            return Observable.just((Thing<Username>) usernameOrPerson);
        } else {
            Observable<Thing<Username>> obs = tc.observeChildrenForType(Observable.just(usernameOrPerson), Username.class, true);
            return obs;
        }

    }

    /**
     * Assembles a {@link User} object from a {@Person} one.
     *
     * @param p the person
     * @return the user thing object
     */
    public Thing<User> assembleUser(Thing<Person> p) {
        User user = new User();
        Observable<Thing<Username>> usernames = tc.observeChildrenForType(p, Username.class, true);

        return null;

//        usernames.toBlockingObservable().forEach(un -> user.addUsername(un.getKey(), un.getValue().getValue())); // can use valueid because we know it's a singlestringvalue
//        Observable<Thing<Role>> roles = tc.observeChildrenForType(p, Role.class, true);
//
//        roles.stream().forEach(role -> user.addRole(role.getKey(), role.getValueId()));
//
//        user.setPerson((Person) tc.getUntypedValue(p));
//
//        return new Thing(p.getKey(), user);
    }



}
