package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.Groups;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Groups, Long> {

    List<Groups> findAllByDeletedFalseOrderByIdDesc();
    List<Groups> findGroupsByName(String name);
}
