package core;

import core.annotations.FindByList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.FieldDecorator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CustomPageFactory {

    public static void initElements(WebDriver driver, Object page) {
        FieldDecorator decorator = new CustomFieldDecorator(new DefaultElementLocatorFactory(driver), driver);
        decorateFields(decorator, page);
    }

    private static void decorateFields(FieldDecorator decorator, Object page) {
        Class<?> proxyIn = page.getClass();
        while (proxyIn != Object.class) {
            decorateFields(proxyIn, decorator, page);
            proxyIn = proxyIn.getSuperclass();
        }
    }

    private static void decorateFields(Class<?> proxyIn, FieldDecorator decorator, Object page) {
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

    static class CustomFieldDecorator extends DefaultFieldDecorator {
        private final WebDriver driver;

        public CustomFieldDecorator(DefaultElementLocatorFactory factory, WebDriver driver) {
            super(factory);
            this.driver = driver;
        }

        @Override
        public Object decorate(ClassLoader loader, Field field) {
            if (field.isAnnotationPresent(FindByList.class)) {
                List<By> bys = constructBys(field.getAnnotation(FindByList.class));
                ElementLocator locator = new CustomElementLocator(driver, bys);
                if (WebElement.class.isAssignableFrom(field.getType())) {
                    return proxyForLocator(loader, locator);
                } else if (List.class.isAssignableFrom(field.getType())) {
                    return proxyForListLocator(loader, locator);
                }
            }
            return super.decorate(loader, field);
        }

        private List<By> constructBys(FindByList annotation) {
            List<By> bys = new ArrayList<>();
            for (FindByList.Locator locator : annotation.value()) {
                switch (locator.type()) {
                    case ID -> bys.add(By.id(locator.value()));
                    case NAME -> bys.add(By.name(locator.value()));
                    case CLASS_NAME -> bys.add(By.className(locator.value()));
                    case CSS -> bys.add(By.cssSelector(locator.value()));
                    case XPATH -> bys.add(By.xpath(locator.value()));
                    case LINK_TEXT -> bys.add(By.linkText(locator.value()));
                    case PARTIAL_LINK_TEXT -> bys.add(By.partialLinkText(locator.value()));
                    case TAG_NAME -> bys.add(By.tagName(locator.value()));
                }
            }
            return bys;
        }
    }
}
