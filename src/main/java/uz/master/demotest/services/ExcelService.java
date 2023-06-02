package uz.master.demotest.services;

import com.poiji.bind.Poiji;
import org.springframework.stereotype.Service;
import uz.master.demotest.dto.excel.QuestionsExcel;
import uz.master.demotest.entity.test.Question;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.mappers.QuestionMapper;
import uz.master.demotest.repositories.QuestionRepository;
import uz.master.demotest.repositories.TestRepository;

import java.io.File;
import java.util.List;
import java.util.Objects;

@Service
public class ExcelService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public ExcelService(TestRepository testRepository, QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }


    private List<QuestionsExcel> convertExcelToEntity(String fileName){
        File file = new File("/JavaProject/tets/AtomsProject/src/main/resources/files/" + fileName);
        List<QuestionsExcel> questions = Poiji.fromExcel(file, QuestionsExcel.class);
        for (QuestionsExcel question : questions) {
            question.toList();
        }
        return questions;
    }

    public boolean saveTestDataFromExcel(Test test) {
        Long testId = test.getId();
        List<QuestionsExcel> questionsExcels = convertExcelToEntity(test.getFile());
        if(checkTest(test, questionsExcels)){
            Long count = 1L;
            for (QuestionsExcel questionsExcel : questionsExcels) {
                if(Objects.isNull(questionsExcel.getText()) || checkToNullAnswers(questionsExcel)){
                    continue;
                }
                Question tempQuestion = new Question();
                tempQuestion.setTestId(testId);
                tempQuestion.setText(questionsExcel.getText());
                tempQuestion.setNumber(count);
                tempQuestion.setCorrectAnswer(questionsExcel.getCorrectAnswer());
                mapOtherAnswers(tempQuestion, questionsExcel);
                questionRepository.save(tempQuestion);
                count++;
            }
           test.setAllQuestion(count - 1);
            testRepository.save(test);
            return true;
        }
        return false;
    }

    private boolean checkTest(Test test, List<QuestionsExcel> questionsExcels) {
        Long count = 1L;
        for (QuestionsExcel questionsExcel : questionsExcels) {
            if(Objects.isNull(questionsExcel.getText())){
                break;
            }
            if(!checkToNullAnswers(questionsExcel)){
                count++;
            }

        }
        return count >= test.getNumberOfQuestion();
    }

    private static boolean checkToNullAnswers(QuestionsExcel questionsExcel) {
        return questionsExcel.getCorrectAnswer() == null
                || questionsExcel.getAnswer2() == null
                || questionsExcel.getAnswer3() == null
                || questionsExcel.getAnswer4() == null;
    }

    private void mapOtherAnswers(Question tempQuestion, QuestionsExcel questionsExcel) {
        for (int i = 0; i < 3; i++) {
            tempQuestion.setAnswer2(questionsExcel.getAnswer2());
            tempQuestion.setAnswer3(questionsExcel.getAnswer3());
            tempQuestion.setAnswer4(questionsExcel.getAnswer4());
        }
    }
}
