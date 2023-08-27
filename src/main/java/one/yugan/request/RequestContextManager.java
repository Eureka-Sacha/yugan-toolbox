package one.yugan.request;

/**
 * @author : yugan.
 * @date : 2023/8/22
 * @email : eureka_sacha@outlook.com
 */
public class RequestContextManager {
    private static final ThreadLocal<RequestContext<?, ?>> THREAD_LOCAL = new ThreadLocal<>();

    private static volatile RequestContextManager instance = null;

    private RequestContextManager() {

    }

    public static RequestContextManager getInstance() {
        if (instance == null) {
            synchronized (RequestContextManager.class) {
                if (instance == null) {
                    instance = new RequestContextManager();
                }
            }
        }
        return instance;
    }

    public void set(RequestContext<?, ?> context) {
        THREAD_LOCAL.set(context);
    }

    public RequestContext<?, ?> getCurrent() {
        return THREAD_LOCAL.get();
    }

    public void clean() {
        THREAD_LOCAL.remove();
    }
}
