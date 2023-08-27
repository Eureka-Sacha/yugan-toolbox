package one.yugan.request;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.yugan.common.YuganProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static one.yugan.common.Constans.REQUEST_SCOPE_CONTEXT_PROPERTIES_PREFIX;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = REQUEST_SCOPE_CONTEXT_PROPERTIES_PREFIX)
public class Properties extends YuganProperties {
    public boolean enable;
    public String requestContextImpl;
}



