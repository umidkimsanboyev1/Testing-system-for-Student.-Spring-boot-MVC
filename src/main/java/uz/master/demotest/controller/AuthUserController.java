package uz.master.demotest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.services.auth.AuthUserService;

@Controller
@RequestMapping("/auth/*")
public class AuthUserController {

    private final AuthUserService service;

    public AuthUserController(AuthUserService service) {
        this.service = service;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "auth/login";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "auth/logout";
    }

    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public String addPage() {
        return "auth/addUser";
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String add(@ModelAttribute AuthUserCreateDto dto) {
        service.create(dto);
        return "redirect:/project/all";
    }

//    @RequestMapping(value = "/auth/reset/{token}",method = RequestMethod.GET)

    @RequestMapping(value = "/auth/forgotPassword",method = RequestMethod.GET)
    public String forgotPage(){
        return "auth/forgot";
    }
    @RequestMapping(value = "/auth/forgotPassword",method = RequestMethod.POST)
    public String forgotPage(@RequestParam String email,@RequestParam String username){
        service.sendMail(email,username);
        return "redirect:auth/login";
    }

}
