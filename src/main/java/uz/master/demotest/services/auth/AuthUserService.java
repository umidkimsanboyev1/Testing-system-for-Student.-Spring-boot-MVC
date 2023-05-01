package uz.master.demotest.services.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.encrypt.PasswordEncoderConfig;
import uz.master.demotest.dto.auth.AddStudentDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
import uz.master.demotest.repositories.*;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class AuthUserService extends AbstractService<AuthUserRepository> {
    private final TokenRepository tokenRepository;
    private final OverAllResultRepository overAllResultRepository;
    private final SessionUser sessionUser;
    private final TestRepository testRepository;
    private final PasswordEncoderConfig encoder;

    protected AuthUserService(AuthUserRepository repository,

                              TokenRepository tokenRepository, OverAllResultRepository overAllResultRepository, SessionUser sessionUser, TestRepository testRepository, PasswordEncoderConfig encoder) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.overAllResultRepository = overAllResultRepository;
        this.sessionUser = sessionUser;
        this.testRepository = testRepository;
        this.encoder = encoder;
    }

    public boolean checkTest(Long testId) {
        return !overAllResultRepository.existsByTakerUserAndTestIdAndCompletedFalse(sessionUser.getFullName(), testId);
    }

    public void saveToAuthUser(Long testId) {
        Test test = testRepository.findById(testId).get();
        AuthUser user = repository.findById(sessionUser.getId()).get();
        OverAllResult overAllResultTemp = setData(testId, test);
        overAllResultRepository.save(overAllResultTemp);
        user.setTestId(testId);
        user.setQuesNumber(1);
        LocalDateTime now = LocalDateTime.now();
        Integer timeForAllQues = test.getTimeForAllQues();
        user.setTime(now.plus(timeForAllQues, ChronoUnit.MINUTES));
        repository.save(user);
    }

    private OverAllResult setData(Long testId, Test test) {
        DateTimeFormatter formatter = getTimeFormatter();
        OverAllResult overAllResultTemp = new OverAllResult();
        overAllResultTemp.setTakerUser(sessionUser.getFullName());
        overAllResultTemp.setTakerUserId(sessionUser.getId());
        overAllResultTemp.setTestId(testId);
        overAllResultTemp.setNumberOfAllQues(test.getNumberOfQuestion());
        overAllResultTemp.setStartedTime(LocalDateTime.now().format(formatter));
        overAllResultTemp.setTestName(test.getName());
        return overAllResultTemp;
    }

    public static DateTimeFormatter getTimeFormatter() {
        String patternForDateTime = "dd.MM.yyyy HH:mm:ss";
        return DateTimeFormatter.ofPattern(patternForDateTime);
    }


//    public AuthUser get(String username) {
//        Optional<AuthUser> authUserByUsername = repository.getAuthUsersByUsernameAndDeletedFalse(username);
//        if (authUserByUsername.isEmpty()) {
//            throw new NotFoundException("username not found");
//        }
//        return authUserByUsername.get();
//    }


    public boolean checkToken(String token) {
        return tokenRepository.findByPrivateToken(token).isEmpty();

    }

//   2/26/2022 sh yerga yozib ketishim kerak validatorlarni

//    public void resetPassword(ResetPassword dto) {
//        AuthUser authUser = get(dto.getUsername());
//        authUser.setPassword(encoder.encode(dto.getPassword()));
//        repository.save(authUser);
//    }


    public void setQuesNumber(Integer generatedNumber) {
        AuthUser authUser = repository.findById(sessionUser.getId()).get();
        authUser.setQuesNumber(generatedNumber);
        repository.save(authUser);
    }

    public List<AuthUser> getAllStudents() {
        List<AuthUser> allStudents = repository.findAuthUserByRoleAndDeletedFalseOrderById(Role.STUDENT);
        return allStudents;
    }

    public void doAction(Long id) {
        AuthUser authUser = repository.findById(id).get();
        authUser.setActive(!authUser.isActive());
        repository.save(authUser);
    }

    public void delete(Long id) {
        AuthUser authUser = repository.findById(id).get();
        authUser.setDeleted(true);
        repository.save(authUser);
    }

    public boolean addStudent(AddStudentDto dto) {
        if(!dto.getPassword1().equals(dto.getPassword2()) || repository.existsAuthUserByUsername(dto.getUsername())){
            return false;
        }
        AuthUser tempUser = new AuthUser();
        MapAuthUser(dto, tempUser);
        return true;
    }

    private void MapAuthUser(AddStudentDto dto, AuthUser tempUser) {
        tempUser.setBlocked(false);
        tempUser.setDeleted(false);
        tempUser.setActive(true);
        tempUser.setRole(Role.STUDENT);
        tempUser.setFullName(dto.getFullName());
        tempUser.setUsername(dto.getUsername());
        tempUser.setGroupName(dto.groupName);
        tempUser.setPassword(encoder.passwordEncoder().encode(dto.password1));
        repository.save(tempUser);
    }
}
