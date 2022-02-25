package uz.master.demotest.dto.column;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import uz.master.demotest.dto.GenericDto;
import uz.master.demotest.dto.task.TaskDto;
import uz.master.demotest.entity.task.Task;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ColumnDto extends GenericDto {

    private Long id;
    private String name;
    private int columnOrder;
    private Long projectId;
    private List<TaskDto> taskDtos = new ArrayList<>(0);

}
