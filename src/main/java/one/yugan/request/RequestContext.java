package one.yugan.request;

import java.io.Serializable;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
public abstract class RequestContext<R extends ServletRequest, S extends ServletResponse> implements Serializable {

    protected abstract void init(R request, S response);

    public abstract Object findValue(String target, String key);

    public abstract Object getAll(String target);

}
