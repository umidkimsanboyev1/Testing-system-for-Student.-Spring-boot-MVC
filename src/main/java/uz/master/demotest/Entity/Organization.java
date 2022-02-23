package uz.master.demotest.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Bekpulatov Shoxruh, Wed 2:40 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
public class Organization extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private String logo;

    @Column(nullable = false)
    private String email;

    private String phone;

    private String address;

    @Column(nullable = false)
    private Long registrationNumber;

    private String webSite;

    @Column(nullable = false)
    private String status;
}
