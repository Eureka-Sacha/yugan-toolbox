package one.yugan.mission;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.yugan.common.YuganProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static one.yugan.common.Constans.MISSION_BUCKET_PROPERTIES_PREFIX;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ConfigurationProperties(prefix = MISSION_BUCKET_PROPERTIES_PREFIX)
public class Properties extends YuganProperties {
    private boolean enable;
    @Min(2)
    @Max(1000)
    private int core = 2;
    private int max = 10;
    private int alive = 0;
    private boolean block = true;
    private int queue = 1000;
    private RejectedExecutionHandler rejectedHandler = new AbortPolicy();
    private TimeUnit unit = TimeUnit.SECONDS;
    private boolean scheduler;
    private boolean monitor = false;
}
