package uz.master.demotest.entity.auth;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@Entity
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    protected Long id;


    @Lob
    @Column(name = "private_token", nullable = false)
    private String privateToken;

    @Column(name = "expire")
    private LocalTime expire;

}
