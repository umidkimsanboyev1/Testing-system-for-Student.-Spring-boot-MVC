package uz.master.demotest.entity.project;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;

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


}
