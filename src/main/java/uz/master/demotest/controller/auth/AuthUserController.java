package uz.master.demotest.controller.auth;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.utils.SessionUser;

@Controller
@RequestMapping("/auth/*")
public class AuthUserController {

    private final AuthUserService service;
    private final SessionUser user;

    public AuthUserController(AuthUserService service, SessionUser user) {
        this.service = service;
        this.user = user;
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String loginPage() {
        return "auth/login";
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logoutPage() {
        return "auth/logout";
    }

    @RequestMapping(value = "reset/{token}")
    public String checkToken(@PathVariable String token) {
        if (service.checkToken(token)) {
            return "redirect:/auth/login";
        }
        return "auth/reset";
    }

    @GetMapping(value = "/resetPassword")
    public String getResetPasswordPage(){
        return "/auth/edit";
    }

    @PostMapping(value = "/resetPassword")
    public String resetPassword(@ModelAttribute ResetPassword dto){
         return service.resetPassword(dto) ? "redirect:/home" : "redirect:/auth/resetPassword";
    }
}
