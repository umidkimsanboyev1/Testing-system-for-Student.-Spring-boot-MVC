package uz.master.demotest.entity.task;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.Auditable;
import uz.master.demotest.enums.Level;
import uz.master.demotest.enums.Priority;

import javax.persistence.*;

/**
 * @author Bekpulatov Shoxruh, Wed 2:40 PM. 2/23/2022
 */
@Getter
@Setter
@Entity(name = "Tasks")
public class Task extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private int taskOrder;

    @Column(nullable = false)
    private String level = Level.EASY.name();

    @Column(nullable = false)
    private String priority = Priority.EASY.name();

    @Column(nullable = false)
    private Long columnId;

}
