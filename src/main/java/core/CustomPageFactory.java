package core;

import core.annotations.FindByList;
import org.openqa.selenium.*;
import org.openqa.selenium.support.pagefactory.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * CustomPageFactory provides a custom implementation of Page Factory pattern for initializing web elements.
 * It specifically handles elements annotated with the custom `FindByList` annotation and prioritizes different locators.
 */
public class CustomPageFactory {
    /**
     * Initializes the fields of a given page object annotated with `FindByList` or standard annotations.
     * A custom field decorator is used to ensure that prioritized locators are respected.
     *
     * @param driver The WebDriver instance used to locate and interact with web elements.
     * @param page   The page object containing web elements to be initialized.
     */
    public static void initElements(WebDriver driver, Object page) {
        FieldDecorator decorator = new CustomFieldDecorator(new DefaultElementLocatorFactory(driver), driver);
        decorateFields(decorator, page);
    }

    /**
     * Decorates the fields of the specified page object using the provided field decorator.
     * This method ensures that all fields (including those inherited) are decorated properly.
     *
     * @param decorator The field decorator to apply to the page's fields.
     * @param page      The page object containing the fields to be decorated.
     */
    private static void decorateFields(FieldDecorator decorator, Object page) {
        for (Class<?> proxyIn = page.getClass(); proxyIn != Object.class; proxyIn = proxyIn.getSuperclass()) {
            Field[] fields = proxyIn.getDeclaredFields();
            for (Field field : fields) {
                Object value = decorator.decorate(page.getClass().getClassLoader(), field);
                if (value != null) {
                    try {
                        field.setAccessible(true);
                        field.set(page, value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    /**
     * CustomFieldDecorator extends the default field decorator to add support for the `FindByList` annotation.
     * It allows for prioritized locators when finding a web element.
     */
    /**
     * CustomFieldDecorator extends the default field decorator to support the custom `FindByList` annotation.
     * This decorator facilitates mapping multiple locators to a single web element or a list of web elements.
     */
    static class CustomFieldDecorator extends DefaultFieldDecorator {
        private final WebDriver driver;

        /**
         * Constructor for the CustomFieldDecorator.
         *
         * @param factory The factory responsible for providing element locators.
         * @param driver  The WebDriver instance to interact with web elements.
         */
        public CustomFieldDecorator(DefaultElementLocatorFactory factory, WebDriver driver) {
            super(factory);
            this.driver = driver;
        }

        /**
         * Decorates a field annotated with the custom `FindByList` annotation.
         * If the field is not annotated, it applies the standard field decoration.
         *
         * @param loader The class loader used to define the proxy class.
         * @param field  The field that needs to be decorated.
         * @return The proxy object mapped to the field or `null` if no proxy is found.
         */
        @Override
        public Object decorate(ClassLoader loader, Field field) {
            if (field.isAnnotationPresent(FindByList.class)) {
                FindByList findByList = field.getAnnotation(FindByList.class);
                WebElement proxyElement = proxyElementForLocator(loader, findByList);
                if (WebElement.class.isAssignableFrom(field.getType())) {
                    return proxyElement;
                } else if (List.class.isAssignableFrom(field.getType())) {
                    return proxyForListLocator(loader, new CustomElementLocator(driver, constructLocators(findByList)));
                }
            }
            return super.decorate(loader, field);
        }

        /**
         * Creates a proxy web element object based on the `FindByList` annotation.
         * This proxy element will dynamically locate elements using prioritized locators.
         *
         * @param loader     The class loader used to define the proxy class.
         * @param findByList The `FindByList` annotation containing multiple locators.
         * @return A proxy web element object using the prioritized locators.
         */
        private WebElement proxyElementForLocator(ClassLoader loader, FindByList findByList) {
            ElementLocator locator = new CustomElementLocator(driver, constructLocators(findByList));
            return proxyForLocator(loader, locator);
        }

        /**
         * Constructs a list of By locators based on the `FindByList` annotation.
         *
         * @param findByList The `FindByList` annotation containing multiple locators.
         * @return A list of By locators to use for finding web elements.
         */
        private List<By> constructLocators(FindByList findByList) {
            List<By> locators = new ArrayList<>();
            for (FindByList.Locator locator : findByList.value()) {
                locators.add(getByFromLocator(locator));
            }
            return locators;
        }

        /**
         * Converts a `FindByList.Locator` annotation into a corresponding Selenium By object.
         *
         * @param locator The `FindByList.Locator` annotation containing the locator type and value.
         * @return The corresponding By object used for locating web elements.
         * @throws IllegalArgumentException If the locator type is unsupported.
         */
        private By getByFromLocator(FindByList.Locator locator) {
            return switch (locator.type()) {
                case ID -> By.id(locator.value());
                case NAME -> By.name(locator.value());
                case CLASS_NAME -> By.className(locator.value());
                case CSS -> By.cssSelector(locator.value());
                case XPATH -> By.xpath(locator.value());
                case TAG_NAME -> By.tagName(locator.value());
                case LINK_TEXT -> By.linkText(locator.value());
                case PARTIAL_LINK_TEXT -> By.partialLinkText(locator.value());
            };
        }
    }
}
