package uz.master.demotest.dto.column;

import org.hibernate.validator.constraints.Length;
import uz.master.demotest.dto.GenericDto;

public class ColumnUpdateDto extends GenericDto {

    @Length(min = 1)
    private Long id;

    @Length(min = 3)
    private String name;

    @Length(min = 1)
    private int columnOrder;
    @Length(min = 1)
    private Long projectId;
}
