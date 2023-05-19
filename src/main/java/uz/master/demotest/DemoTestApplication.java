package uz.master.demotest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.master.demotest.repositories.AuthRoleRepository;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.QuestionRepository;

//@EnableJpaAuditing
@SpringBootApplication
public class DemoTestApplication {


    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final QuestionRepository questionRepository;

    public DemoTestApplication(AuthUserRepository authUserRepository,
                               AuthRoleRepository authRoleRepository,
                               PasswordEncoder passwordEncoder,
                               QuestionRepository questionRepository) {
        this.authUserRepository = authUserRepository;
        this.authRoleRepository = authRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.questionRepository = questionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }


}
