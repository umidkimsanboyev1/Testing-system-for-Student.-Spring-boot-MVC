package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import uz.master.demotest.dto.task.TaskUpdateDto;
import uz.master.demotest.entity.task.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    Task findByIdAndDeletedFalse(Long id);

    List<Task> findAllByDeletedFalse();

    List<Task> findAllByColumnIdAndDeletedFalse(Long columnId);

    @Transactional
    @Modifying
    @Query(value = "Update Tasks t SET t.deleted = true WHERE t.id=:id")
    void delete(@Param("id") Long id);

    @Transactional
    @Modifying
    @Query(value = "Update Tasks t SET t.name =#{#dto.name},t.description =#{#dto.description}," +
            "t.taskOrder =#{#dto.taskOrder},t.priority =#{#dto.priority},t.level =#{#dto.level},t.columnId =#{#dto.columnId} WHERE t.id=#{#dto.id}")
    void update(@Param("dto") TaskUpdateDto dto);

    @Modifying
    @Query(value = "insert into task_member (user_id,task_id) VALUES (:memberId,:taskId)", nativeQuery = true)
    @Transactional
    void addMember(@Param("taskId") Long taskId, @Param("memberId") Long memberId);

    @Modifying
    @Query("delete from Task_Member b where b.taskId=:taskId and b.userId=:membetId")
    @Transactional
    void deleteMember(@Param("taskId") Long taskId, @Param("memberId") Long memberId);
}
