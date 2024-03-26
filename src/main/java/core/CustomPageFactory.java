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
                FindByList findByList = field.getAnnotation(FindByList.class);
                List<By> bys = new ArrayList<>();
                for (String locator : findByList.value()) {
                    bys.add(By.xpath(locator)); //Se está asumiendo que todo son Xpath, validar si se cambia por CSS
                }
                ElementLocator locator = new CustomElementLocator(driver, bys);
                if (WebElement.class.isAssignableFrom(field.getType())) {
                    return proxyForLocator(loader, locator);
                }
                //TODO Agregar soporte para List<WebElement> si es necesario
            }
            return super.decorate(loader, field);
        }
    }
}
