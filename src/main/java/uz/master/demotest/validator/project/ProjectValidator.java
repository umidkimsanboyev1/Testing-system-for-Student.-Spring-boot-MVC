package uz.master.demotest.validator.project;

import org.springframework.stereotype.Component;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.validator.Validator;

@Component
public class ProjectValidator implements Validator {
    private final AuthUserService service;

    public ProjectValidator(AuthUserService service) {
        this.service = service;
    }
//    private boolean checkId(Long id){
//
//    }
}
