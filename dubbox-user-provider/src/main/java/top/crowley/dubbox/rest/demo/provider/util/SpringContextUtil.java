package top.crowley.dubbox.rest.demo.provider.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author Wangsy
 *
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(final Class<T> type) {
        return applicationContext.getBean(type);
    }
}
