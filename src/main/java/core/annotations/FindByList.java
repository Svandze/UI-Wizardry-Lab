package core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindByList {
    Locator[] value();

    enum LocatorType {
        ID, NAME, CLASS_NAME, TAG_NAME, CSS, XPATH, LINK_TEXT, PARTIAL_LINK_TEXT
    }

    @interface Locator {
        LocatorType type();
        String value();
    }
}
