package things;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.apache.bval.guice.ValidationModule;
import rx.Observable;
import things.config.XstreamModule;
import things.thing.Thing;
import things.thing.ThingControl;
import things.thing.ThingReaders;
import things.types.Address;
import things.types.Person;
import things.types.Role;
import things.utils.FileUtils;

import javax.validation.Validator;
import java.nio.file.Paths;

/**
 * Project: hub
 * <p/>
 * Written by: Markus Binsteiner
 * Date: 12/03/14
 * Time: 10:56 AM
 */
public class Application {
    

	public static void main(String[] args) throws Exception {
        
//        AbstractApplicationContext context = new AnnotationConfigApplicationContext(TestMongoConfig.class);

//        MongoOperations mo = (MongoOperations) context.getBean("mongoTemplate");
//        ThingControl tc = (ThingControl) context.getBean("thingControl");

        FileUtils.removeRecursive(Paths.get("/home/markus/things"));
        FileUtils.removeRecursive(Paths.get("/home/markus/values"));

        try {
            Injector injector = Guice.createInjector(new XstreamModule(), new ValidationModule());

            ThingControl tc = injector.getInstance(ThingControl.class);

            ThingReaders re = injector.getInstance(ThingReaders.class);
            Validator val = injector.getInstance(Validator.class);
//        MongoOperations

//        mo.dropCollection(Thing.class);
//        mo.dropCollection(Person.class);
//        mo.dropCollection(Role.class);

            Person user = new Person();
            user.setFirstName("Person");
            user.setLastName("Name");

            Thing<Person> pt = tc.createThing("username", user);

            Address address = new Address();
            address.setCity("Auckland");
            address.setCountry("NZ");
            address.setNr(1);
            address.setStreet("Fleet street");

            Thing<Address> at = tc.createThing("home", address);

            tc.addChildThing(pt, at);

            Object id = pt.getId();
            System.out.println("ID: "+id);

            Role role1 = new Role("role1");
            Thing<Role> r1t = tc.createThing("group_1", role1);

            Role role2 = new Role("role2");
            Thing<Role> r2t = tc.createThing("group_1", role2);

            Role role3 = new Role("role3");
            Thing<Role> r3t = tc.createThing("group_2", role3);

            tc.addChildThing(pt, r1t);
            tc.addChildThing(pt, r2t);
            tc.addChildThing(pt, r3t);


            Observable<Thing> childs = tc.getChildsMatchingTypeAndKey(pt, "role", "*2*", true);

            childs.toBlockingObservable().forEach(t -> System.out.println(t));


            Observable<Thing> childs2 = tc.getChildsMatchingTypeAndKey(pt, "address", "*", true);

            childs2.toBlockingObservable().forEach(t -> System.out.println(t));



        } catch (Exception e) {
            e.printStackTrace();
        }

//        Optional<Thing> t = tc.findThingById(Person.class, id);

//        System.out.println(t);

//
////        for (String s : context.getBeanDefinitionNames() ) {
////            System.out.println(s);
////        }
//
//        mo.dropCollection(Thing.class);
//        mo.dropCollection(Person.class);
//        mo.dropCollection(Role.class);
//
//        Person user = new Person();
//        user.setFirstName("Person");
//        user.setLastName("Name");
//
//        Thing ut = tc.createThing("username", user);
//
//        Role role1 = new Role("role1");
//        Thing r1t = tc.createThing("group_1", role1);
//
//        Role role2 = new Role("role2");
//        Thing r2t = tc.createThing("group_1", role2);
//
//        tc.addThingToThing(ut, r1t);
//        tc.addThingToThing(ut, r2t);
//
//
//        tc.findAllThings().forEach(t ->
//                {
//                    System.out.println(t);
//                }
//        );

//        SpringApplication.run(TestMongoConfig.class, args);

	}




}
