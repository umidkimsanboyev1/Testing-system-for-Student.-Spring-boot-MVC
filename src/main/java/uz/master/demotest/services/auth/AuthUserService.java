package uz.master.demotest.services.auth;

import com.poiji.bind.Poiji;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import uz.master.demotest.configs.encrypt.PasswordEncoderConfig;
import uz.master.demotest.dto.auth.AddStudentDto;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.dto.auth.StudentsCreateDto;
import uz.master.demotest.dto.excel.StudentsExcelDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.OverAllResultRepository;
import uz.master.demotest.repositories.TestRepository;
import uz.master.demotest.repositories.TokenRepository;
import uz.master.demotest.services.AbstractService;
import uz.master.demotest.services.FileStorageService;
import uz.master.demotest.utils.SessionUser;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthUserService extends AbstractService<AuthUserRepository> {
    private final TokenRepository tokenRepository;
    private final OverAllResultRepository overAllResultRepository;
    private final SessionUser sessionUser;
    private final TestRepository testRepository;
    private final PasswordEncoderConfig encoder;
    private final FileStorageService fileStorageService;
    private final AuthUserRepository authUserRepository;

    protected AuthUserService(AuthUserRepository repository,

                              TokenRepository tokenRepository, OverAllResultRepository overAllResultRepository, SessionUser sessionUser, TestRepository testRepository, PasswordEncoderConfig encoder, FileStorageService fileStorageService, AuthUserRepository authUserRepository) {
        super(repository);
        this.tokenRepository = tokenRepository;
        this.overAllResultRepository = overAllResultRepository;
        this.sessionUser = sessionUser;
        this.testRepository = testRepository;
        this.encoder = encoder;
        this.fileStorageService = fileStorageService;
        this.authUserRepository = authUserRepository;
    }

    public static DateTimeFormatter getTimeFormatter() {
        String patternForDateTime = "dd.MM.yyyy HH:mm:ss";
        return DateTimeFormatter.ofPattern(patternForDateTime);
    }

    public boolean checkTest(Long testId) {
        AuthUser authUser = getAuthUser();
        return !overAllResultRepository.existsByTakerUserAndTestId(authUser.getFullName(), testId);
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
        overAllResultTemp.setGroupName(authUserRepository.findById(sessionUser.getId()).get().getGroupName());
        return overAllResultTemp;
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
        AuthUser authUser = getAuthUser();
        authUser.setQuesNumber(generatedNumber);
        repository.save(authUser);
    }

    public List<AuthUser> getAllStudents() {
        return repository.findAuthUserByRoleOrderByFullName(Role.STUDENT);
    }

    public void doAction(Long id) {
        AuthUser authUser = repository.findById(id).get();
        authUser.setActive(!authUser.isActive());
        System.out.println(authUser.isActive());
        repository.save(authUser);
    }

    public void delete(Long id) {
        AuthUser authUser = repository.findById(id).get();
        repository.delete(authUser);
    }

    public boolean addStudent(AddStudentDto dto) {
        if (!dto.getPassword1().equals(dto.getPassword2()) || repository.existsAuthUserByUsername(dto.getUsername())) {
            return false;
        }
        AuthUser tempUser = new AuthUser();
        MapAuthUser(dto, tempUser, Role.STUDENT);
        return true;
    }

    private void MapAuthUser(AddStudentDto dto, AuthUser tempUser, Role role) {
        tempUser.setBlocked(false);
        tempUser.setActive(true);
        tempUser.setRole(role);
        tempUser.setFullName(dto.getFullName());
        tempUser.setUsername(dto.getUsername());
        tempUser.setGroupName(dto.groupName);
        tempUser.setPassword(encoder.passwordEncoder().encode(dto.password1));
        repository.save(tempUser);
    }

    public LocalDateTime getUserTime() {
        return repository.findById(sessionUser.getId()).get().getTime();
    }

    public List<AuthUser> getAllStudentsByGroup(String groupName) {
        return repository.findAuthUserByRoleAndGroupNameOrderByFullName(Role.STUDENT, groupName);
    }

    public AuthUser getAuthUser() {
        return repository.findById(sessionUser.getId()).get();
    }

    public boolean checkSelectedGroupName() {
        AuthUser authUser = getAuthUser();
        return Objects.isNull(authUser.getSelectedGroup());
    }

    public String getSelectedGroupName() {
        return getAuthUser().getSelectedGroup();
    }

    public void setSelectedGroupName(Object o) {
        AuthUser authUser = getAuthUser();
        authUser.setSelectedGroup(null);
        repository.save(authUser);
    }

    public void actionStudents(boolean b) {
        AuthUser authUser = getAuthUser();
        List<AuthUser> students;
        if (Objects.isNull(authUser.getSelectedGroup())) {
            students = repository.findAuthUserByRoleOrderByFullName(Role.STUDENT);
        } else {
            students = repository.findAuthUserByRoleAndGroupNameOrderByFullName(Role.STUDENT, authUser.getSelectedGroup());
        }

        students.forEach(authUser1 -> authUser1.setActive(b));
        repository.saveAll(students);
    }

    public boolean resetPassword(ResetPassword dto) {
        AuthUser authUser = getAuthUser();
        String oldPassword = authUser.getPassword();
        String rawPassword = encoder.passwordEncoder().encode(dto.getOldPassword());
        if (!dto.getNewPassword1().equals(dto.getNewPassword2()) || encoder.passwordEncoder().matches(rawPassword, oldPassword)) {
            return false;
        }
        String newPassword = encoder.passwordEncoder().encode(dto.getNewPassword1());
        authUser.setPassword(newPassword);
        repository.save(authUser);
        return true;
    }

    public Long setAuthUserSelectedGroup(String groupName) {
        AuthUser authUser = getAuthUser();
        authUser.setSelectedGroup(groupName);
        repository.save(authUser);
        return authUser.getId();
    }

    public AuthUser getAuthUserById(Long id) {
        return repository.findById(id).get();
    }

    public void updateAuthUser(AuthUser user) {
        Optional<AuthUser> authUserByUsername = repository.findAuthUserByUsername(user.getUsername());
        if(authUserByUsername.isPresent() && !authUserByUsername.get().getId().equals(user.getId())){
            throw new RuntimeException();
        }
        AuthUser authUser = repository.findById(user.getId()).get();
        authUser.setUsername(user.getUsername());
        if(!Objects.isNull(user.getPassword())){
            authUser.setPassword(encoder.passwordEncoder().encode(user.getPassword()));
        }
        authUser.setGroupName(user.getGroupName());
        authUser.setFullName(user.getFullName());
        repository.save(authUser);
    }

    public boolean addTeacher(AddStudentDto dto) {
        if (!dto.getPassword1().equals(dto.getPassword2()) || repository.existsAuthUserByUsername(dto.getUsername())) {
            return false;
        }
        AuthUser tempUser = new AuthUser();
        MapAuthUser(dto, tempUser, Role.TEACHER);
        return true;
    }

    public List<AuthUser> getAllTeachers() {
        return repository.findAuthUserByRoleOrderByFullName(Role.TEACHER);
    }


    public boolean checkToRole(Role role) {
        return getAuthUser().getRole().equals(role);
    }

    public Long setAuthUserViewedTestId(Long id) {
        AuthUser authUser = getAuthUser();
        authUser.setViewedTestId(id);
        repository.save(authUser);
        return authUser.getId();
    }

    public void createStudents(StudentsCreateDto studentCreateDto) {
        String storedName = fileStorageService.store(studentCreateDto.getFile());
        List<StudentsExcelDto> students = convertExcelToEntity(storedName);
        for (StudentsExcelDto dto : students) {
            AuthUser student = new AuthUser();
            student.setFullName(dto.getFullName());
            student.setPassword(encoder.passwordEncoder().encode(dto.getPassword()));
            student.setGroupName(dto.getGroupName());
            student.setActive(false);
            student.setRole(Role.STUDENT);
            student.setBlocked(false);
            student.setUsername(getUsernameForStudent(dto.getFullName()));
            repository.save(student);
        }
    }

    private String getUsernameForStudent(String fullName) {
        String[] nomlar = fullName.split(" ");
        String familiya = nomlar[0];
        String ism = nomlar[1];
        String familiyaLowerCase = familiya.toLowerCase();
        char ismBirinchiHarf = ism.toLowerCase().charAt(0);
        String username = familiyaLowerCase + "." + ismBirinchiHarf;
        if(repository.existsAuthUserByUsername(username)){
            username += ism.toLowerCase().charAt(1);
            if(repository.existsAuthUserByUsername(username)){
                username += ism.toLowerCase().charAt(2);
            }
        }
        return username;
    }

    private List<StudentsExcelDto> convertExcelToEntity(String fileName) {
        File file = new File("/JavaProject/tets/AtomsProject/src/main/resources/files/" + fileName);
        List<StudentsExcelDto> students = Poiji.fromExcel(file, StudentsExcelDto.class);
        return students;
    }

    public void deleteByGroupName(String name) {
        List<AuthUser> allByGroupName = repository.findAllByGroupName(name);
        repository.deleteAll(allByGroupName);
    }

    public List<AuthUser> getAllAuthUsersByRoleAndBySearch(String text, Role role) {
        List<AuthUser> authUserByRoleOrderByFullName = repository.findAuthUserByRoleOrderByFullName(role);
        List<AuthUser> results = searchUsers(text, authUserByRoleOrderByFullName);
        return results;
    }

    public List<AuthUser> getAllAuthUsersByRoleAndBySearch(String text, Role role, String selectedGroup) {
        List<AuthUser> authUserByRoleOrderByFullName = repository.findAuthUserByRoleAndGroupNameOrderByFullName(role, selectedGroup);
        List<AuthUser> results = searchUsers(text, authUserByRoleOrderByFullName);
        return results;
    }


    @NotNull
    private static List<AuthUser> searchUsers(String text, List<AuthUser> authUserByRoleOrderByFullName) {
        List<AuthUser> results = new ArrayList<>();
        for (AuthUser authUser : authUserByRoleOrderByFullName) {
            String fullName = authUser.getFullName();
            String username = authUser.getUsername();
            if(fullName.toLowerCase().contains(text.toLowerCase()) || username.toLowerCase().contains(text.toLowerCase())){
                results.add(authUser);
            }
        }
        return results;
    }

}
