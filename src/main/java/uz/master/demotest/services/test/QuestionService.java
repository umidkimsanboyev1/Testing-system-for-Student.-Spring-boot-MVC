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
        for (int i = 1; i <= test.getNumberOfQuestion(); i++) {
            SendQuestion tempSendQuestion = new SendQuestion();
            List<Integer> randomNumbersList = getRandomNumbersListForRepo(test.getNumberOfQuestion());
            for (Integer random : randomNumbersList) {
                Question byIdAndTestId = questionRepository.findByIdAndTestId(Long.valueOf(random), testId);
                tempSendQuestion.setQuestion(byIdAndTestId.getText());
                tempSendQuestion.setQuestionId(byIdAndTestId.getId());
                tempSendQuestion.setTakerId(sessionUser.getId());
                tempSendQuestion.setTestId(test.getId());
                replaceAnswers(tempSendQuestion, byIdAndTestId);
                tempSendQuestion.setGeneratedQuestionNumber(i);
            }
            sendQuestionRepository.save(tempSendQuestion);
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

    public SendQuestion getQuestionForSessionUse(Integer i) {
        return sendQuestionRepository.findByGeneratedQuestionNumberAndTestIdAndTakerId(i, sessionUser.getTestId(), sessionUser.getId());
    }
}
