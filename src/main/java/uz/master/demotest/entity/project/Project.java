package uz.master.demotest.entity.project;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author Bekpulatov Shoxruh, Wed 2:40 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
public class Project extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String tz;

    @Column(nullable = false)
    private Long orgId;

    @Column(nullable = false)
    private Long teamLeaderId;


    @CreatedDate
    @Column(name = "deadline", columnDefinition = "date")
    private LocalDate deadline;


}
