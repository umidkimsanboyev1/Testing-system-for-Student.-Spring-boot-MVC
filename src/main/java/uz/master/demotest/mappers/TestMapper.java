package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.master.demotest.dto.test.TestCreateDto;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.test.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {

    TestDto toDto (Test test);

    @Mapping(target = "file", ignore = true)
    Test toEntity(TestCreateDto dto);

}
