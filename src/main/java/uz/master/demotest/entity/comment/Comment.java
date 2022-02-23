package uz.master.demotest.entity.comment;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;

/**
 * @author Bekpulatov Shoxruh, Wed 3:16 PM. 2/23/2022
 */

@Getter
@Setter
@Entity
public class Comment extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private Long taskId;
}
