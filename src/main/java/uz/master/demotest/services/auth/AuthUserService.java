package uz.master.demotest.services.auth;

import org.springframework.stereotype.Service;

import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;

import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.mappers.AuthUserMapper;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.services.GenericService;
import uz.master.demotest.utils.AuthUserValidator;

import java.util.List;

@Service
public class AuthUserService
        extends AbstractService<
        AuthUserRepository,
        AuthUserMapper,
        AuthUserValidator>
        implements GenericService<AuthDto,Long>,
        GenericCrudService<AuthUser,AuthDto, AuthUserCreateDto, AuthUserUpdateDto,Long> {


    protected AuthUserService(AuthUserRepository repository,
                              AuthUserMapper mapper,
                              AuthUserValidator validator) {
        super(repository, mapper, validator);
    }

    @Override
    public Long create(AuthUserCreateDto createDto) {
        return null;
    }

    @Override
    public Void delete(Long id) {
        return null;
    }

    @Override
    public Void update(AuthUserUpdateDto updateDto) {
        return null;
    }

    @Override
    public List<AuthDto> getAll() {
        return null;
    }

    @Override
    public AuthDto get(Long id) {
        return null;
    }
}
