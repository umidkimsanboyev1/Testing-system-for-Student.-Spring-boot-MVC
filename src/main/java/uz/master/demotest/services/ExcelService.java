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

    public void saveTestDataFromExcel(Test test) {
        Long testId = test.getId();
        List<QuestionsExcel> questionsExcels = convertExcelToEntity(test.getFile());
        for (QuestionsExcel questionsExcel : questionsExcels) {
            Question tempQuestion = new Question();
            tempQuestion.setTestId(testId);
            tempQuestion.setText(questionsExcel.getText());
            tempQuestion.setNumber(questionsExcel.getNumber());
            tempQuestion.setCorrectAnswer(questionsExcel.getCorrectAnswer());
            mapOtherAnswers(tempQuestion, questionsExcel);
            questionRepository.save(tempQuestion);
        }
    }

    private void mapOtherAnswers(Question tempQuestion, QuestionsExcel questionsExcel) {
        for (int i = 0; i < 3; i++) {
            tempQuestion.setAnswer2(questionsExcel.getAnswer2());
            tempQuestion.setAnswer3(questionsExcel.getAnswer3());
            tempQuestion.setAnswer4(questionsExcel.getAnswer4());
        }
    }
}
