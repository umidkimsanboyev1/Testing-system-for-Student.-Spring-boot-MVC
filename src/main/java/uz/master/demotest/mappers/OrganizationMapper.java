package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.Organization.OrganizationCreateDto;
import uz.master.demotest.entity.organization.Organization;

@Component
@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization fromDto(OrganizationCreateDto dto);

}
