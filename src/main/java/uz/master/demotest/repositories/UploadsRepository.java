package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.action.Uploads;

import java.util.Optional;

public interface UploadsRepository extends JpaRepository<Uploads,Long> {
    Optional<Uploads>findByGeneratedName(String filename);
}
