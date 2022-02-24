package uz.master.demotest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.master.demotest.entity.column.ProjectColumn;

public interface ColumnRepository extends JpaRepository<ProjectColumn,Long> {
}
