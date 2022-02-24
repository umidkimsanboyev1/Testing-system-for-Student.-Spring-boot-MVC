package uz.master.demotest.dto.organization;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrganizationUpdateDto {

    @Size(min = 2, max = 30, message = "value for title must be between {min} and {max}")
    private String name;

    @NotBlank(message = "Logotype not be null or Blank")
    private String logo;

    @NotBlank(message = "E-mail not be null or Blank")
    private String email;

    private String phone;

    private String address;

    @NotBlank(message = "Web site Address not be null or Blank")
    private String webSite;

}
