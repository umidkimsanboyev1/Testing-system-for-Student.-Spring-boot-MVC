package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter

public class AuthUserUpdateDto extends GenericDto {
    private String firstName;
    private String lastname;
    @NotBlank
    private String username;
    @Pattern(regexp = "[0-9a-zA-Z]{2,16}(@)[0-9a-zA-Z]{2,5}(.)[0-9a-zA-Z]{2,3}")
    @NotBlank
    private String email;
}
