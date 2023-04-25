package uz.master.demotest.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.test.Subject;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Subject findSubjectByName(String name);
    List<Subject> findAllByDeletedFalse();
}
