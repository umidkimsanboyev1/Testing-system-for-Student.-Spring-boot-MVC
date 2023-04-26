package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.test.TestCreateDto;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.dto.test.TestIntroductionDto;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.mappers.TestMapper;
import uz.master.demotest.repositories.*;
import uz.master.demotest.services.ExcelService;
import uz.master.demotest.services.FileStorageService;
import uz.master.demotest.utils.SessionUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestService {

    private final uz.master.demotest.repositories.TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AuthUserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestMapper testMapper;
    private final ExcelService excelService;
    private final SendQuestionRepository sendQuestionRepository;
    private final FileStorageService fileStorageService;

    private final SessionUser sessionUser;

    public TestService(uz.master.demotest.repositories.TestRepository repository, QuestionRepository questionRepository, AuthUserRepository userRepository, SubjectRepository subjectRepository, TestMapper testMapper, ExcelService excelService, SendQuestionRepository sendQuestionRepository, FileStorageService fileStorageService, SessionUser sessionUser) {
        this.testRepository = repository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.testMapper = testMapper;
        this.excelService = excelService;
        this.sendQuestionRepository = sendQuestionRepository;
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
        excelService.saveTestDataFromExcel(test);
    }


    public TestIntroductionDto getTestIntroduction(Long id) {
        Test test = testRepository.findById(id).get();
        return testMapper.toIntroductionDto(test);
    }


}
