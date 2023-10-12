package uz.master.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.utils.SessionUser;

@Controller
public class BaseController {
    private final SessionUser user;
    private final AuthUserService service;

    public BaseController(SessionUser user, AuthUserService service) {
        this.user = user;
        this.service = service;
    }

    @RequestMapping(value = {"", "/", "/home"})
    public String home() {
        UserDetails details = user.getInstance();
        String code = details.getRole().toString();
        System.out.println(code);
        if (code.equals("ADMIN")) {
            return "redirect:/admin/results/1";
        } else if (code.equals("TEACHER")) {
            return "redirect:/teacher/myTests";
        } else if(code.equals("DEKANAT")){
            return "redirect:/oquvBolim/results";
        } else {
            return "redirect:/student/listTests";
        }
    }


    @GetMapping(value = "/logout")
    public String getLogout(){
        Long testId = service.getAuthUser().getTestId();
        if(testId == null){
            return "redirect:/auth/logout";
        }
        return "redirect:/student/result";
    }


}
