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
public class Task extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private int order;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String priority;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "column_id_id", nullable = false)
    private ProjectColumn column;

    @OneToMany(cascade = CascadeType.ALL)
    private List<AuthUser> members;
}
