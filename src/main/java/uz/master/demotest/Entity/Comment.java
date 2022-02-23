package uz.master.demotest.Entity;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.Entity.auth.AuthUser;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthUser author;
}
