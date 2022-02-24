package uz.master.demotest.services.comment;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.comment.CommentCreateDto;
import uz.master.demotest.dto.comment.CommentDto;
import uz.master.demotest.dto.comment.CommentUpdateDto;
import uz.master.demotest.entity.comment.Comment;
import uz.master.demotest.mappers.CommentMapper;
import uz.master.demotest.repositories.CommentRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.utils.CommentValidator;
import uz.master.demotest.utils.Validator;

import java.util.List;

@Service
public class CommentService extends AbstractService<CommentRepository, CommentMapper, Validator>
        implements GenericCrudService<Comment, CommentDto, CommentCreateDto, CommentUpdateDto, Long> {


    protected CommentService(CommentRepository repository,
                             CommentMapper mapper,
                             CommentValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public List<CommentDto> getAll() {
        return mapper.toDto(repository.findAllByDeletedFalse());
    }

    public List<CommentDto> getAll(Long taskId) {
        return mapper.toDto(repository.findAllByTaskIdd(taskId));
    }

    @Override
    public Long create(CommentCreateDto dto) {
        Comment comment = mapper.fromCreateDto(dto);
        comment.setAuthorUsername(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
        return repository.save(comment).getId();
    }

    @Override
    public CommentDto get(Long id) {
        return mapper.toDto(repository.findByIdAndDeletedFalse(id));
    }

    @Override
    public Void delete(Long id) {
        repository.delete(id);
        return null;
    }

    public CommentUpdateDto getUpdateDto(Long id) {
        return mapper.toUpdateDto(this.get(id));
    }

    @Override
    public Void update(CommentUpdateDto dto) {
        repository.update(dto.getId(), dto.getText());
        return null;
    }
}
