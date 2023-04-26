package uz.master.demotest.entity.auth;


import lombok.*;
import uz.master.demotest.entity.Auditable;
import uz.master.demotest.enums.Role;

import javax.persistence.Column;
import javax.persistence.Entity;
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
public class AuthUser extends Auditable {

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Role role;

    private String fullName;

    private String groupName;

    private Long testId;

    private Integer QuesId;

    @Column(columnDefinition = "0")
    private Integer tryingCount;
    private Integer timeLeft;

    private boolean active;

    private boolean blocked;


}
