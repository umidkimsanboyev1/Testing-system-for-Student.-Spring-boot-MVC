package uz.master.demotest.entity.organization;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uz.master.demotest.entity.Auditable;
import uz.master.demotest.entity.BaseEntity;

import javax.persistence.*;

/**
 * @author Kimsanboyev Umidjon, Wed 2:40 PM. 2/23/2022
 */
@Getter
@Setter
@Entity
@ToString
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

    private Boolean blocked = false;

    private Boolean deleted = false;
}
