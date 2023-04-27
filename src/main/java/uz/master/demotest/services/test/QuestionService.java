package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.entity.test.Question;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.repositories.QuestionRepository;
import uz.master.demotest.repositories.SendQuestionRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.utils.SessionUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuestionService {

    private final TestRepository testRepository;
    private final SessionUser sessionUser;
    private final QuestionRepository questionRepository;
    private final SendQuestionRepository sendQuestionRepository;

    public QuestionService(TestRepository testRepository, SessionUser sessionUser, QuestionRepository questionRepository, SendQuestionRepository sendQuestionRepository) {
        this.testRepository = testRepository;
        this.sessionUser = sessionUser;
        this.questionRepository = questionRepository;
        this.sendQuestionRepository = sendQuestionRepository;
    }

    public void startTest(Long testId) {
        Test test = testRepository.findById(testId).get();
        generateQuestionSet(test);
    }

    private void generateQuestionSet(Test test) {
        Long testId = test.getId();
        Integer counter = 1;
        Integer maxNumberOfTestQuestion = questionRepository.countAllByTestId(testId);
        List<Integer> randomNumbersList = getRandomNumbersListForRepo(maxNumberOfTestQuestion);
        SendQuestion tempSendQuestion;
            for (Integer random : randomNumbersList) {
                if(counter > test.getNumberOfQuestion()){
                    break;
                }
                Question byIdAndTestId = questionRepository.findByNumberAndTestId(Long.valueOf(random), testId);
                tempSendQuestion = new SendQuestion();
                tempSendQuestion.setQuestionId(byIdAndTestId.getId());
                tempSendQuestion.setTestId(test.getId());
                tempSendQuestion.setQuestion(byIdAndTestId.getText());
                replaceAnswers(tempSendQuestion, byIdAndTestId);
                tempSendQuestion.setTakerId(sessionUser.getId());
                tempSendQuestion.setGeneratedQuestionNumber(counter);
                sendQuestionRepository.save(tempSendQuestion);
                counter++;
            }

    }

    private void replaceAnswers(SendQuestion tempSendQuestion, Question byIdAndTestId) {
        List<Integer> randomNumbersList = getRandomNumbersListForAnswer(4);
        List<String> listAnswers = new ArrayList<>();
        listAnswers.add(byIdAndTestId.getCorrectAnswer());
        listAnswers.add(byIdAndTestId.getAnswer2());
        listAnswers.add(byIdAndTestId.getAnswer3());
        listAnswers.add(byIdAndTestId.getAnswer4());
        tempSendQuestion.setAnswer1(listAnswers.get(randomNumbersList.get(0)));
        tempSendQuestion.setAnswer2(listAnswers.get(randomNumbersList.get(1)));
        tempSendQuestion.setAnswer3(listAnswers.get(randomNumbersList.get(2)));
        tempSendQuestion.setAnswer4(listAnswers.get(randomNumbersList.get(3)));
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
    private List<Integer> getRandomNumbersListForAnswer(Integer max) {
        ArrayList<Integer> numbers = new ArrayList<>();
        Random randomGenerator = new Random();
        while (numbers.size() < max) {

            int random = randomGenerator.nextInt(max);
            if (!numbers.contains(random)) {
                numbers.add(random);
            }
        }
        System.out.println(numbers);
        return numbers;
    }

    public SendQuestion getQuestionForSessionUser(Integer i) {
        Long testId = sessionUser.getTestId();
        Long id = sessionUser.getId();
        SendQuestion sendQuestion = sendQuestionRepository.findSendQuestionByGeneratedQuestionNumberAndTestIdAndTakerId(i, testId, id);
        return sendQuestion;
    }

    public boolean checkTestProgress(Integer generatedNumber) {
        return true;
    }
}
