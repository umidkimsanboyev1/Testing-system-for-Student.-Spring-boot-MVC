package uz.master.demotest.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.result.OverAllResult;

import java.util.List;


public interface OverAllResultRepository extends JpaRepository<OverAllResult, Long> {

    OverAllResult findByTakerUser(String fullName);
    boolean existsByTakerUserAndTestIdAndCompletedFalse(String takerUser, Long testId);

    List<OverAllResult> findOverAllResultsByTestIdAndCompletedTrueOrderByPassedTime(Long id);
}
