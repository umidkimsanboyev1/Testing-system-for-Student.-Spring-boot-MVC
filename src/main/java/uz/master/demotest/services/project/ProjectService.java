package uz.master.demotest.services.project;

import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.entity.project.Project;
import uz.master.demotest.mappers.ProjectMapper;
import uz.master.demotest.repositories.ProjectRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.utils.BaseUtils;
import uz.master.demotest.utils.Validator;

import java.util.List;

public class ProjectService extends AbstractService<ProjectRepository, ProjectMapper, Validator>
        implements GenericCrudService<Project, ProjectDto, ProjectCreateDto, ProjectUpdateDto, Long> {

    protected ProjectService(ProjectRepository repository, ProjectMapper mapper, Validator validator, BaseUtils baseUtils) {
        super(repository, mapper, validator, baseUtils);
    }

    @Override
    public Long create(ProjectCreateDto createDto) {
        return null;
    }

    @Override
    public Void delete(Long id) {
        return null;
    }

    @Override
    public Void update(ProjectUpdateDto updateDto) {
        return null;
    }

    @Override
    public List<ProjectDto> getAll() {
        return null;
    }

    @Override
    public ProjectDto get(Long id) {
        return null;
    }
}
