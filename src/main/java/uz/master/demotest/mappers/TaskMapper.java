package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.task.TaskCreateDto;
import uz.master.demotest.dto.task.TaskDto;
import uz.master.demotest.dto.task.TaskUpdateDto;
import uz.master.demotest.entity.task.Task;

/**
 * @author Bekpulatov Shoxruh, Thu 10:54 AM. 2/24/2022
 */
@Component
@Mapper(componentModel = "spring")
public interface TaskMapper extends BaseMapper<Task, TaskDto, TaskCreateDto, TaskUpdateDto> {

    TaskUpdateDto toUpdateDto(TaskDto taskDto);


}
