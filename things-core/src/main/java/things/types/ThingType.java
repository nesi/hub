package things.types;

import com.google.common.collect.Sets;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Project: things
 * <p>
 * Written by: Markus Binsteiner
 * Date: 14/05/14
 * Time: 8:58 PM
 */
public class ThingType<V> {

    private final String type;
    private final Class typeClass;

    private boolean needsUniqueKey = false;
    private boolean needsUniqueKeyAsChild = false;
    private boolean needsUniqueValue = false;
    private boolean needsParent = false;
    private Set<String> allowedParentTypes = Sets.newHashSet();

    private SingleStringConverter<V> converter;

    public ThingType(String type, Class typeClass) {
        this.type = type;
        this.typeClass = typeClass;
    }

    public String getType() {
        return type;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public boolean isNeedsUniqueKey() {
        return needsUniqueKey;
    }

    public void setNeedsUniqueKey(boolean needsUniqueKey) {
        this.needsUniqueKey = needsUniqueKey;
    }

    public boolean isNeedsUniqueKeyAsChild() {
        return needsUniqueKeyAsChild;
    }

    public void setNeedsUniqueKeyAsChild(boolean needsUniqueKeyAsChild) {
        this.needsUniqueKeyAsChild = needsUniqueKeyAsChild;
    }

    public boolean isNeedsUniqueValue() {
        return needsUniqueValue;
    }

    public void setNeedsUniqueValue(boolean needsUniqueValue) {
        this.needsUniqueValue = needsUniqueValue;
    }

    public boolean isNeedsParent() {
        return needsParent;
    }

    public void setNeedsParent(boolean needsParent) {
        this.needsParent = needsParent;
    }

    public Set<String> getAllowedParentTypes() {
        return allowedParentTypes;
    }

    public void setAllowedParentTypes(Set<String> allowedParentTypes) {
        this.allowedParentTypes = allowedParentTypes;
    }

    private SingleStringConverter<V> getConverter() {
        return converter;
    }

    public void setConverter(SingleStringConverter<V> converter) {
        this.converter = converter;
    }

    public Optional<String> convertToString(V value) {
        if ( converter != null ) {
            return Optional.of(converter.convertToString(value));
        } else {
            return Optional.empty();
        }
    }

    public Optional<V> convertFromString(String valueString) {
        if ( converter != null ) {
            return Optional.of(converter.convertFromString(valueString));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof ThingType ) {
            return Objects.equals(getType(), ((ThingType) o).getType());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getType());
    }

    public boolean convertsFromString() {
        return converter != null;
    }
}
