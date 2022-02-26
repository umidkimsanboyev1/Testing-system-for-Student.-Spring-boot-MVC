package uz.master.demotest.dto.column;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import uz.master.demotest.dto.BaseDto;
import uz.master.demotest.mappers.BaseMapper;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class ColumnCreateDto implements BaseDto {

    @Length(min = 3)
    private String name;
    @Length(min = 1)
    private Long projectId;
}
