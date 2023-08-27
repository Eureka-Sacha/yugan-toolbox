package one.yugan.controller;

import one.yugan.request.RequestContextManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static one.yugan.request.RequestContextConstants.TARGET_ATTRIBUTE;
import static one.yugan.request.RequestContextConstants.TARGET_COOKIE;
import static one.yugan.request.RequestContextConstants.TARGET_HEADER;

/**
 * @author : yugan.
 * @date : 2023/8/26
 * @email : eureka_sacha@outlook.com
 */
@RestController("test")
public class TestController {

    static RequestContextManager contextManager = RequestContextManager.getInstance();

    @GetMapping("context")
    public String test() {
        System.out.println(contextManager.getCurrent().getAll(TARGET_HEADER));
        System.out.println(contextManager.getCurrent().getAll(TARGET_COOKIE));
        System.out.println(contextManager.getCurrent().getAll(TARGET_ATTRIBUTE));

        return contextManager.getCurrent().findValue(TARGET_HEADER, "content-type").toString();
    }
}
