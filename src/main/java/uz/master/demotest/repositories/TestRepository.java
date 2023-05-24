package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.master.demotest.entity.test.Test;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findAllByActiveTrue();

    List<Test> findAllByDeletedFalseOrderById();

    List<Test> findAllByDeletedFalseAndOwnerIdOrderById(Long id);

    List<Test> findTestsByDeletedFalse();

    List<Test> findTestsByDeletedFalseAndOwnerId(Long id);


    Optional<Test> findByName(String name);

    boolean existsTestByName(String name);


    @Transactional
    @Modifying
    @Query(value = "update Test a set a.deleted=true where  a.id=:id")
    void testDeleteById(Long id);
}
