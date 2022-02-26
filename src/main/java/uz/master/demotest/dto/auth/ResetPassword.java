package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String username;
    private String password;
    private String confirmPassword;
}
