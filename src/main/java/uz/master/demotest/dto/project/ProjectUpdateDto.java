package uz.master.demotest.dto.project;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectUpdateDto extends GenericDto {
    private String name;
    private String description;
    private String tz;
    private Long orgId;
}
