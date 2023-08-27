package one.yugan.request;

import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import static one.yugan.request.RequestContextConstants.TARGET_ATTRIBUTE;
import static one.yugan.request.RequestContextConstants.TARGET_COOKIE;
import static one.yugan.request.RequestContextConstants.TARGET_HEADER;

/**
 * @author : yugan.
 * @date : 2023/8/22
 * @email : eureka_sacha@outlook.com
 */
@Getter
@Setter
public class DefaultRequestContext extends RequestContext<HttpServletRequest, HttpServletResponse> {

    private Map<String, String> headers;
    private Map<String, Object> attributes;
    private Cookie[] cookies;
    //todo
    private Map<String, Object> bodyParams;
    private Map<String, Object> params;

    @Override
    public void init(HttpServletRequest request, HttpServletResponse response) {
        this.headers = buildHeaderMap(request);
        this.attributes = buildAttribute(request);
        this.cookies = request.getCookies();
    }

    @Override
    public Object findValue(String target, String key) {
        return switch (target) {
            case TARGET_HEADER -> findHeader(key);
            case TARGET_ATTRIBUTE -> findAttribute(key);
            case TARGET_COOKIE -> findCookie(key);
            default -> null;
        };
    }

    @Override
    public Object getAll(String target) {
        return switch (target) {
            case TARGET_HEADER -> headers;
            case TARGET_ATTRIBUTE -> attributes;
            case TARGET_COOKIE -> cookies;
            default -> null;
        };
    }

    private Map<String, String> buildHeaderMap(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        request.getHeaderNames().asIterator().forEachRemaining(
            o -> headers.put(o, request.getHeader(o))
        );
        return headers;
    }

    private Map<String, Object> buildAttribute(HttpServletRequest request) {
        Map<String, Object> attributes = new HashMap<>();
        request.getAttributeNames().asIterator().forEachRemaining(
            o -> attributes.put(o, request.getAttribute(o))
        );
        return attributes;
    }

    public Cookie findCookie(String key) {
        if (cookies == null) {
            throw new EmptyContextException("empty cookies");
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie;
            }
        }
        return null;
    }

    public String findHeader(String name) {
        if (headers == null) {
            throw new EmptyContextException("empty headers");
        }
        return headers.get(name);
    }

    public Object findAttribute(String name) {
        if (attributes == null) {
            throw new EmptyContextException("empty attributes");
        }
        return attributes.get(name);
    }

}
