package uz.master.demotest.services.student;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.TestRepository;

import java.util.List;


@Service
public class StudentService {

    private final AuthUserRepository userRepository;
    private final TestRepository testRepository;


    public StudentService(AuthUserRepository userRepository, TestRepository testRepository) {
        this.userRepository = userRepository;
        this.testRepository = testRepository;
    }

    public List<Test> getTestList() {
        return testRepository.findAllByActiveTrue();
    }
}
