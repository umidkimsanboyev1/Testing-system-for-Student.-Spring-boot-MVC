package uz.master.demotest;

import com.poiji.bind.Poiji;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.master.demotest.dto.excel.QuestionsExcel;
import uz.master.demotest.repositories.AuthRoleRepository;
import uz.master.demotest.repositories.AuthUserRepository;

import java.io.File;
import java.util.List;

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

    public static void main2(String[] args) {
        SpringApplication.run(DemoTestApplication.class, args);
    }

    public static void main(String[] args) {
            File file = new File("D:/JavaProject/tets/AtomsProject/src/main/resources/files/1682425537361.xlsx");
            List<QuestionsExcel> questions = Poiji.fromExcel(file, QuestionsExcel.class);
        for (QuestionsExcel question : questions) {
            System.out.println(question);
        }
    }


}
