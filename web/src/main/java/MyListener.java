import com.fuzhongwangcs.im.Boot;
import com.fuzhongwangcs.im.spring.SpringContainer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class MyListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent event) {
        // TODO Auto-generated method stub  

    }

    public void contextInitialized(ServletContextEvent event) {
        SpringContainer.init();
        System.out.println("Spring容器初始化成功...");

        new Boot().boot();

    }
}  