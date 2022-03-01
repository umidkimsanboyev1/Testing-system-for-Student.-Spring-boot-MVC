package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import uz.master.demotest.dto.GenericDto;

@Getter
@Setter
public class AuthUserUpdateDto extends GenericDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String phone;
    private MultipartFile photo;
    private String photoPath;
}
