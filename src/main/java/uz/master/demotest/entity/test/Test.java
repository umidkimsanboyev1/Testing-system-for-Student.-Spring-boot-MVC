package uz.master.demotest.entity.test;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String file;
    private Long subjectId;

    // group ID for group
    private Long groupId;
    private Long ownerId;
    private Integer numberOfQuestion;
    // time for one and all question in minutes
    private Integer timeForOneQues;
    private Integer timeForAllQues;
    private boolean completed;

    private boolean active;
    private boolean deleted;
}
