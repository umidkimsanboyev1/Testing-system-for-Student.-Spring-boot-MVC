package uz.master.demotest.services.task;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.task.TaskCreateDto;
import uz.master.demotest.dto.task.TaskDto;
import uz.master.demotest.dto.task.TaskUpdateDto;
import uz.master.demotest.entity.action.Action;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.task.Task;
import uz.master.demotest.entity.task.Task_Member;
import uz.master.demotest.mappers.TaskMapper;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.TaskRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.utils.TaskValidator;
import uz.master.demotest.utils.Validator;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService extends AbstractService<TaskRepository, TaskMapper, Validator>
        implements GenericCrudService<Task, TaskDto
        , TaskCreateDto, TaskUpdateDto, Long> {


    private final AuthUserRepository authUserRepository;

    protected TaskService(TaskRepository repository, TaskMapper mapper, TaskValidator validator, AuthUserRepository authUserRepository) {
        super(repository, mapper, validator);
        this.authUserRepository = authUserRepository;
    }

    @Override
    public List<TaskDto> getAll() {
        return mapper.toDto(repository.findAllByDeletedFalse());
    }

    public List<TaskDto> getAll(Long columnId) {
        return mapper.toDto(repository.findAllByColumnIdAndDeletedFalse(columnId));
    }


    @Override
    public Long create(TaskCreateDto createDto) {
        return repository.save(mapper.fromCreateDto(createDto)).getId();
    }

    @Override
    public Void delete(Long id) {
        repository.delete(id);
        return null;
    }

    @Override
    public Void update(TaskUpdateDto dto) {
        repository.update(dto.getId(), dto.getName(), dto.getDescription(), dto.getTaskOrder(), dto.getLevel(), dto.getPriority(), dto.getColumnId());
        return null;
    }

    @Override
    public TaskDto get(Long id) {
        Task task = repository.findByIdAndDeletedFalse(id);
        TaskDto dto = mapper.toDto(task);
        dto.setCreatedAt(task.getCreatedAt().toString());
        dto.setUpdatedAt(task.getUpdatedAt().toString());
        return dto;
    }


    public TaskUpdateDto getUpdateDto(Long id) {
        return mapper.toUpdateDto(this.get(id));
    }

    public Void addMamber(Long taskId, Long memberId) {
        repository.addMember(taskId, memberId);
        return null;
    }

    public Void deleteMember(Long taskId, Long memberId) {
        repository.deleteMember(taskId, memberId);
        return null;
    }

    public List<Action> getActions(Long id) {
        return repository.getActions(id);
    }

    public List<AuthUser> getMembers(Long id) {
        List<Task_Member> memberIds = repository.getMembers(id);
        List<AuthUser> list = new ArrayList<>();
        for (Task_Member memberId : memberIds) {
            list.add(authUserRepository.findById(memberId.getUserId()).get());
        }
        return list;
    }
}
