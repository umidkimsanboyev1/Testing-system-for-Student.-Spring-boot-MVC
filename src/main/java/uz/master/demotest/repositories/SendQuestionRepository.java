package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.test.SendQuestion;

import java.util.List;
import java.util.Optional;

public interface SendQuestionRepository extends JpaRepository<SendQuestion, Long> {

    SendQuestion getSendQuestionByTakerIdAndTestIdAndGeneratedQuestionNumber(Long takerId, Long testId, Integer generatedQuestionNumber);
    List<SendQuestion> findSendQuestionByTestIdAndTakerId(Long testId, Long takerId);

}
