package uz.master.demotest.services.organization;

import org.springframework.stereotype.Service;
import uz.master.demotest.dto.Organization.OrganizationCreateDto;
import uz.master.demotest.mappers.OrganizationMapper;
import uz.master.demotest.repositories.OrganizationRepository;

@Service
public class orgService {

    private final OrganizationMapper mapper;

    private final OrganizationRepository repository;

    public orgService(OrganizationMapper mapper, OrganizationRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    public void create(OrganizationCreateDto dto){
        repository.save(mapper.fromDto(dto));
    }

}
