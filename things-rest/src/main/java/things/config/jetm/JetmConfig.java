package things.config.jetm;

import etm.contrib.aop.aopalliance.EtmMethodCallInterceptor;
import etm.contrib.console.HttpConsoleServer;
import etm.contrib.integration.spring.web.SpringHttpConsoleServlet;
import etm.core.monitor.NestedMonitor;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Markus Binsteiner
 */
@Configuration
public class JetmConfig {

    @Bean
    public BeanNameAutoProxyCreator etmAutoProxy() {
        BeanNameAutoProxyCreator bc = new BeanNameAutoProxyCreator();
        bc.setInterceptorNames(new String[]{"etmMethodCallInterceptor"});
        bc.setBeanNames(new String[]{
                "thingControl"
//                "*RestController"
//                "*Reader"
//                "*Writer",
//                "*Action",
//                "*Query",
//                "*Connector"
        });

        return bc;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public HttpConsoleServer etmHttpConsole() {
        HttpConsoleServer hcs = new HttpConsoleServer(etmMonitor());
        return hcs;
    }

    @Bean
    public EtmMethodCallInterceptor etmMethodCallInterceptor() {
        EtmMethodCallInterceptor emci = new EtmMethodCallInterceptor(etmMonitor());
        return emci;
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public NestedMonitor etmMonitor() {
        NestedMonitor nm = new NestedMonitor();
        return nm;
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        SpringHttpConsoleServlet servlet = new SpringHttpConsoleServlet();
        ServletRegistrationBean srb = new ServletRegistrationBean(servlet, "/jetm/*");
        srb.setLoadOnStartup(1);
        return srb;
    }
}
