package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.test.Test;

@Mapper(componentModel = "spring")
public interface TestMapper {

    public TestDto toDto (Test test);

}
