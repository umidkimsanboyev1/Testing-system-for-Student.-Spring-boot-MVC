package uz.master.demotest.services.organization;


import org.springframework.stereotype.Service;
import uz.master.demotest.dto.Organization.OrganizationDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;
import uz.master.demotest.mappers.OrganizationMapper;
import uz.master.demotest.repositories.OrganizationRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.utils.OrganizationValidator;

import java.util.List;

@Service
public class OrganizationService extends AbstractService<OrganizationRepository, OrganizationMapper, OrganizationValidator> implements GenericCrudService<Organization, OrganizationDto, uz.master.demotest.dto.organization.OrganizationCreateDto, OrganizationUpdateDto, Long> {

    protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidator validator) {
        super(repository, mapper, validator);
    }


    public OrganizationUpdateDto getOrganization(Long id) {
        return mapper.toUpdateDto(repository.findOrganizationByIdAndDeletedFalseOrderByIdAsc(id));
    }

    @Override
    public List<OrganizationDto> getAll() {
        for (Organization organization : repository.findAllByDeletedFalse()) {
            System.out.println("organization = " + organization);
        }

        return mapper.toDto(repository.findAllByDeletedFalse());
    }

    @Override
    public OrganizationDto get(Long id) {
        return mapper.toDto(repository.findOrganizationByIdAndDeletedFalseOrderByIdAsc(id));
    }


    @Override
    public Long create(uz.master.demotest.dto.organization.OrganizationCreateDto createDto) {
        Organization organization = mapper.fromCreateDto(createDto);
        organization.setStatus("ACTIVE");
        organization.setDeleted(false);
        repository.save(organization);
        return null;
    }

    @Override
    public Void delete(Long id) {
        repository.deleteOrganization(id);
        return null;
    }

    @Override
    public Void update(OrganizationUpdateDto updateDto) {
        return null;
    }

    public Void update(OrganizationUpdateDto updateDto, Long id) {
        updateDto.setId(id);
        updateDto.setRegistrationNumber(repository.findOrganizationByIdAndDeletedFalseOrderByIdAsc(id).getRegistrationNumber());
        updateDto.setStatus("ACTIVE");
        repository.save(mapper.fromUpdateDto(updateDto));
        return null;
    }
}
