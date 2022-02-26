package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.entity.project.Project;


@Component
@Mapper(componentModel = "spring")
public interface ProjectMapper extends BaseMapper<Project, ProjectDto, ProjectCreateDto, ProjectUpdateDto> {
    ProjectUpdateDto toUpdateDto(ProjectDto dto);

    @Override
    @Mapping(target = "tz",ignore = true)
    Project fromCreateDto(ProjectCreateDto dto);
}
