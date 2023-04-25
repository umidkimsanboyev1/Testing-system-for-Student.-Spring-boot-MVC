package uz.master.demotest.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.enums.Role;
import uz.master.demotest.exceptions.NotFoundException;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.TokenRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.utils.SessionUser;

import java.util.Optional;

@Service
public class AuthUserService
        extends AbstractService<
        AuthUserRepository> {
    private final TokenRepository tokenRepository;

    private final PasswordEncoder encoder;

    private final SessionUser sessionUser;

    protected AuthUserService(AuthUserRepository repository,

                              TokenRepository tokenRepository, PasswordEncoder encoder, SessionUser sessionUser) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.encoder = encoder;
        this.sessionUser = sessionUser;
    }


    public AuthUser get(String username) {
        Optional<AuthUser> authUserByUsername = repository.getAuthUsersByUsernameAndDeletedFalse(username);
        if (authUserByUsername.isEmpty()) {
            throw new NotFoundException("username not found");
        }
        return authUserByUsername.get();
    }


    public boolean checkToken(String token) {
        return tokenRepository.findByPrivateToken(token).isEmpty();

    }

    // TODO: 2/26/2022 sh yerga yozib ketishim kerak validatorlarni
    public void resetPassword(ResetPassword dto) {
        AuthUser authUser = get(dto.getUsername());
        authUser.setPassword(encoder.encode(dto.getPassword()));
        repository.save(authUser);
    }


    public String getUserById(Long id) {
        return repository.getById(id).getFullName();
    }

    public String getUserName() {
        return repository.findById(sessionUser.getId()).get().getFullName();
    }

    public Long getSessionId() {
        return sessionUser.getId();
    }

    public boolean hasAdminRole() {
        return repository.getById(sessionUser.getId()).getRole().equals(Role.ADMIN);
    }
}
