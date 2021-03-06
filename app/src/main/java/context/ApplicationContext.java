package context;

import annotation.Inject;
import factory.BeanFactory;
import lombok.Setter;
import postprocessor.BeanPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Обёртка над BeanFactory, контролирует жизненный цикл бинов
 */
public class ApplicationContext {
    private static final ApplicationContext APPLICATION_CONTEXT = new ApplicationContext();
    private final Map<Class, Object> beanMap = new ConcurrentHashMap<>();
    @Setter
    private BeanFactory beanFactory;
    private boolean singleton = true;

    public ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        return APPLICATION_CONTEXT;
    }

    public <T> T getBean(Class<T> clazz) {
        if (beanMap.containsKey(clazz)) {
            Inject injet = clazz.getAnnotation(Inject.class);
            if (singleton || (injet != null && !injet.singleton())) {
                T bean = (T) beanMap.get(clazz);
                callPostProcessor(bean);
                return bean;
            }


        }
        T bean = beanFactory.getBean(clazz);
        callPostProcessor(bean);

        beanMap.put(clazz, bean);
        return bean;
    }

    public <T> T getBean(Class<T> clazz, boolean singleton) {
        this.singleton = singleton;
        T bean = getBean(clazz);
        this.singleton = true;
        return bean;
    }

    public <T> T getBean(String className) {
        Class<T> clazz = null;
        try {
            clazz = (Class<T>) beanFactory.getClassByClassName(className);
        } catch (Exception e) {
            System.err.println("Не удалось найти класс");
        }
        return APPLICATION_CONTEXT.getBean(clazz);
    }

    public void callPostProcessor(Object bean) {
        beanFactory.getBeanConfigurator().getScanner().getSubTypesOf(BeanPostProcessor.class)
                .forEach(processor -> {
                    try {
                        processor.getDeclaredConstructor().newInstance().process(bean);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }
}
