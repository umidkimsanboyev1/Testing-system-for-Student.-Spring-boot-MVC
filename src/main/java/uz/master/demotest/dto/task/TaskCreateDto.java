package uz.master.demotest.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.BaseDto;

/**
 * @author Bekpulatov Shoxruh, Thu 10:43 AM. 2/24/2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskCreateDto implements BaseDto {
    private String name;
    private String description;
    private String level;
    private String priority;
    private Long columnId;
    private Long projectId;
}
