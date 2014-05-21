package things.thing;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import things.exceptions.TypeRuntimeException;
import things.types.NoRestrictionsType;
import things.types.TypeRegistry;
import things.types.UniqueKeyType;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;


public abstract class BaseThingControlTest {

    private int allThingsSize = 4;

    private NoRestrictionsType nrt1;
    private NoRestrictionsType nrt2;
    private Thing<NoRestrictionsType> t1;
    private Thing<NoRestrictionsType> t2;
    private Thing<UniqueKeyType> t3;
    private Thing<UniqueKeyType> t4;
    @Inject
    private ThingControl thingControl;
    @Inject
    private TypeRegistry typeRegistry;
    private UniqueKeyType ukt1;
    private UniqueKeyType ukt2;

    public abstract void deleteAllThings();

    @Before
    public void setUp() throws Exception {
        nrt1 = new NoRestrictionsType();
        nrt1.setProperty1("prop1");
        nrt1.setProperty2("prop2");
        t1 = thingControl.createThing("nrt1", nrt1);
        nrt2 = new NoRestrictionsType();
        nrt2.setProperty1("prop3");
        nrt2.setProperty2("prop4");
        t2 = thingControl.createThing("nrt2", nrt2);
        ukt1 = new UniqueKeyType();
        ukt1.setProperty1("prop1_u");
        ukt1.setProperty2("prop2_u");
        t3 = thingControl.createThing("ukt1", ukt1);
        ukt2 = new UniqueKeyType();
        ukt2.setProperty1("prop3_u");
        ukt2.setProperty2("prop4_u");
        t4 = thingControl.createThing("ukt2", ukt2);
    }

    @After
    public void tearDown() throws Exception {

        deleteAllThings();

    }

    @Test
    public void testAddChildThing() throws Exception {
        thingControl.addChildThing(t1, t2);

        Optional<Thing<NoRestrictionsType>> nt1 = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "nrt1", true);
        Optional<Thing<NoRestrictionsType>> nt2 = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "nrt2", true);

        List<Thing> children = thingControl.getChildren(nt1.get());

        assertThat("Parent contains new child", children.contains(nt2.get()));
        assertThat("Child only has one parent", nt2.get().getParents().size() == 1);
    }

    @Test
    public void testConvertToTyped() throws Exception {

        List<Thing> allThings = thingControl.findThingsForType("noRestrictionsType");
        assertThat("List is not empty", allThings.size() > 0);
        for ( Thing t : allThings ) {
            Thing<NoRestrictionsType> tt = thingControl.populateAndConvertToTyped(NoRestrictionsType.class, t);
            NoRestrictionsType nrt = tt.getValue();
            assertThat("Value is of the right class", nrt instanceof NoRestrictionsType);
        }

    }

    @Test(expected = TypeRuntimeException.class)
    public void testConvertToTypedFail() throws Exception {

        List<Thing> allThings = thingControl.findThingsForType("noRestrictionsType");
        assertThat("List is not empty", allThings.size() > 0);
        for ( Thing t : allThings ) {
            Thing<UniqueKeyType> tt = thingControl.populateAndConvertToTyped(UniqueKeyType.class, t);
            UniqueKeyType ukt = tt.getValue();
        }

    }

    @Test
    public void testCreateThing() throws Exception {
        NoRestrictionsType nrt3 = new NoRestrictionsType();
        nrt1.setProperty1("prop6");
        nrt1.setProperty2("prop7");
        Thing<NoRestrictionsType> t3 = thingControl.createThing("nrt3", nrt3);

        List<Thing> allThings = thingControl.findAllThings();
        assertThat("Newly created thing is in the list of all things", allThings.contains(t3));
        Optional<Thing<NoRestrictionsType>> temp = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "nrt3", true);
        assertThat("Newly created thing value is present and equal", temp.get().getValue().equals(nrt3));

    }

    @Test
    public void testExecuteAction() throws Exception {
        //TODO
    }

    @Test
    public void testExecuteQuery() throws Exception {
        //TODO
    }

    @Test
    public void testFilterThingsWithValue() throws Exception {
        List<Thing> allThings = thingControl.findAllThings();

        List<Thing<NoRestrictionsType>> filtered = thingControl.filterThingsWithValue(allThings, nrt2);

        assertThat("Filtered list only has 1 result", filtered.size() == 1);
        assertThat("Values match", nrt2.equals(filtered.get(0).getValue()));

    }

    @Test
    public void testFindAllThings() throws Exception {

        List<Thing> allThings = thingControl.findAllThings();

        assertThat("All 2 thigs are present", allThings.size() == allThingsSize);
        assertThat("All things contain thing 1", allThings.contains(t1));
        assertThat("All things contain thing 2", allThings.contains(t2));
        assertThat("All things contain thing 3", allThings.contains(t3));
        assertThat("All things contain thing 3", allThings.contains(t4));

    }

    @Test
    public void testFindThingsForType() throws Exception {
        List<Thing<NoRestrictionsType>> things1 = thingControl.findThingsForType(NoRestrictionsType.class);
        List<Thing> things2 = thingControl.findThingsForType(typeRegistry.getType(UniqueKeyType.class));

        assertThat("All NoRestrictionType things are present", things1.contains(t1));
        assertThat("All NoRestrictionType things are present", things1.contains(t2));
        assertThat("All UniqueKeyType things are present", things2.contains(t3));
        assertThat("All UniqueKeyType things are present", things2.contains(t4));

    }

    @Test
    public void testFindThingsForTypeAndKey() throws Exception {


        List<Thing<NoRestrictionsType>> things1 = thingControl.findThingsForTypeAndKey(NoRestrictionsType.class, "nrt1");
        List<Thing> things2 = thingControl.findThingsForTypeAndKey(typeRegistry.getType(UniqueKeyType.class), "ukt1");

        assertThat("NoRestrictionType thing 1 is present", things1.contains(t1));
        assertThat("NoRestrictionType thing 2 is not present", !things1.contains(t2));
        assertThat("UniqueKeyType thing 1 is present", things2.contains(t3));
        assertThat("UniqueKeyType thing 2 is not present", !things2.contains(t4));
    }

    @Test
    public void testFindThingsMatchingType() throws Exception {
        List<Thing> things1 = thingControl.findThingsMatchingType("*Type");

        assertThat("All things are found", things1.size() == 4);
    }

    @Test
    public void testFindThingsMatchingTypeAndKey() throws Exception {
        List<Thing> things1 = thingControl.findThingsMatchingTypeAndKey("noRestrictionsType", "nrt1");
        assertThat("Only one result", things1.size() == 1);

        things1 = thingControl.findThingsMatchingTypeAndKey("noRestrictionsType", "nrt*");
        assertThat("Only two results", things1.size() == 2);

        things1 = thingControl.findThingsMatchingTypeAndKey("noRestrictionsType", "*1");
        assertThat("Only one result", things1.size() == 1);

        things1 = thingControl.findThingsMatchingTypeAndKey("noRestrictionsType", "*rt1");
        assertThat("Only one result", things1.size() == 1);

        things1 = thingControl.findThingsMatchingTypeAndKey("*", "*1");
        assertThat("Only two results", things1.size() == 2);

        things1 = thingControl.findThingsMatchingTypeAndKey("*Type", "*1");
        assertThat("Only two results", things1.size() == 2);

        things1 = thingControl.findThingsMatchingTypeAndKey("*Type", "nrt*");
        assertThat("Only two results", things1.size() == 2);

        things1 = thingControl.findThingsMatchingTypeAndKey("*niqu*", "*1");
        assertThat("Only two results", things1.size() == 1);

        things1 = thingControl.findThingsMatchingTypeAndKey("*niqu*", "*");
        assertThat("Only two results", things1.size() == 2);

        things1 = thingControl.findThingsMatchingTypeAndKey("*", "*");
        assertThat("Only two results", things1.size() == 4);
    }

    @Test
    public void testFindUniqueThingMatchingTypeAndKey() throws Exception {

        Optional<Thing> result = thingControl.findUniqueThingMatchingTypeAndKey("*", "nrt1");
        assertThat("One result was returned", result.isPresent());

        result = thingControl.findUniqueThingMatchingTypeAndKey("noRestrictionsType", "*1");
        assertThat("One result was returned", result.isPresent());

        result = thingControl.findUniqueThingMatchingTypeAndKey("noRestrictionsType", "*3");
        assertThat("No result was returned", !result.isPresent());

        result = thingControl.findUniqueThingMatchingTypeAndKey("*Type", "nrt1");
        assertThat("One result was returned", result.isPresent());

        result = thingControl.findUniqueThingMatchingTypeAndKey("*niq*", "*2*");
        assertThat("One result was returned", result.isPresent());

        Optional<Thing<NoRestrictionsType>> resultTyped = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "*3");
        assertThat("One result was returned", !resultTyped.isPresent());

        resultTyped = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "*2");
        assertThat("One result was returned", resultTyped.isPresent());
    }

    @Test
    public void testGetChildren() throws Exception {
        thingControl.addChildThing(t1, t2);
        thingControl.addChildThing(t1, t3);
        thingControl.addChildThing(t1, t4);

        List<Thing> children = thingControl.getChildren(t1);
        assertThat("Parent has 3 children", children.size() == 3);
    }

    @Test
    public void testGetChildrenForType() throws Exception {
        thingControl.addChildThing(t1, t2);
        thingControl.addChildThing(t1, t3);
        thingControl.addChildThing(t1, t4);

        List<Thing<UniqueKeyType>> children = thingControl.getChildrenForType(t1, UniqueKeyType.class);
        assertThat("Parent has 2 children", children.size() == 2);
        assertThat("Thing 3 is child of thing 1", children.contains(t3));
        assertThat("Thing 4 is child of thing 1", children.contains(t4));
    }

    @Test
    public void testGetChildsMatchingType() throws Exception {
        thingControl.addChildThing(t1, t2);
        thingControl.addChildThing(t1, t3);
        thingControl.addChildThing(t1, t4);

        List<Thing> children = thingControl.getChildrenMatchingType(t1, "*niqu*");
        assertThat("Parent has 2 children of type UniqueKeyType", children.size() == 2);
        assertThat("Thing 3 is child of thing 1", children.contains(t3));
        assertThat("Thing 4 is child of thing 1", children.contains(t4));
    }

    @Test
    public void testGetChildsMatchingTypeAndKey() throws Exception {
        thingControl.addChildThing(t1, t2);
        thingControl.addChildThing(t1, t3);
        thingControl.addChildThing(t1, t4);

        List<Thing> children = thingControl.getChildrenMatchingTypeAndKey(t1, "*niqu*", "*");
        assertThat("Parent has 2 children of type UniqueKeyType", children.size() == 2);
        assertThat("Thing 3 is child of thing 1", children.contains(t3));
        assertThat("Thing 4 is child of thing 1", children.contains(t4));

        children = thingControl.getChildrenMatchingTypeAndKey(t1, "*", "*1");
        assertThat("Parent has  child of type UniqueKeyType", children.size() == 1);
        assertThat("Thing 3 is child of thing 1", children.contains(t3));
    }

    @Test
    public void testGetValue() throws Exception {
        Optional<Thing<NoRestrictionsType>> t = thingControl.findUniqueThingMatchingTypeAndKey(NoRestrictionsType.class, "nrt1", false);

        assertThat("Value equals original value", thingControl.getValue(t.get()).equals(nrt1));
    }

    @Test
    public void testThingForTypeAndKeyExists() throws Exception {
        boolean exists = thingControl.thingForTypeAndKeyExists("noRestrictionsType", "nrt2");
        assertThat("Thing exists", exists);
        exists = thingControl.thingForTypeAndKeyExists("noRestrictionsType", "nrt3");
        assertThat("Thing does not exist", !exists);
        exists = thingControl.thingForTypeAndKeyExists("noRestrictionsTypeXXX", "nrt2");
        assertThat("Thing does not exist", !exists);
    }

    @Test
    public void testThingForTypeAndKeyExists1() throws Exception {

    }

    @Test
    public void testThingMatchingTypeAndKeyExists() throws Exception {
        boolean exists = thingControl.thingMatchingTypeAndKeyExists("*niq*", "ukt1");
        assertThat("Thing exists", exists);
        exists = thingControl.thingMatchingTypeAndKeyExists("*xxx*", "*");
        assertThat("Thing does not exist", !exists);
    }
}
