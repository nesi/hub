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

    private Set<String> allowedParentTypes = Sets.newHashSet();
    private SingleStringConverter<V> converter;
    private boolean needsParent = false;
    private boolean needsUniqueKey = false;
    private boolean needsUniqueValueForKeyAsChild = false;
    private boolean needsUniqueKeyAsChild = false;
    private boolean needsUniqueValue = false;
    private final String type;
    private final Class typeClass;
    public ThingType(String type, Class typeClass) {
        this.type = type;
        this.typeClass = typeClass;
    }

    public Optional<V> convertFromString(String valueString) {
        if ( converter != null ) {
            return Optional.of(converter.convertFromString(valueString));
        } else {
            return Optional.empty();
        }
    }

    public Optional<String> convertToString(V value) {
        if ( converter != null ) {
            return Optional.of(converter.convertToString(value));
        } else {
            return Optional.empty();
        }
    }

    public boolean convertsFromString() {
        return converter != null;
    }

    @Override
    public boolean equals(Object o) {
        if ( o instanceof ThingType ) {
            return Objects.equals(getType(), ((ThingType) o).getType());
        } else {
            return false;
        }
    }

    public Set<String> getAllowedParentTypes() {
        return allowedParentTypes;
    }

    private SingleStringConverter<V> getConverter() {
        return converter;
    }

    public String getType() {
        return type;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getType());
    }

    public boolean isNeedsParent() {
        return needsParent;
    }

    public boolean isNeedsUniqueKey() {
        return needsUniqueKey;
    }

    public boolean isNeedsUniqueValueForKeyAsChild() {
        return needsUniqueValueForKeyAsChild;
    }

    public boolean isNeedsUniqueKeyAsChild() {
        return needsUniqueKeyAsChild;
    }

    public boolean isNeedsUniqueValue() {
        return needsUniqueValue;
    }

    public void setAllowedParentTypes(Set<String> allowedParentTypes) {
        this.allowedParentTypes = allowedParentTypes;
    }

    public void setConverter(SingleStringConverter<V> converter) {
        this.converter = converter;
    }

    public void setNeedsParent(boolean needsParent) {
        this.needsParent = needsParent;
    }

    public void setNeedsUniqueKey(boolean needsUniqueKey) {
        this.needsUniqueKey = needsUniqueKey;
    }

    public void setNeedsUniqueValueForKeyAsChild(boolean needsUniqueValueForKeyAsChild) {
        this.needsUniqueValueForKeyAsChild = needsUniqueValueForKeyAsChild;
    }

    public void setNeedsUniqueKeyAsChild(boolean needsUniqueKeyAsChild) {
        this.needsUniqueKeyAsChild = needsUniqueKeyAsChild;
    }

    public void setNeedsUniqueValue(boolean needsUniqueValue) {
        this.needsUniqueValue = needsUniqueValue;
    }
}
