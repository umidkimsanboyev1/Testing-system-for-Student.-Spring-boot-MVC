package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.task.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
