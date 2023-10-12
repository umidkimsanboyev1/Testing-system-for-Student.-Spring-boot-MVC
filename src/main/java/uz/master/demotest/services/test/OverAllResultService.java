package uz.master.demotest.services.test;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.repositories.AuthUserRepository;
import uz.master.demotest.repositories.OverAllResultRepository;
import uz.master.demotest.repositories.SendQuestionRepository;

import java.util.ArrayList;
import java.util.List;

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


    public List<OverAllResult> findResultByTestIdAndGroupNameAndSearch(Long id, String selectedGroup, String text) {
        List<OverAllResult> groupNameOrderByTakerUser = resultRepository.findOverAllResultsByTestIdAndGroupNameOrderByTakerUser(id, selectedGroup);
        return getSearchedResults(text, groupNameOrderByTakerUser);
    }


    public List<OverAllResult> findResultByTestIdAndSearch(Long id, String text) {
        List<OverAllResult> idOrderByTakerUser = resultRepository.findOverAllResultsByTestIdOrderByTakerUser(id);
        return getSearchedResults(text, idOrderByTakerUser);
    }

    @NotNull
    private static List<OverAllResult> getSearchedResults(String text, List<OverAllResult> groupNameOrderByTakerUser) {
        List<OverAllResult> results = new ArrayList<>();
        for (OverAllResult result : groupNameOrderByTakerUser) {
            String takerUser = result.getTakerUser().toLowerCase();
            Integer correctAnsweredQues = result.getCorrectAnsweredQues();
            double efficiency = result.getEfficiency();
            if(takerUser.contains(text.toLowerCase()) || (String.valueOf(correctAnsweredQues)).contains(text) || (String.valueOf(efficiency)).contains(text)){
                results.add(result);
            }
        }
        return results;
    }

}
