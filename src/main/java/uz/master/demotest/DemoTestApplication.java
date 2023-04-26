package uz.master.demotest;

import com.poiji.bind.Poiji;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.master.demotest.dto.excel.QuestionsExcel;
import uz.master.demotest.repositories.AuthRoleRepository;
import uz.master.demotest.repositories.AuthUserRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@EnableJpaAuditing
@SpringBootApplication
public class DemoTestApplication {


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

    public static void main2(String[] args) {
        System.out.println("boshlandi");
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Random randomGenerator = new Random();
        while (numbers.size() < 4) {

            int random = randomGenerator .nextInt(4) + 1;
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        System.out.println(numbers);
        System.out.println("tugadi");
    }


}
