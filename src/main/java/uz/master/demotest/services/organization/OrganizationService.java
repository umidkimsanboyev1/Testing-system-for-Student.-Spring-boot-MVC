package uz.master.demotest.services.organization;


import org.springframework.stereotype.Service;
import uz.master.demotest.dto.Organization.OrganizationCreateDto;
import uz.master.demotest.dto.Organization.OrganizationDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;
import uz.master.demotest.mappers.OrganizationMapper;
import uz.master.demotest.repositories.OrganizationRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.utils.BaseUtils;
import uz.master.demotest.utils.OrganizationValidator;

import java.util.List;

@Service
public class OrganizationService extends AbstractService<OrganizationRepository, OrganizationMapper, OrganizationValidator>
        implements GenericCrudService<Organization, OrganizationDto, uz.master.demotest.dto.Organization.OrganizationCreateDto, OrganizationUpdateDto, Long> {

    protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidator validator, BaseUtils baseUtils) {
        super(repository, mapper, validator, baseUtils);
    }


    public OrganizationUpdateDto getOrganization(Long id) {
        return mapper.toUpdateDto(repository.findOrganizationByIdAndDeletedFalse(id));
    }

    @Override
    public List<OrganizationDto> getAll() {
        return mapper.toDto(repository.findAllByDeletedFalse());
    }

    @Override
    public OrganizationDto get(Long id) {
        return mapper.toDto(repository.findOrganizationByIdAndDeletedFalse(id));
    }

    @Override
    public Long create(OrganizationCreateDto createDto) {
        return null;
    }

    @Override
    public Void delete(Long id) {
        repository.deleteOrganization(id);
        return null;
    }

    @Override
    public Void update(OrganizationUpdateDto updateDto) {
        repository.save(mapper.fromUpdateDto(updateDto));
        return null;
    }
}
