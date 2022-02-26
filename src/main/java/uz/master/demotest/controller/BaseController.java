package uz.master.demotest.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.entity.auth.AuthUser;

import java.util.Objects;

@Controller
public class BaseController {

    @RequestMapping(value = {"/home", "/"})
    public String home() {
        return "redirect:/project/all";
    }

    @RequestMapping(value = {"/contacts"})
    public String contacts() {
        return "contatcs";
    }

    @PostMapping(value = {"/search"})
    public String search() {
        return "search";
    }
}
