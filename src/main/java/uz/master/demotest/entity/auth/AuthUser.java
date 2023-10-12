package uz.master.demotest.entity.auth;


import lombok.*;
import uz.master.demotest.entity.Auditable;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.enums.Role;
import uz.master.demotest.enums.TypeEducation;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Role role;
    private TypeEducation typeEducation;

    private String fullName;

    private String groupName;

    private String selectedGroup;

    private Long testId;
    private Long viewedTestId;

    private Integer QuesNumber;

    @Column(columnDefinition = "0")
    private Integer tryingCount;
    private LocalDateTime time;

    private boolean active;

    private boolean blocked;


}
