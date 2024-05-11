package core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify multiple locators for a single web element.
 * Allows specifying an ordered list of locator types to locate dynamic elements.
 *
 * <p>Each web element is located based on the first matching locator in the provided list.
 * This approach ensures robust element identification when dealing with dynamic or unpredictable element structures.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FindByList {
    /**
     * Defines an array of `Locator` annotations, each representing a locator type and value.
     *
     * @return Array of locator annotations specifying various locator types.
     */
    Locator[] value();

    /**
     * Annotation to define a single locator type and its value.
     */
    @interface Locator {
        /**
         * Specifies the type of locator to be used, such as ID or CSS.
         *
         * @return The locator type from the `LocatorType` enum.
         */
        LocatorType type();

        /**
         * Specifies the locator value, like an ID, class name, or CSS selector.
         *
         * @return The value associated with the locator type.
         */
        String value();
    }

    /**
     * Enum representing different locator types supported by Selenium WebDriver.
     * These types include standard locator strategies such as ID, Name, ClassName, etc.
     */
    enum LocatorType {
        ID, NAME, CLASS_NAME, CSS, XPATH, TAG_NAME, LINK_TEXT, PARTIAL_LINK_TEXT
    }
}
