package uz.master.demotest.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskDto extends GenericDto {
    private String name;
    private String description;
    private int taskOrder;
    private String level;
    private String priority;
    private Long columnId;
    private String createdAt;
    private String updatedAt;
}
