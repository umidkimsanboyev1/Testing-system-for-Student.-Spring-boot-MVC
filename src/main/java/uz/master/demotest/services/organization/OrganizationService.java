package uz.master.demotest.services.organization;


import org.springframework.stereotype.Service;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.entity.organization.Organization;
import uz.master.demotest.mappers.OrganizationMapper;
import uz.master.demotest.repositories.OrganizationRepository;

import java.util.List;

@Service
public class OrganizationService {
    private final OrganizationMapper mapper;

    private final OrganizationRepository repository;

    public OrganizationService(OrganizationMapper mapper, OrganizationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void create(uz.master.demotest.dto.Organization.OrganizationCreateDto dto){
        repository.save(mapper.fromCreateDto(dto));
    }


    public OrganizationUpdateDto getOrganization(Long id) {
        return null;/*mapper.toUpdateDto(repository.findOrganizationByIdAndDeletedNot(id));*/
    }

    public void update(OrganizationUpdateDto dto) {
        repository.save(mapper.fromUpdateDto(dto));
    }

    public void delete(Long id) {
        repository.deleteOrganization(id);
    }

    public List<uz.master.demotest.dto.Organization.OrganizationDto> getOrganizations() {
        return mapper.toDto(repository.findAllByDeletedNot(false));
    }
}
