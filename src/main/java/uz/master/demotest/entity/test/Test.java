package uz.master.demotest.entity.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Long subjectId;

    // group ID for group
    private Long groupId;
    private Long ownerId;
    private Integer numberOfQuestion;
    // time for one and all question in minutes
    private Integer timeForOneQues;
    private Integer timeForAllQues;

    private boolean active;
    private boolean deleted;
}
