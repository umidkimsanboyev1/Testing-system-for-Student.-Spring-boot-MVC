package uz.master.demotest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TestPassDto {
    private String question;
    private String questionNumber;
    private List<String> answers;
}
