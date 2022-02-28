package uz.master.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.utils.SessionUser;
import uz.master.demotest.services.project.ProjectService;

@Controller
public class BaseController {



    private final SessionUser user;
    private final ProjectService service;

    public BaseController(SessionUser user, ProjectService service) {
        this.user = user;
        this.service = service;
    }


    @RequestMapping(value = { "/","/home"})
    public String home() {

        UserDetails details = user.getInstance();
        String code = details.getRole().getCode();

        if(code.equals("ADMIN")){
            return "redirect:organization/list";
        }else{
            return "redirect:project/all/" + details.getOrganization();
        }

    }



    @PostMapping(value = {"/search"})
    public String search() {
        return "search";
    }

}
