package uz.master.demotest.services.test;

import org.springframework.stereotype.Service;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.repositories.OverAllResultRepository;
import uz.master.demotest.repositories.SendQuestionRepository;

import java.util.List;

@Service
public class OverAllResultService {
    private final OverAllResultRepository resultRepository;
    private SendQuestionRepository sendQuestionRepository;


    public OverAllResultService(OverAllResultRepository resultRepository, SendQuestionRepository sendQuestionRepository) {
        this.resultRepository = resultRepository;
        this.sendQuestionRepository = sendQuestionRepository;
    }

    public void delete(Long id) {
        OverAllResult overAllResult = resultRepository.findById(id).get();
        Long testId = overAllResult.getTestId();
        Long takerUserId = overAllResult.getTakerUserId();
        sendQuestionRepository.removeAllByTakerIdAndTestId(takerUserId, testId);
        resultRepository.deleteById(id);
    }


}
