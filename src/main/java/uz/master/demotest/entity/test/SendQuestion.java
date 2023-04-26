package uz.master.demotest.entity.test;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class SendQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long questionId;
    private Long takerId;
    private Long testId;
    private Integer generatedQuestionNumber;
    private String question;
    private String answer1;
    private boolean checked1;
    private String answer2;
    private boolean checked2;
    private String answer3;
    private boolean checked3;
    private String answer4;
    private boolean checked4;

}
