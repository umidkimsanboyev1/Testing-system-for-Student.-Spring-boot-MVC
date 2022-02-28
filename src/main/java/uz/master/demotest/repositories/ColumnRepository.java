package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.entity.column.ProjectColumn;

import java.util.List;

public interface ColumnRepository extends JpaRepository<ProjectColumn,Long> {

    List<ProjectColumn>findAllByProjectIdAndDeletedFalseOrderByColumnOrder(Long id);


    @Transactional
    @Modifying
    @Query(value = "Update ProjectColumn t SET t.deleted = true WHERE t.id=:id")
    void delete(@Param("id") Long id);


    @Transactional
    @Modifying
    @Query(value = "Update ProjectColumn t SET t.deleted = true WHERE t.projectId=:id")
    void deleteAllProjectId(@Param("id") Long id);

}
