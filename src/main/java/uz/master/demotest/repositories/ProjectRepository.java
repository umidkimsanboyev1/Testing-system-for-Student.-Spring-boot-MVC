package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.project.Project;

public interface ProjectRepository extends JpaRepository<Project,Long> {
}
