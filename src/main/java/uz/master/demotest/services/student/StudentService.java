package uz.master.demotest.services.student;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.OverAllResultRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.utils.SessionUser;

import java.util.ArrayList;
import java.util.List;


@Service
public class StudentService {

    private final AuthUserRepository userRepository;
    private final TestRepository testRepository;
    private final SessionUser sessionUser;
    private final OverAllResultRepository overAllResultRepository;


    public StudentService(AuthUserRepository userRepository, TestRepository testRepository, SessionUser sessionUser, OverAllResultRepository overAllResultRepository) {
        this.userRepository = userRepository;
        this.testRepository = testRepository;
        this.sessionUser = sessionUser;
        this.overAllResultRepository = overAllResultRepository;
    }

    public List<Test> getTestList(AuthUser authUser) {
        List<Test> result = new ArrayList<>();
        List<Test> allTests = testRepository.findAllByActiveTrueAndDeletedFalse();
        for (Test test : allTests) {
            boolean b = overAllResultRepository.existsByTakerUserAndTestId(authUser.getFullName(), test.getId());
            if(!b) result.add(test);
        }
        return result;
    }

    public boolean existTestOverStudent(AuthUser authUser) {
        return true;
    }
}
