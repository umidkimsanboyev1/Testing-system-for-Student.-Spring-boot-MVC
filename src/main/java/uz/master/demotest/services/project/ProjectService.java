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

import uz.master.demotest.services.column.ColumnService;
import uz.master.demotest.utils.ProjectValidator;

import java.util.List;

@Service
public class ProjectService extends AbstractService<ProjectRepository, ProjectMapper, ProjectValidator>
        implements GenericCrudService<Project, ProjectDto, ProjectCreateDto, ProjectUpdateDto, Long> {

    private final ColumnService columnService;

    protected ProjectService(ProjectRepository repository, ProjectMapper mapper, ProjectValidator validator,
                             ColumnService columnService) {
        super(repository, mapper, validator);
        this.columnService = columnService;
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
        ProjectDto projectDto = mapper.toDto(repository.findByIdAndDeletedFalse(id));
        projectDto.setColumns(columnService.getAll(projectDto.getId()));
        return projectDto;
    }

    public ProjectUpdateDto getUpdateDto(Long id) {
        return mapper.toUpdateDto(get(id));
    }
}
