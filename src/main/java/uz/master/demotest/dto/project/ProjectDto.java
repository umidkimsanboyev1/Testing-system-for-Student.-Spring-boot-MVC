package uz.master.demotest.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;
import uz.master.demotest.dto.column.ColumnDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectDto extends GenericDto {
    private String name;
    private String description;
    private String tz;
    private String organizationName;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
    private List<ColumnDto> columns = new ArrayList<>();
    private int projectMembersCount = 0;


    public Integer getTasksCount() {
        int countOfTasks = 0;


        for (ColumnDto column : columns) {
            countOfTasks += column.getTaskDtos().size();
        }

        return countOfTasks;

    }

    public Integer getDoneTasksCount() {
        int countOfTasks = 0;


        for (ColumnDto column : columns) {
            if (column.getName().equals("Done"))
                countOfTasks += column.getTaskDtos().size();
        }

        return countOfTasks;
    }

    public Integer getFrozenTasksCount() {
        int countOfTasks = 0;


        for (ColumnDto column : columns) {
            if (column.getName().equals("Frozen"))
                countOfTasks += column.getTaskDtos().size();
        }

        return countOfTasks;
    }
}
