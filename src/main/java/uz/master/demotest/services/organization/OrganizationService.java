package uz.master.demotest.services.organization;


import org.springframework.stereotype.Service;
import uz.master.demotest.dto.organization.OrganizationDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;
import uz.master.demotest.mappers.OrganizationMapper;
import uz.master.demotest.repositories.OrganizationRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.validator.organization.OrganizationValidator;

import java.util.List;

@Service
public class OrganizationService extends AbstractService<OrganizationRepository, OrganizationMapper, OrganizationValidator> implements GenericCrudService<Organization, OrganizationDto, uz.master.demotest.dto.organization.OrganizationCreateDto, OrganizationUpdateDto, Long> {

    protected OrganizationService(OrganizationRepository repository, OrganizationMapper mapper, OrganizationValidator validator) {
        super(repository, mapper, validator);
    }


    public OrganizationUpdateDto getOrganization(Long id) {
        return mapper.toUpdateDto(repository.findOrganizationByIdAndDeletedFalse(id));
    }

    @Override
    public List<OrganizationDto> getAll() {
        for (Organization organization : repository.findAllByDeletedFalseOrderByIdAsc()) {
            System.out.println("organization = " + organization);
        }

        return mapper.toDto(repository.findAllByDeletedFalseOrderByIdAsc());
    }

    @Override
    public OrganizationDto get(Long id) {
        System.out.println("mapper.toDto(repository.findOrganizationByIdAndDeletedFalse(id)) = " + mapper.toDto(repository.findOrganizationByIdAndDeletedFalse(id)));
        return mapper.toDto(repository.findOrganizationByIdAndDeletedFalse(id));
    }


    @Override
    public Long create(uz.master.demotest.dto.organization.OrganizationCreateDto createDto) {
        Organization organization = mapper.fromCreateDto(createDto);
        organization.setStatus("ACTIVE");
        organization.setDeleted(false);
        return repository.save(organization).getId();
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
        updateDto.setRegistrationNumber(repository.findOrganizationByIdAndDeletedFalse(id).getRegistrationNumber());
        updateDto.setStatus("ACTIVE");
        repository.save(mapper.fromUpdateDto(updateDto));
        return null;
    }

    public void block(Long id, boolean b) {
        Organization organization = repository.findOrganizationByIdAndDeletedFalse(id);
        organization.setBlocked(b);
        repository.save(organization);
        System.out.println("repository.findOrganizationByIdAndDeletedFalseOrderByIdAsc(id) = " + repository.findOrganizationByIdAndDeletedFalse(id));
    }
}
