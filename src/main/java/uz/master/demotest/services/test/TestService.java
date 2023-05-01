package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.test.*;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.mappers.TestMapper;
import uz.master.demotest.repositories.*;
import uz.master.demotest.services.ExcelService;
import uz.master.demotest.services.FileStorageService;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        test.setActive(!test.isActive());
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


    public ResultDto getResult() {
        AuthUser authUser = userRepository.findById(sessionUser.getId()).get();
        Long testId = authUser.getTestId();
        int correctAnswers = collectCurrentAnswersNumber(testId, authUser.getId());
        int allQuestion = testRepository.findById(testId).get().getNumberOfQuestion();
        double efficiency = ((double) correctAnswers / allQuestion) * 100.0;
        setAuthUserData(authUser);
        return getResultDto(authUser, correctAnswers, allQuestion, efficiency);
    }

    private ResultDto getResultDto(AuthUser authUser, Integer correctAnswers, Integer allQuestion, double efficiency) {
        String patternForDateTime = "dd.MM.yyyy HH:mm:ss";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patternForDateTime);
        OverAllResult result = overAllResultRepository.findByTakerUser(authUser.getFullName());
        result.setCompleted(true);
        result.setNumberOfAllQues(allQuestion);
        result.setTakerUserId(authUser.getId());
        result.setEfficiency(efficiency);
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
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer1()) && sendQuestion.isChecked1()) {
                correctAnswerCounter++;
            }
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer2()) && sendQuestion.isChecked2()) {
                correctAnswerCounter++;
            }
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer3()) && sendQuestion.isChecked3()) {
                correctAnswerCounter++;
            }
            if (sendQuestion.getCorrectAnswer().equals(sendQuestion.getAnswer4()) && sendQuestion.isChecked4()) {
                correctAnswerCounter++;
            }
        }
        return correctAnswerCounter;
    }

    private void setAuthUserData(AuthUser authUser) {
        authUser.setQuesNumber(null);
        authUser.setTestId(null);
        userRepository.save(authUser);
    }


    public List<OverAllResultDTO> getAllResults() {

        List<Test> allTests = testRepository.findTestsByActiveTrueAndDeletedFalse();
        List<OverAllResultDTO> results = new ArrayList<>();
        for (Test test : allTests) {
            Long id = test.getId();
            OverAllResultDTO dto = new OverAllResultDTO();
            dto.setTest(test);
            List<OverAllResult> overAllResultsByTestId = overAllResultRepository.findOverAllResultsByTestIdAndCompletedTrueOrderByPassedTime(id);
            dto.setResults(overAllResultsByTestId);
            results.add(dto);
        }
        return results;

    }
}
