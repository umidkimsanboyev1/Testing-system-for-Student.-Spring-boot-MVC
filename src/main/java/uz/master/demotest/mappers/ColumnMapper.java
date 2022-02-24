package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.column.ColumnCreateDto;
import uz.master.demotest.dto.column.ColumnDto;
import uz.master.demotest.dto.column.ColumnUpdateDto;
import uz.master.demotest.entity.column.ProjectColumn;


@Mapper(componentModel = "spring")
public interface ColumnMapper extends BaseMapper<ProjectColumn, ColumnDto, ColumnCreateDto, ColumnUpdateDto> {
}
