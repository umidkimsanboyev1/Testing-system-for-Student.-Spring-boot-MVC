package uz.master.demotest.dto.test;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Test;

import java.util.List;

@Getter
@Setter
public class OverAllResultDTO {

    private Test test;
    private List<OverAllResult> results;
}
