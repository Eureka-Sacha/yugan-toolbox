package one.yugan.request;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static one.yugan.common.Constans.REQUEST_SCOPE_CONTEXT_PROPERTIES_PREFIX;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
@Configuration
@EnableConfigurationProperties(Properties.class)
public class RequestScopeContextAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = REQUEST_SCOPE_CONTEXT_PROPERTIES_PREFIX, name = "enable", havingValue = "true")
    @ConditionalOnMissingBean
    public RequestScopeContextFilter requestScopeContextFilter(Properties properties)
        throws ClassNotFoundException {
        if (properties.getRequestContextImpl() == null) {
            return new RequestScopeContextFilter();
        } else {
            return new RequestScopeContextFilter(properties.getRequestContextImpl());
        }
    }
}
