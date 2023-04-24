package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.mappers.TestMapper;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.SubjectRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.utils.SessionUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final AuthUserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestMapper testMapper;

    private final SessionUser sessionUser;

    public TestService(TestRepository repository, AuthUserRepository userRepository, SubjectRepository subjectRepository, TestMapper testMapper, SessionUser sessionUser) {
        this.testRepository = repository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.testMapper = testMapper;
        this.sessionUser = sessionUser;
    }

    public List<TestDto> getAllTests() {
        List<Test> allTests = testRepository.findAllByDeletedFalseOrderById();
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : allTests) {
            TestDto testDto = testMapper.toDto(test);
            testDto.setOwner(userRepository.findById(test.getOwnerId()).get().getFullName());
            testDto.setSubject(subjectRepository.findById(test.getSubjectId()).get().getName());
            testDtos.add(testDto);
        }
        return testDtos;
    }

    public void doAction(Long id) {
        Test test = testRepository.findById(id).get();
        if (test.isActive())
            test.setActive(false);
        else
            test.setActive(true);
        testRepository.save(test);
    }

    public List<TestDto> getMyOwnerTests() {
        List<Test> allTests = testRepository.findAllByDeletedFalseAndOwnerIdOrderById(sessionUser.getId());
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : allTests) {
            TestDto testDto = testMapper.toDto(test);
            testDto.setSubject(subjectRepository.findById(test.getSubjectId()).get().getName());
            testDtos.add(testDto);
        }
        return testDtos;
    }
}
