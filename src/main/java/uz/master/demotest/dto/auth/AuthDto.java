package uz.master.demotest.dto.auth;



import lombok.Getter;
import lombok.Setter;
import uz.master.demotest.dto.GenericDto;
import uz.master.demotest.entity.auth.AuthRole;
import uz.master.demotest.enums.Role;

@Getter
@Setter

public class AuthDto extends GenericDto {
    private Long orgId;
    private String firstName;
    private String lastName;
    private Role role;
    private String username;
    private String picturePath;
    private String phone;
    private String email;
}
