package one.yugan;

import one.yugan.request.RequestScopeContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        RequestScopeContextFilter requestScopeContextFilter = context.getBean(RequestScopeContextFilter.class);
        System.out.println(requestScopeContextFilter.requestContextInstance);

    }
}
