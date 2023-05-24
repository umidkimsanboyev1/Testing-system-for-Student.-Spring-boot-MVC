package uz.master.demotest.configs;

import org.springframework.context.annotation.Configuration;
import uz.master.demotest.dto.test.ResultDto;
import uz.master.demotest.services.test.TestService;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Configuration
public class SessionListener implements HttpSessionListener {
    private final TestService testService;

    public SessionListener(TestService testService) {
        this.testService = testService;
    }


}