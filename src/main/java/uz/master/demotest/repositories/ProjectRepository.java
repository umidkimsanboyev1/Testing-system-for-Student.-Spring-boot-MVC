package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.master.demotest.dto.project.ProjectDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.entity.project.Project;

import javax.transaction.Transactional;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {
    Project findByIdAndDeletedFalse(Long id);

    List<Project> findAllByOrgIdAndDeletedFalse(Long id);
    List<Project> findAllByDeletedFalse();

    @Transactional
    @Modifying
    @Query("update Project p set p.deleted = true where p.id = :id")
    void delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "update Project p set p.name = #{#dto.name}, p.description = #{#dto.description}, p.tz = #{#dto.tz}, p.orgId = #{#dto.orgId}  where p.id = #{#dto.id}",nativeQuery = true)
    void update(@Param("dto") ProjectUpdateDto dto);

    @Query("select u.teamLeaderId from  Project u where u.id=:id")
    Long getTeamLead(@Param("id") Long projectId);

}
