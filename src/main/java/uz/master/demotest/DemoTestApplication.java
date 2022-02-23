package uz.master.demotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DemoTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }

}
