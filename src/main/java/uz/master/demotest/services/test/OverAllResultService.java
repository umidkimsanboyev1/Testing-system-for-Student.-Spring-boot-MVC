package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.OverAllResultRepository;
import uz.master.demotest.repositories.SendQuestionRepository;

@Service
public class OverAllResultService {
    private final OverAllResultRepository resultRepository;
    private final SendQuestionRepository sendQuestionRepository;
    private final AuthUserRepository authUserRepository;


    public OverAllResultService(OverAllResultRepository resultRepository, SendQuestionRepository sendQuestionRepository, AuthUserRepository authUserRepository) {
        this.resultRepository = resultRepository;
        this.sendQuestionRepository = sendQuestionRepository;
        this.authUserRepository = authUserRepository;
    }

    public void delete(Long id) {
        OverAllResult overAllResult = resultRepository.findById(id).get();
        Long testId = overAllResult.getTestId();
        Long takerUserId = overAllResult.getTakerUserId();
        AuthUser authUser = authUserRepository.findById(takerUserId).get();
        authUser.setTime(null);
        authUser.setQuesNumber(null);
        authUser.setTestId(null);
        authUserRepository.save(authUser);
        sendQuestionRepository.removeAllByTakerIdAndTestId(takerUserId, testId);
        resultRepository.deleteById(id);
    }


}
