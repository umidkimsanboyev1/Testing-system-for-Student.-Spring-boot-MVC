package uz.master.demotest.dto.auth;


import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.BaseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AuthUserCreateDto implements BaseDto {

    @Size(min = 3,max = 20)
    @NotBlank
    private String username;
    @NotNull
    @Size(min = 3,max = 16)
    @NotBlank
    private String password;
    @Pattern(regexp = "[0-9a-zA-Z]{2,16}(@)[0-9a-zA-Z]{2,5}(.)[0-9a-zA-Z]{2,3}")
    @NotBlank
    @NotNull
    private String email;
    @NotBlank
    @NotNull
    private Long userRole;
    @NotNull
    @Pattern(regexp = "[+](998)[0-9]{9}")
    @NotBlank
    private String phone;

    private Long organizationId;

}
