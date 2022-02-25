package uz.master.demotest.dto.organization;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.BaseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrganizationCreateDto implements BaseDto {

    @Size(min = 2, max = 30, message = "value for title must be between {min} and {max}")
    private String name;

    @NotBlank(message = "Logotype not be null or Blank")
    private String logo;

    @NotBlank(message = "E-mail not be null or Blank")
    private String email;

    @NotBlank(message = "Web site Address not be null or Blank")
    private String webSite;

    @NotBlank(message = "Registration number not be null or Blank")
    private Long registrationNumber;

    private String phone;

    private String address;


}
