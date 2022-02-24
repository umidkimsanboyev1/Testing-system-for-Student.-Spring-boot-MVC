package uz.master.demotest.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;
import uz.master.demotest.dto.column.ColumnDto;
import uz.master.demotest.entity.organization.Organization;

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
    private List<ColumnDto> columns;
    private int projectMembersCount;
}
