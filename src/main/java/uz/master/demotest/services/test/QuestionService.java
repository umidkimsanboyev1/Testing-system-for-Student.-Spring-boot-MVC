package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.test.CheckingDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.entity.test.Question;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.QuestionRepository;
import uz.master.demotest.repositories.SendQuestionRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final TestRepository testRepository;
    private final SessionUser sessionUser;
    private final AuthUserRepository authUserRepository;
    private final QuestionRepository questionRepository;
    private final SendQuestionRepository sendQuestionRepository;

    public QuestionService(TestRepository testRepository, SessionUser sessionUser, AuthUserRepository authUserRepository, QuestionRepository questionRepository, SendQuestionRepository sendQuestionRepository) {
        this.testRepository = testRepository;
        this.sessionUser = sessionUser;
        this.authUserRepository = authUserRepository;
        this.questionRepository = questionRepository;
        this.sendQuestionRepository = sendQuestionRepository;
    }

    public void startTest(Long testId) {
        Test test = testRepository.findById(testId).get();
        generateQuestionSet(test);
    }

    private void generateQuestionSet(Test test) {
        Long testId = test.getId();
        Long id = sessionUser.getId();
        Integer counter = 1;
        Integer maxNumberOfTestQuestion = questionRepository.countAllByTestId(testId);
        List<Integer> randomNumbersList = getRandomNumbersListForRepo(maxNumberOfTestQuestion);
            for (Integer random : randomNumbersList) {
                if(counter > test.getNumberOfQuestion()){
                    break;
                }
                Question byIdAndTestId = questionRepository.findByNumberAndTestId(Long.valueOf(random), testId);
                setValues(test, counter, byIdAndTestId, id);
                counter++;
            }

    }

    private void setValues(Test test, Integer counter, Question byIdAndTestId, Long id) {
        SendQuestion tempSendQuestion;
        tempSendQuestion = new SendQuestion();
        tempSendQuestion.setQuestionId(byIdAndTestId.getId());
        tempSendQuestion.setTestId(test.getId());
        tempSendQuestion.setQuestion(byIdAndTestId.getText());
        replaceAnswers(tempSendQuestion, byIdAndTestId);
        tempSendQuestion.setTakerId(id);
        tempSendQuestion.setGeneratedQuestionNumber(counter);
        sendQuestionRepository.save(tempSendQuestion);
    }

    private void replaceAnswers(SendQuestion tempSendQuestion, Question byIdAndTestId) {
        List<Integer> randomNumbersList = getRandomNumbersListForAnswer();
        List<String> listAnswers = new ArrayList<>();
        listAnswers.add(byIdAndTestId.getCorrectAnswer());
        listAnswers.add(byIdAndTestId.getAnswer2());
        listAnswers.add(byIdAndTestId.getAnswer3());
        listAnswers.add(byIdAndTestId.getAnswer4());
        tempSendQuestion.setCorrectAnswer(byIdAndTestId.getCorrectAnswer());
        tempSendQuestion.setAnswer1(listAnswers.get(randomNumbersList.get(0)));
        tempSendQuestion.setAnswer2(listAnswers.get(randomNumbersList.get(1)));
        tempSendQuestion.setAnswer3(listAnswers.get(randomNumbersList.get(2)));
        tempSendQuestion.setAnswer4(listAnswers.get(randomNumbersList.get(3)));
    }

    public SendQuestion getQuestionForSessionUser(Integer i) {
        Long id = sessionUser.getId();
        AuthUser authUser = authUserRepository.findById(id).get();
        return sendQuestionRepository.getSendQuestionByTakerIdAndTestIdAndGeneratedQuestionNumber(id, authUser.getTestId(), i);
    }


    private List<Integer> getRandomNumbersListForRepo(Integer max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random randomGenerator = new Random();
        while (numbers.size() < max) {

            int random = randomGenerator.nextInt(max) + 1;
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        System.out.println(numbers);
        return numbers;
    }
    private List<Integer> getRandomNumbersListForAnswer() {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random randomGenerator = new Random();
        while (numbers.size() < 4) {
            int random = randomGenerator.nextInt(4);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        System.out.println(numbers);
        return numbers;
    }


    public boolean checkTestProgress(Integer generatedNumber) {
        Long id = sessionUser.getId();
        AuthUser authUser = authUserRepository.findById(id).get();
        Test test = testRepository.findById(authUser.getTestId()).get();
        return generatedNumber <= (test.getNumberOfQuestion()) && generatedNumber >= 1;
    }

    public boolean checkToEndTest( Integer generatedNumber) {
        Long id = sessionUser.getId();
        AuthUser authUser = authUserRepository.findById(id).get();
        return testRepository.findById(authUser.getTestId()).get().getNumberOfQuestion().equals(generatedNumber);
    }

    public void mapAnswers(CheckingDto checking,
                           Long id, Integer generatedNumber) {
        Runnable task = () -> {
            AuthUser authUser = authUserRepository.findById(id).get();
            Long testId = authUser.getTestId();
            System.out.println(checking.getChecking());
            System.out.println("test: " + testId);
            System.out.println("sessionId: " + id);
            SendQuestion sendQuestion = sendQuestionRepository.getSendQuestionByTakerIdAndTestIdAndGeneratedQuestionNumber(id, testId, generatedNumber);
            System.out.println(sendQuestion);
            switch (checking.getChecking()) {
                case 1 -> {
                    sendQuestion.setChecked1(true);
                    sendQuestion.setChecked2(false);
                    sendQuestion.setChecked3(false);
                    sendQuestion.setChecked4(false);
                }
                case 2 -> {
                    sendQuestion.setChecked1(false);
                    sendQuestion.setChecked2(true);
                    sendQuestion.setChecked3(false);
                    sendQuestion.setChecked4(false);
                }
                case 3 -> {
                    sendQuestion.setChecked1(false);
                    sendQuestion.setChecked2(false);
                    sendQuestion.setChecked3(true);
                    sendQuestion.setChecked4(false);
                }
                case 4 -> {
                    sendQuestion.setChecked1(false);
                    sendQuestion.setChecked2(false);
                    sendQuestion.setChecked3(false);
                    sendQuestion.setChecked4(true);
                }
            }
            sendQuestionRepository.save(sendQuestion);
        };
        task.run();
    }


    public boolean checkTime() {
        AuthUser authUser = authUserRepository.findById(sessionUser.getId()).get();
        LocalDateTime time = authUser.getTime();
        LocalDateTime now = LocalDateTime.now();
        return !time.isAfter(now);
    }

}
