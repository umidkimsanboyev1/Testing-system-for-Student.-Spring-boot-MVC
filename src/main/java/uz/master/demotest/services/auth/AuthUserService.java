package uz.master.demotest.services.auth;

import org.springframework.stereotype.Service;

import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;

import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.auth.Token;
import uz.master.demotest.exceptions.NotFoundException;
import uz.master.demotest.mappers.AuthUserMapper;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.TokenRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.services.GenericService;
import uz.master.demotest.utils.AuthUserValidator;
import uz.master.demotest.utils.SendEmail;

import java.util.*;

@Service
public class AuthUserService
        extends AbstractService<
        AuthUserRepository,
        AuthUserMapper,
        AuthUserValidator>
        implements GenericService<AuthDto,Long>,
        GenericCrudService<AuthUser,AuthDto, AuthUserCreateDto, AuthUserUpdateDto,Long> {
    private final TokenRepository tokenRepository;

   private final  SendEmail email;
    protected AuthUserService(AuthUserRepository repository,
                              AuthUserMapper mapper,
                              AuthUserValidator validator,
                              TokenRepository tokenRepository, SendEmail email) {
        super(repository, mapper, validator);
        this.tokenRepository = tokenRepository;
        this.email = email;
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

    public void sendMail(String emailUser,String username) throws NotFoundException {
        Optional<AuthUser> authUserByUsername = repository.getAuthUserByUsername(username);
        if (authUserByUsername.isEmpty()){
            throw new NotFoundException("username not found");
        }else if(Objects.nonNull(emailUser) &&emailUser.equals(authUserByUsername.get().getEmail())){
            String tokenGenerate =UUID.randomUUID().toString().replace("-","");
            Token token=new Token();
            token.setPrivateToken(tokenGenerate);
            tokenRepository.save(token);
            email.sendEmail(emailUser,"localhost:8080/auth/reset/"+tokenGenerate);
        }

    }
}
