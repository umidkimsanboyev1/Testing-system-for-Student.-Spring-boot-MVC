package uz.master.demotest.entity.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;
    private Long number;
    private Long testId;
    private String correctAnswer;
    private String answer2;
    private String answer3;
    private String answer4;

    private String imagePath;
}
