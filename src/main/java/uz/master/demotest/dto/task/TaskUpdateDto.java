package uz.master.demotest.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

/**
 * @author Bekpulatov Shoxruh, Thu 10:43 AM. 2/24/2022
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class TaskUpdateDto extends GenericDto {
    private String name;
    private String description;
    private int taskOrder;
    private String level;
    private String priority;
}
