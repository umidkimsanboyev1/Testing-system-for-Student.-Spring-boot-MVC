package uz.master.demotest.dto.Organization;


import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class OrganizationDto extends GenericDto {

    private Long id;

    private String name;

    private String logo;

    private String email;

    private String phone;

    private String address;

    private Long registrationNumber;

    private String webSite;
}
