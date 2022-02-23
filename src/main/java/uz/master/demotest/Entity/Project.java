package uz.master.demotest.Entity;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.Entity.auth.AuthUser;

import javax.persistence.*;
import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Wed 2:40 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
public class Project extends Auditable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String tz;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "pm_id", nullable = false)
    private AuthUser pm;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AuthUser> members;

}
