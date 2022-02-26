package uz.master.demotest.entity.auth;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.Time;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import uz.master.demotest.entity.Auditable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Getter
@Setter
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;


    @Column(name = "private_token", nullable = false)
    private String privateToken;


    private LocalDateTime expire=LocalDateTime.of(LocalDate.now(),LocalTime.ofSecondOfDay(LocalTime.now().getSecond()+300));

}