package uz.master.demotest.entity.column;


import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;

/**
 * @author Bekpulatov Shoxruh, Wed 3:09 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
public class ProjectColumn extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int columnOrder;

    @Column(nullable = false)
    private Long projectId;


}
