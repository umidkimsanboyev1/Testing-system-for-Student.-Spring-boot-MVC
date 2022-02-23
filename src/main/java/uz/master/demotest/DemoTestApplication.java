package uz.master.demotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.master.demotest.repositories.UserRepository;

@EnableJpaAuditing
@SpringBootApplication
public class DemoTestApplication {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public DemoTestApplication(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }

}
