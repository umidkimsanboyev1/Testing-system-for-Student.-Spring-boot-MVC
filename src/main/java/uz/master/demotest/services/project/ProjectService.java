package uz.master.demotest.services.project;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.entity.project.Project;
import uz.master.demotest.mappers.ProjectMapper;
import uz.master.demotest.repositories.ProjectRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;

import uz.master.demotest.utils.Validator;

import java.util.List;

@Service
public class ProjectService extends AbstractService<ProjectRepository, ProjectMapper, Validator>
        implements GenericCrudService<Project, ProjectDto, ProjectCreateDto, ProjectUpdateDto, Long> {

    protected ProjectService(ProjectRepository repository, ProjectMapper mapper, Validator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public Long create(ProjectCreateDto createDto) {
        return repository.save(mapper.fromCreateDto(createDto)).getId();
    }

    @Override
    public Void delete(Long id) {
        repository.delete(id);
        return null;
    }

    @Override
    public Void update(ProjectUpdateDto updateDto) {
        repository.update(updateDto);
        return null;
    }

    @Override
    public List<ProjectDto> getAll() {
        return mapper.toDto(repository.findAllByDeletedFalse());
    }

    @Override
    public ProjectDto get(Long id) {
        return mapper.toDto(repository.findByIdAndDeletedFalse(id));
    }

    public ProjectUpdateDto getUpdateDto(Long id) {
        return mapper.toUpdateDto(get(id));
    }
}
