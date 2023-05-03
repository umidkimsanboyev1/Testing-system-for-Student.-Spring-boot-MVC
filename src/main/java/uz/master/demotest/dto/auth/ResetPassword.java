package uz.master.demotest.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPassword {
    private String oldPassword;
    private String newPassword1;
    private String newPassword2;
}
