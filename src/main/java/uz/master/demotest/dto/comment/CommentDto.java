package uz.master.demotest.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto extends GenericDto {

    private String text;

    private String authorUsername;

    private Long taskId;

    private LocalDateTime createdAt;


}
