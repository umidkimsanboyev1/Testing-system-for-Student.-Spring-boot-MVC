package uz.master.demotest.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto extends GenericDto {

    private String text;

    private Long authorId;

    private Long taskId;


}
