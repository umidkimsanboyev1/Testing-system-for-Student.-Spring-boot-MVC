package uz.master.demotest.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.master.demotest.entity.result.OverAllResult;

import javax.transaction.Transactional;
import java.util.List;


public interface OverAllResultRepository extends JpaRepository<OverAllResult, Long> {

    OverAllResult findByTakerUserAndTestId(String takerUser, Long testId);
    boolean existsByTakerUserAndTestId(String takerUser, Long testId);

    @Query("SELECT DISTINCT o.groupName FROM OverAllResult o where o.testId = ?1 order by o.groupName")
    List<String> findDistinctGroupNamesAndTestId(Long testId);

    @Transactional
    List<OverAllResult> findOverAllResultsByTestIdOrderByTakerUser(Long id);
    List<OverAllResult> findOverAllResultsByTestIdAndTakerUserIdOrderByPassedTime(Long testId, Long TakerId);
    @Transactional
    List<OverAllResult> findOverAllResultsByTestIdAndGroupNameOrderByTakerUser(Long id, String groupName);


}
