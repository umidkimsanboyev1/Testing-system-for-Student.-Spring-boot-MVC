package uz.master.demotest.services.auth;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.auth.AuthDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;

import uz.master.demotest.dto.auth.AuthUserUpdateDto;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.entity.auth.AuthRole;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.auth.Token;
import uz.master.demotest.exceptions.NotFoundException;
import uz.master.demotest.mappers.AuthUserMapper;
import uz.master.demotest.repositories.AuthRoleRepository;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.TokenRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.GenericCrudService;
import uz.master.demotest.services.GenericService;
import uz.master.demotest.validator.auth.AuthUserValidator;
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
    private final AuthRoleRepository roleRepository;
    private final  SendEmail email;
    private final PasswordEncoder encoder;
    protected AuthUserService(AuthUserRepository repository,
                              AuthUserMapper mapper,
                              AuthUserValidator validator,
                              TokenRepository tokenRepository, AuthRoleRepository roleRepository, SendEmail email, PasswordEncoder encoder) {
        super(repository, mapper, validator);
        this.tokenRepository = tokenRepository;
        this.roleRepository = roleRepository;
        this.email = email;
        this.encoder = encoder;
    }

    @Override
    public Long create(AuthUserCreateDto createDto) {
        AuthUser authUser = mapper.fromCreateDto(createDto);
        AuthRole byId = roleRepository.getById(createDto.getUserRole());
        authUser.setOrganizationId(((UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOrganization());
        authUser.setRole(byId);
        authUser.setPassword(encoder.encode(createDto.getPassword()));
        return repository.save(authUser).getId();
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

    public AuthUser get(String username) {
        Optional<AuthUser> authUserByUsername = repository.getAuthUserByUsername(username);
        if (authUserByUsername.isEmpty()){
            throw new NotFoundException("username not found");
        }
        return authUserByUsername.get();
    }

    public void sendMail(String emailUser,String username) throws NotFoundException {
        if(Objects.nonNull(emailUser) &&emailUser.equals(get(username).getEmail())){
            String tokenGenerate =UUID.randomUUID().toString().replace("-","");
            Token token=new Token();
            token.setPrivateToken(tokenGenerate);
            tokenRepository.save(token);
            email.sendEmail(emailUser,tokenGenerate);
        }

    }

    public boolean checkToken(String token) {
        return tokenRepository.findByPrivateToken(token).isEmpty();

    }

    // TODO: 2/26/2022 sh yerga yozib ketishim kerak validatorlarni
    public void resetPassword(ResetPassword dto){
        AuthUser authUser = get(dto.getUsername());
        authUser.setPassword(encoder.encode(dto.getPassword()));
        repository.save(authUser);
    }
}
