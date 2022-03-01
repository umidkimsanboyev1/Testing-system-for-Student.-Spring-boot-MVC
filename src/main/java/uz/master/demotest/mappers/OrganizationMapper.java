package uz.master.demotest.mappers;

import org.hibernate.annotations.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import uz.master.demotest.dto.organization.OrganizationCreateDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface OrganizationMapper extends BaseMapper<Organization, uz.master.demotest.dto.organization.OrganizationDto, OrganizationCreateDto, OrganizationUpdateDto> {


    @Override
    @Mapping(target = "logo", ignore = true)
    Organization fromCreateDto(OrganizationCreateDto dto);

    @Mapping(target = "logo", ignore = true)
    OrganizationUpdateDto toUpdateDto(Organization organization);

    @Mapping(target = "logo", ignore = true)
    Organization fromUpdateDto(OrganizationUpdateDto dto);

    List<uz.master.demotest.dto.organization.OrganizationDto> toDto(List<Organization> organizations);
}
