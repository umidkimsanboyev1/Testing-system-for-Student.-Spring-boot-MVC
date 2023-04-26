package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.master.demotest.entity.test.Question;

import javax.transaction.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Question findByIdAndTestId(Long id, Long testId);
}
