package uz.master.demotest.mappers;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.Organization.OrganizationCreateDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface OrganizationMapper {

    Organization fromCreateDto(OrganizationCreateDto dto);

    OrganizationUpdateDto toUpdateDto(Organization organization);

    Organization fromUpdateDto(OrganizationUpdateDto dto);

    List<uz.master.demotest.dto.Organization.OrganizationDto> toDto(List<Organization> organizations);
}
