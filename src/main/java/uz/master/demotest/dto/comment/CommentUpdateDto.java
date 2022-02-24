package uz.master.demotest.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

/**
 * @author Bekpulatov Shoxruh, Thu 11:54 AM. 2/24/2022
 */
@Getter
@Setter
@AllArgsConstructor
public class CommentUpdateDto extends GenericDto {
    private String text;
}
