package uz.master.demotest.Entity.auth;


import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.Entity.Auditable;
import uz.master.demotest.Entity.Organization;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table
public class AuthUser extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", nullable = false)
    private AuthRole role;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    private boolean active = true;

    private boolean blocked;
}
