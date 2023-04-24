package uz.master.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.utils.SessionUser;

@Controller
public class BaseController {
    private final SessionUser user;

    public BaseController(SessionUser user) {
        this.user = user;
    }

    @RequestMapping(value = {"", "/", "/home"})
    public String home() {
        UserDetails details = user.getInstance();
        String code = details.getRole().toString();
        System.out.println(code);
        if (code.equals("ADMIN")) {
            return "redirect:admin/allTests";
        } else if (code.equals("TEACHER")) {
            return "redirect:teacher/myTests";
        } else {
            return "redirect:student/listTests";
        }
    }
}
