package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.test.SendQuestion;

public interface SendQuestionRepository extends JpaRepository<SendQuestion, Long> {

    boolean existsByGeneratedQuestionNumberAndTakerIdAndTestId(Integer generatedQuestionNumber, Long takerId,Long testId);

    SendQuestion findByGeneratedQuestionNumberAndTestIdAndTakerId(Integer generatedQuestionNumber, Long testId, Long takerId);

}
