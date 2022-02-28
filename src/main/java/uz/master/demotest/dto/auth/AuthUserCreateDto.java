package uz.master.demotest.dto.auth;


import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.BaseDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class AuthUserCreateDto implements BaseDto {
    private String firstName;
    private String lastname;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @Pattern(regexp = "[0-9a-zA-Z]{2,16}(@)[0-9a-zA-Z]{2,5}(.)[0-9a-zA-Z]{2,3}")
    @NotBlank
    private String email;
    @NotBlank
    private String confirmPassword;
    @NotBlank
    private Long userRole;

}
