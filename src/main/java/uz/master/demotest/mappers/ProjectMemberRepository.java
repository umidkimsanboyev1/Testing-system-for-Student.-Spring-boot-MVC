package uz.master.demotest.mappers;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.project.ProjectMember;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember,Long> {
}
