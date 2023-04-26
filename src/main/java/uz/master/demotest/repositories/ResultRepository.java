package uz.master.demotest.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.result.OverAllResult;

public interface ResultRepository extends JpaRepository<OverAllResult, Long> {

    OverAllResult findByTakerUser(String fullName);
    boolean existsByTakerUserAndTestId(String takerUser, Long testId);
}
