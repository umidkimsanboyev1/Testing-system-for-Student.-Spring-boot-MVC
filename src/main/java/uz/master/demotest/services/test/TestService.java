package uz.master.demotest.services.test;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.test.*;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
import uz.master.demotest.mappers.TestMapper;
import uz.master.demotest.repositories.*;
import uz.master.demotest.services.ExcelService;
import uz.master.demotest.services.FileStorageService;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TestService {

    private final uz.master.demotest.repositories.TestRepository testRepository;
    private final AuthUserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TestMapper testMapper;
    private final ExcelService excelService;
    private final SendQuestionRepository sendQuestionRepository;
    private final FileStorageService fileStorageService;
    private final OverAllResultRepository overAllResultRepository;

    private final SessionUser sessionUser;

    public TestService(TestRepository repository, AuthUserRepository userRepository, SubjectRepository subjectRepository, TestMapper testMapper, ExcelService excelService, SendQuestionRepository sendQuestionRepository, FileStorageService fileStorageService, OverAllResultRepository overAllResultRepository, SessionUser sessionUser) {
        this.testRepository = repository;
        this.userRepository = userRepository;
        this.subjectRepository = subjectRepository;
        this.testMapper = testMapper;
        this.excelService = excelService;
        this.sendQuestionRepository = sendQuestionRepository;
        this.fileStorageService = fileStorageService;
        this.overAllResultRepository = overAllResultRepository;
        this.sessionUser = sessionUser;
    }

    public List<TestDto> getAllTests() {
        List<Test> allTests = testRepository.findAllByDeletedFalseOrderByIdDesc();
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : allTests) {
            TestDto testDto = testMapper.toDto(test);
            testDto.setAllQuestion(test.getAllQuestion());
            testDto.setOwner(userRepository.findById(test.getOwnerId()).get().getFullName());
            testDtos.add(testDto);
        }
        return testDtos;
    }

    public void doAction(Long id) {
        Test test = testRepository.findById(id).get();
        test.setActive(!test.isActive());
        testRepository.save(test);
    }

    public List<TestDto> getMyOwnerTests() {
        List<Test> allTests = testRepository.findAllByDeletedFalseAndOwnerIdOrderById(sessionUser.getId());
        List<TestDto> testDtos = new ArrayList<>();
        for (Test test : allTests) {
            TestDto testDto = testMapper.toDto(test);
            testDtos.add(testDto);
            testDto.setAllQuestion(test.getAllQuestion());
        }
        return testDtos;
    }

    public Long createTest(TestCreateDto dto) {
        AuthUser authUser = userRepository.findById(sessionUser.getId()).get();
        if(testRepository.existsTestByNameAndDeletedFalse(dto.getName())){
            throw new RuntimeException();
        }
        String patternForDateTime = "dd.MM.yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternForDateTime);
        Test test = testMapper.toEntity(dto);
        test.setTimeForAllQues(dto.getTimeForOneQues());
        test.setOwnerId(sessionUser.getId());
        test.setCreatedTime(LocalDateTime.now().format(formatter));
        test.setActive(false);
        test.setArchived(false);
        test.setOwnerName(authUser.getFullName());
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

    public boolean restoreExcelFileToTest(Long id, MultipartFile file) {
        String store = fileStorageService.store(file);
        Test test = testRepository.findById(id).get();
        test.setFile(store);
        testRepository.save(test);
        boolean b = excelService.saveTestDataFromExcel(test);
        return b;
    }


    public TestIntroductionDto getTestIntroduction(Long id) {
        Test test = testRepository.findById(id).get();
        return testMapper.toIntroductionDto(test);
    }


    public ResultDto getResult(Long id) {
        AuthUser authUser = userRepository.findById(id).get();
        Long testId = authUser.getTestId();
        int correctAnswers = collectCurrentAnswersNumber(testId, authUser.getId());
        int allQuestion = testRepository.findById(testId).get().getNumberOfQuestion();
        double efficiency = Math.ceil(((double) correctAnswers / allQuestion) * 100.0);
        setAuthUserData(authUser);
        return getResultDto(authUser, correctAnswers, allQuestion, efficiency, testId);
    }

    private ResultDto getResultDto(AuthUser authUser, Integer correctAnswers, Integer allQuestion, double efficiency, Long testId) {
        String patternForDateTime = "dd.MM.yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternForDateTime);
        OverAllResult result = overAllResultRepository.findByTakerUserAndTestId(authUser.getFullName(), testId);
        result.setCompleted(true);
        result.setNumberOfAllQues(allQuestion);
        result.setTakerUserId(authUser.getId());
        result.setEfficiency(efficiency);
        result.setGroupName(authUser.getGroupName());
        result.setCorrectAnsweredQues(correctAnswers);
        result.setPassedTime(LocalDateTime.now().format(formatter));
        ResultDto dto = new ResultDto();
        dto.setCorrectQuestion(correctAnswers);
        dto.setAllNumberOfQuestion(allQuestion);
        dto.setEfficiency(efficiency);
        overAllResultRepository.save(result);
        return dto;
    }

    private Integer collectCurrentAnswersNumber(Long testId, Long id) {
        List<SendQuestion> sendQuestionList = sendQuestionRepository.findSendQuestionByTestIdAndTakerId(testId, id);
        int correctAnswerCounter = 0;
        for (SendQuestion sendQuestion : sendQuestionList) {
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer1()) && sendQuestion.isChecked1())
                correctAnswerCounter++;
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer2()) && sendQuestion.isChecked2())
                correctAnswerCounter++;
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer3()) && sendQuestion.isChecked3())
                correctAnswerCounter++;
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer4()) && sendQuestion.isChecked4())
                correctAnswerCounter++;
        }
        return correctAnswerCounter;
    }

    private void setAuthUserData(AuthUser authUser) {
        authUser.setQuesNumber(null);
        authUser.setTestId(null);
        userRepository.save(authUser);
    }


    public List<OverAllResultDTO> getAllResults() {
        List<Test> allTests = testRepository.findAllByDeletedFalseOrderByIdDesc();
        return getOverAllResultDTOS(allTests);

    }

    public List<OverAllResultDTO> getAllMyResults(Long takerId) {
        List<Test> allTests = testRepository.findAllByDeletedFalseOrderByIdDesc();
        return getMyOverAllResultDTOS(allTests, takerId);

    }

    @NotNull
    private List<OverAllResultDTO> getOverAllResultDTOS(List<Test> allTests) {
        List<OverAllResultDTO> results = new ArrayList<>();
        for (Test test : allTests) {
            Long id = test.getId();
            OverAllResultDTO dto = new OverAllResultDTO();
            dto.setTest(test);
            List<OverAllResult> overAllResultsByTestId = overAllResultRepository.findOverAllResultsByTestIdOrderByTakerUser(id);
            for (OverAllResult overAllResult : overAllResultsByTestId) {
                overAllResult.setEfficiency( Math.ceil(overAllResult.getEfficiency()));
            }
            dto.setResults(overAllResultsByTestId);
            results.add(dto);
        }
        return results;
    }

    @NotNull
    private List<OverAllResultDTO> getMyOverAllResultDTOS(List<Test> allTests, Long takerId) {
        List<OverAllResultDTO> results = new ArrayList<>();
        for (Test test : allTests) {
            Long id = test.getId();
            OverAllResultDTO dto = new OverAllResultDTO();
            AuthUser authUser = userRepository.findById(sessionUser.getId()).get();
            if(overAllResultRepository.existsByTakerUserAndTestId(authUser.getFullName(), id)){
                dto.setTest(test);
                List<OverAllResult> overAllResultsByTestId = overAllResultRepository.findOverAllResultsByTestIdAndTakerUserIdOrderByPassedTime(id, takerId);
                dto.setResults(overAllResultsByTestId);
                results.add(dto);
            }
        }
        return results;
    }
//    public List<OverAllResultDTO> getAllResultsForTeacher() {
//        List<Test> allTests = testRepository.findTestsByDeletedFalseAndOwnerId(sessionUser.getId());
//        return getOverAllResultDTOS(allTests);
//    }

    public List<Test> getAllResultsForTeacher() {
        return testRepository.findTestsByDeletedFalseAndOwnerId(sessionUser.getId());
    }


    public List<OverAllResultDTO> getAllResultsByGroup(Long id, String groupName) {
        AuthUser authUser = userRepository.findById(id).get();
        List<Test> testByOwner;
        if(Role.TEACHER.equals(authUser.getRole())){
            testByOwner = testRepository.findTestsByDeletedFalseAndOwnerId(id);
        } else {
            testByOwner = testRepository.findTestsByDeletedFalse();
        }
        List<OverAllResultDTO> results = new ArrayList<>();
        for (Test test : testByOwner) {
            Long testId = test.getId();
            OverAllResultDTO dto = new OverAllResultDTO();
            dto.setTest(test);
            List<OverAllResult> overAllResultsByTestId = overAllResultRepository.findOverAllResultsByTestIdAndGroupNameOrderByTakerUser(testId, groupName);
            dto.setResults(overAllResultsByTestId);
            results.add(dto);
        }
        return results;
    }

    public Test getTest(Long id) {
        Test test = testRepository.findById(id).get();
        return test;
    }

    public void updateTest(Test dto) {
        Test test = testRepository.findById(dto.getId()).get();
        Optional<Test> byName = testRepository.findByName(dto.getName());
        if (byName.isPresent() && !byName.get().getId().equals(test.getId())) {
            throw new RuntimeException();
        }
        if(test.getAllQuestion() != null && test.getAllQuestion() < test.getNumberOfQuestion()){
            throw new RuntimeException();
        }
        test.setName(dto.getName());
        test.setNumberOfQuestion(dto.getNumberOfQuestion());
        test.setTimeForOneQues(dto.getTimeForOneQues());
        testRepository.save(test);
    }

    public List<OverAllResult> getOverAllResultByTestId(Long testId) {
        List<OverAllResult> overAllResultsByTestIdOrderByTakerUser = overAllResultRepository.findOverAllResultsByTestIdOrderByTakerUser(testId);
        for (OverAllResult overAllResult : overAllResultsByTestIdOrderByTakerUser) {
            overAllResult.setEfficiency( Math.ceil(overAllResult.getEfficiency()));
        }
        return overAllResultsByTestIdOrderByTakerUser;
    }

    public List<OverAllResult> getOverAllResultByTestIdAndGroupName(Long id, String groupName) {
        List<OverAllResult> results = overAllResultRepository.findOverAllResultsByTestIdAndGroupNameOrderByTakerUser(id, groupName);
        return results;
    }

    public List<Test> getAllTest() {
        return testRepository.findAllByDeletedFalse();
    }

    public List<OverAllResult> getOverAllResultByTestIdAndGroupNameForTeacher(Long testId, String groupName) {
        if(!testRepository.findById(testId).get().getOwnerId().equals(sessionUser.getId())){
            throw new RuntimeException("Test egasi bo'lmagan odamdan murojaat");
        }
        List<OverAllResult> results = overAllResultRepository.findOverAllResultsByTestIdAndGroupNameOrderByTakerUser(testId, groupName);
        return results;
    }

    public boolean haveTestForStudent(AuthUser authUser) {
        if(authUser.getTestId() != null && authUser.getTime().isBefore(LocalDateTime.now())){
            if(overAllResultRepository.existsByTakerUserAndTestId(authUser.getFullName(), authUser.getTestId())){
                return true;
            }
        }
        return false;
    }
}
