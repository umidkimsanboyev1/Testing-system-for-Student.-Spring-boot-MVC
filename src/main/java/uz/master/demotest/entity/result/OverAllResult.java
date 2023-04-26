package uz.master.demotest.entity.result;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OverAllResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private Long testId;
    private String subject;
    private Date startedTime;
    private Date passedTime;
    private String takerUser;
    private Long takerUserId;
    private Integer numberOfAllQues;
    private Integer correctAnsweredQues;
    private Integer efficiency;

}
