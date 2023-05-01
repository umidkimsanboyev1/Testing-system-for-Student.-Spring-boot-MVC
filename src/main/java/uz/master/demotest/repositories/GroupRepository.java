package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.services.GroupService;

import java.util.List;

public interface GroupRepository  extends JpaRepository<Groups, Long> {

    List<Groups> findAllByDeletedFalse();
}
