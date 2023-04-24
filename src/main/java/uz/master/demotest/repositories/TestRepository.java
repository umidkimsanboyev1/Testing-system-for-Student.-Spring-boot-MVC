package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.test.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {

    List<Test> findAllByActiveTrue();
    List<Test> findAllByDeletedFalseOrderById();

    List<Test> findAllByDeletedFalseAndOwnerIdOrderById (Long id);
}
