package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.comment.CommentCreateDto;
import uz.master.demotest.dto.comment.CommentDto;
import uz.master.demotest.dto.comment.CommentUpdateDto;
import uz.master.demotest.entity.comment.Comment;

/**
 * @author Bekpulatov Shoxruh, Thu 11:56 AM. 2/24/2022
 */
@Component
@Mapper(componentModel = "spring")
public interface CommentMapper extends BaseMapper<Comment, CommentDto, CommentCreateDto, CommentUpdateDto> {
    CommentUpdateDto toUpdateDto(CommentDto commentDto);
}
