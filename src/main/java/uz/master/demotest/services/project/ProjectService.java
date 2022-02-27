package uz.master.demotest.services.project;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.entity.project.Project;
import uz.master.demotest.mappers.ProjectMapper;
import uz.master.demotest.repositories.ProjectRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.services.column.ColumnService;
import uz.master.demotest.validator.project.ProjectValidator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService extends AbstractService<ProjectRepository, ProjectMapper, ProjectValidator>
        implements GenericCrudService<Project, ProjectDto, ProjectCreateDto, ProjectUpdateDto, Long> {


    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    private final ColumnService columnService;

    protected ProjectService(ProjectRepository repository, ProjectMapper mapper, ProjectValidator validator,
                             ColumnService columnService) {
        super(repository, mapper, validator);
        this.columnService = columnService;
    }

    @Override
    public Long create(ProjectCreateDto createDto) {
        Project project = mapper.fromCreateDto(createDto);
        project.setOrgId(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOrganization());
        return repository.save(project).getId();
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
        return repository.findAllByDeletedFalse().stream().map(project -> {
            ProjectDto dto=mapper.toDto(project);
            dto.setCreatedAt(project.getCreatedAt().format(FORMATTER));
        return dto; }).collect(Collectors.toList());
    }
    public List<ProjectDto> getAll(Long id ) {
        return repository.findAllByOrgIdAndDeletedFalse(id).stream().map(project -> {
            ProjectDto dto=mapper.toDto(project);
            dto.setCreatedAt(project.getCreatedAt().format(FORMATTER));
            return dto; }).collect(Collectors.toList());
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
