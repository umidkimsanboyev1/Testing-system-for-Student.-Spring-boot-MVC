package uz.master.demotest.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.exceptions.NotFoundException;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.ResultRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.repositories.TokenRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class AuthUserService
        extends AbstractService<
        AuthUserRepository> {
    private final TokenRepository tokenRepository;
    private final ResultRepository resultRepository;

    private final PasswordEncoder encoder;

    private final SessionUser sessionUser;
    private final TestRepository testRepository;

    protected AuthUserService(AuthUserRepository repository,

                              TokenRepository tokenRepository, ResultRepository resultRepository, PasswordEncoder encoder, SessionUser sessionUser, TestRepository testRepository) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.resultRepository = resultRepository;
        this.encoder = encoder;
        this.sessionUser = sessionUser;
        this.testRepository = testRepository;
    }

    public boolean testStart(Long testId) {
        Test test = testRepository.findById(testId).get();
        AuthUser user = repository.findById(sessionUser.getId()).get();
        if(resultRepository.existsByTakerUserAndTestId(user.getFullName(), testId)) return false;
        OverAllResult overAllResultTemp = new OverAllResult();
        overAllResultTemp.setTakerUser(sessionUser.getFullName());
        overAllResultTemp.setTakerUserId(sessionUser.getId());
        overAllResultTemp.setTestId(testId);
        overAllResultTemp.setNumberOfAllQues(test.getTimeForAllQues());
        overAllResultTemp.setStartedTime(new Date());
        overAllResultTemp.setTestName(test.getName());
        resultRepository.save(overAllResultTemp);
        user.setTestId(testId);
        user.setQuesId(1);
        repository.save(user);
        return true;
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


    public Integer getSessionUserTime(Long testId) {
        AuthUser authUser = repository.findById(sessionUser.getId()).get();
        Integer timeForAllQues = testRepository.findById(testId).get().getTimeForAllQues();
        authUser.setTimeLeft(timeForAllQues);
        return timeForAllQues;
    }

}
