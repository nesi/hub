package things.thing;

import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Set;

/**
 * Project: things-to-build
 * <p>
 * Written by: Markus Binsteiner
 * Date: 6/05/14
 * Time: 7:55 PM
 */
public class Thing<V> implements Comparable<Thing>, java.io.Serializable {

    private String id;

    private String key;
    private Set<String> parents = Sets.newHashSet();
    private String thingType;
    protected V value;
    private Boolean valueIsLink = false;

    /**
     * Default constructor, don't use in normal circumstances.
     * <p>
     * If creating a new Thing to store (i.e. one that has a {@link things.model.PersistentValue} Value),
     * always use {@link ThingControl#createThing(String, java.io.Serializable)}.
     * <p>
     * If creating a Thing for returning via the API, prefer the {@link things.thing.Thing#Thing(String, java.io.Serializable)} constructor.
     */
    public Thing() {
    }

    @Override
    public int compareTo(Thing o) {
        return ComparisonChain.start().compare(getThingType(), o.getThingType())
                .compare(getKey(), o.getKey())
                        //.compare(getValue(), o.getValue(), valueComparator)
                        //.compare(getValue(), o.getValue(), Ordering.natural().nullsFirst())
                .compare(getId(), o.getId(), Ordering.natural().nullsFirst())
                .result();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null) return false;

        if (getClass().equals(obj.getClass())) {
            final Thing other = (Thing) obj;
            if (getId() == null || other.getId() == null) {
                return Objects.equals(getThingType(), other.getThingType()) && Objects.equals(getValue(), other.getValue());
            } else {
                return getId().equals(other.getId());
            }
        } else {
            return false;
        }
    }

    /**
     * Returns the id of this thing.
     * <p>
     * Ids are created by the underlying storage backend and unique among all Things.
     *
     * @return the (unique) id
     */
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
    public String getId() {
        return id;
    }

    /**
     * Returns the key of this thing.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    // --------------------- GETTER / SETTER METHODS ---------------------

    /**
     * Returns all the other Things this Thing has references to.
     *
     * @return this Things' other Things
     */
    public Set<String> getParents() {
        return parents;
    }

    /**
     * Returns the Type of this Things' value.
     *
     * @return the type
     */
    public String getThingType() {
        return thingType;
    }

    /**
     * The id of the value associated with this thing.
     * <p>
     * Used internally to retrieve the value itself.
     *
     * @return the value id
     */
    public V getValue() {
        return value;
    }

    public Boolean getValueIsLink() {
        return valueIsLink;
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return Objects.hash(getValue(), getThingType());
        }
        return Objects.hashCode(getId());
    }

    /**
     * Used internally for serialization/deserialization. Don't use if you don't know what you are doing.
     *
     * @param id the id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the key of this thing.
     * <p>
     * Usually you should not need to have to use this method.
     *
     * @param key the key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Sets this Things other things.
     * <p>
     * You should not need to use this method, if you want to add Things to Things to be stored
     * on the storage backend use {@link ThingControl#addThingToThing(things.thing.Thing, things.thing.Thing)}
     * or {@link ThingControl#addThingToThing(String, String)} method.
     * <p>
     * When returning Things via the API, other Things are not included in the serialization, so don't
     * waste cycles adding Things in the first place.
     *
     * @param ids the list of ids that point to this Things' other Things
     */
    public void setParents(Set<String> ids) {
        this.parents = ids;
    }

    /**
     * Sets this Things' value. Don't use if you don't know what you are doing.
     * <p>
     * Use the {@link #setValue(Serializable)} method instead, it'll also the the type.
     *
     * @param type the type
     */
    public void setThingType(String type) {
        thingType = type;
    }

    /**
     * Sets the id of this Things' value.
     * <p>
     * Don't use. Only used internally.
     *
     * @param value the value id.
     */
    public void setValue(V value) {
        if (this.value == null || this.value.equals(value)) {
            this.value = value;
        }
    }

    public void setValueIsLink(Boolean valueIsLink) {
        this.valueIsLink = valueIsLink;
    }

//    /**
//     * Sets the value for this Thing.
//     *
//     * Can be used to create a Thing that is returned to a client via a Thing-API.
//     *
//     * @param value the value
//     */
//    public void setValue(V value) {
//        setThingType(TypeRegistry.getType(value.getClass()));
//    }

    //    public void setValue(Value value) {
//        this.value = value;
//    }

    @Override
    public String toString() {
        return "Thing{" +
                "id='" + id + '\'' +
                ", type='" + getThingType() +
                "', key='" + key + '\'' +
                ", value='" + value +
                "', parents=" + parents +
                '}';
    }
}
