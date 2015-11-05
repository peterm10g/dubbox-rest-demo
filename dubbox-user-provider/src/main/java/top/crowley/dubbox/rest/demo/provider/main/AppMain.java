package top.crowley.dubbox.rest.demo.provider.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author wangsy
 */
public class AppMain {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppMain.class);

    public static final String configFile = "classpath:application-*.xml";

    private static ClassPathXmlApplicationContext context;

    public static void main(final String[] args) {

        context = new ClassPathXmlApplicationContext(new String[] { configFile });
        context.start();
        try {
            Thread.sleep(60*60*1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        LOGGER.info("========Application Started========");
    }

}
