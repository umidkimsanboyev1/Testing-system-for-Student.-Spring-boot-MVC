package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.test.TestCreateDto;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.mappers.TestMapper;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.SubjectRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.services.FileStorageService;
import uz.master.demotest.utils.SessionUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final TestRepository testRepository;
    private final AuthUserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestMapper testMapper;
    private final FileStorageService fileStorageService;

    private final SessionUser sessionUser;

    public TestService(TestRepository repository, AuthUserRepository userRepository, SubjectRepository subjectRepository, TestMapper testMapper, FileStorageService fileStorageService, SessionUser sessionUser) {
        this.testRepository = repository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.testMapper = testMapper;
        this.fileStorageService = fileStorageService;
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

    public Long createTest(TestCreateDto dto) {
        Test test = testMapper.toEntity(dto);
        test.setTimeForAllQues(dto.getTimeForOneQues() * dto.getNumberOfQuestion());
        test.setOwnerId(sessionUser.getId());
        test.setSubjectId(subjectRepository.findSubjectByName(dto.getSubject()).getId());
        test.setActive(false);
        Test save = testRepository.save(test);
        System.out.println(save.getId());
        return save.getId();
    }

    public void delete(Long id) {
        testRepository.testDeleteById(id);
    }

    public String getTestById(Long id) {
        System.out.println(id);
        String name = testRepository.findById(id).get().getName();
        System.out.println(name);
        return name;
    }

    public void restoreExcelFileToTest(Long id, MultipartFile file) {
        String store = fileStorageService.store(file);
        Test test = testRepository.findById(id).get();
        test.setFile(store);
        testRepository.save(test);
        uploadTestFromFiles(test);
    }

    private void uploadTestFromFiles(Test test) {
        String file = test.getFile();
        String fullPath = "/JavaProject/tets/AtomsProject/src/main/resources/files" + file;


    }

}
