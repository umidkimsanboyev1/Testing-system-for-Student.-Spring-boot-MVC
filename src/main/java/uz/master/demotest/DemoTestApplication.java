package uz.master.demotest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.auth.AuthRole;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.repositories.AuthRoleRepository;
import uz.master.demotest.repositories.AuthUserRepository;

//@EnableJpaAuditing
@SpringBootApplication
public class DemoTestApplication/* implements CommandLineRunner*/ {


    private final AuthUserRepository authUserRepository;
    private final AuthRoleRepository authRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public DemoTestApplication(AuthUserRepository authUserRepository,
                               AuthRoleRepository authRoleRepository,
                               PasswordEncoder passwordEncoder
    ) {
        this.authUserRepository = authUserRepository;
        this.authRoleRepository = authRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }

    @Transactional(timeout = 10)
    public void run(String... args) throws Exception {
        AuthUser admin = new AuthUser();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("123"));
        System.out.println(admin.getPassword());
        AuthRole adminRole = authRoleRepository.findAuthRoleByCode("ADMIN").get();
        admin.setRole(adminRole);


        authUserRepository.save(admin);


        AuthUser manager = new AuthUser();
        manager.setUsername("user");
        manager.setPassword(passwordEncoder.encode("user123"));
        AuthRole managerRole = authRoleRepository.findAuthRoleByCode("USER").get();
        manager.setRole(managerRole);
        authUserRepository.save(manager);

    }
}
