package uz.master.demotest.dto.Organization;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class OrganizationDto {

    private Long id;

    private String name;

    private String logo;

    private String email;

    private String phone;

    private String address;

    private Long registrationNumber;

    private String webSite;
}
