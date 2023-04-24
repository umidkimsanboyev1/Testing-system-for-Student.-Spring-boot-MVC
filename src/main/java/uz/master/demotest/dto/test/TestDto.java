package uz.master.demotest.dto.test;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestDto {
    private Long id;

    private String name;
    private String subject;

    private String owner;

    private Integer numberOfQuestion;
    private Integer timeForOneQues;
    private Integer timeForAllQues;

    private boolean active;
}
