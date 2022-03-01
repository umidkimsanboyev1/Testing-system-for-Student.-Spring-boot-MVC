package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;

@Getter
@Setter
public class AuthUserUpdateDto extends GenericDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;

}
