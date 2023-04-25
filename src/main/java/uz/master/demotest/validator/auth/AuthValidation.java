package uz.master.demotest.validator.auth;

import org.springframework.stereotype.Component;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.enums.Role;
import uz.master.demotest.repositories.AuthUserRepository;


public class AuthValidation {
    private final AuthUserRepository authUserRepository;

    public AuthValidation(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }


}
