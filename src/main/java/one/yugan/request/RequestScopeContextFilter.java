package one.yugan.request;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author : yugan.
 * @date : 2023/8/22
 * @email : eureka_sacha@outlook.com
 */
public class RequestScopeContextFilter extends OncePerRequestFilter {

    private final RequestContextManager contextManager = RequestContextManager.getInstance();

    public final Class<? extends RequestContext<? extends ServletRequest, ? extends ServletResponse>>
        requestContextInstance;

    public RequestScopeContextFilter() {
        requestContextInstance = DefaultRequestContext.class;
    }

    public RequestScopeContextFilter(String clazz) throws ClassNotFoundException {
        Class<?> z = Class.forName(clazz);
        if (RequestContext.class.isAssignableFrom(z)) {
            requestContextInstance
                = (Class<? extends RequestContext<? extends ServletRequest, ? extends ServletResponse>>)z;
        } else {
            throw new ClassNotFoundException("not a instance from one.yugan.request.RequestContext");
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            RequestContext requestContext = requestContextInstance.getDeclaredConstructor().newInstance();
            requestContext.init(request, response);
            contextManager.set(requestContext);
            filterChain.doFilter(request, response);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        } finally {
            contextManager.clean();
        }
    }

}
