package uz.master.demotest.entity.comment;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import uz.master.demotest.entity.Auditable;
import uz.master.demotest.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author Bekpulatov Shoxruh, Wed 3:16 PM. 2/23/2022
 */

@Getter
@Setter
@Entity
public class Comment extends Auditable {



    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String authorUsername;

    @Column(nullable = false)
    private Long taskId;
}
