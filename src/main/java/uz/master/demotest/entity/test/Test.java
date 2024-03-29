package uz.master.demotest.entity.test;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
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
    private String ownerName;
    private String file;
    private String createdTime;
    private Long subjectId;
    // group ID for group
    private Long groupId;
    private Long ownerId;
    private Integer numberOfQuestion;
    // time for one and all question in minutes
    private Integer timeForOneQues;
    private Integer timeForAllQues;
    private boolean completed;
    private boolean archived;
    private boolean active;
    private boolean deleted;
    private Long allQuestion;
}
