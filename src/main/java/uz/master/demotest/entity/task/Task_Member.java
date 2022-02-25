package uz.master.demotest.entity.task;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Bekpulatov Shoxruh, Wed 5:07 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
public class Task_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String username;
    private Long taskId;
}
