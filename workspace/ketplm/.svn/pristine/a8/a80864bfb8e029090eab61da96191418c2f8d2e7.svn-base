package ext.ket.shared.util;

import org.springframework.web.context.ContextLoader;

public class SpringUtil {
    /**
     * Spring DL(Dependency Lookup)을 위한 메소드
     * 
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName) {
	return ContextLoader.getCurrentWebApplicationContext().getBean(beanName);
    }

}
