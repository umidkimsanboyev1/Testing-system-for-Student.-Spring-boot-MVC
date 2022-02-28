package uz.master.demotest.controller.auth;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.auth.AddAdminDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.ResetPassword;
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
    public String addAdmin(@ModelAttribute AuthUserCreateDto dto) {
        service.create(dto);
        return "redirect:/project/all";
    }

    @RequestMapping(value = "addAdmin/{id}", method = RequestMethod.POST)
    public String addAdminPage(@ModelAttribute AddAdminDto dto, @PathVariable Long id) {
        service.createAdmin(dto, id);
        return "redirect: /organization/list";
    }


    @RequestMapping(value = "addUser/", method = RequestMethod.POST)
    public String add(@ModelAttribute AuthUserCreateDto dto) {
        service.create(dto);
        return "redirect:/project/all";
    }


    @RequestMapping(value = "reset/{token}")
    public String checkToken(@PathVariable String token) {
        if (service.checkToken(token)) {
            return "redirect:/auth/login";
        }
        return "auth/reset";

    }

    @RequestMapping(value = "reset", method = RequestMethod.POST)
    public String checkToken(@ModelAttribute ResetPassword password) {
        service.resetPassword(password);
        return "redirect:/auth/login";

    }


    @RequestMapping(value = "forgot", method = RequestMethod.GET)
    public String forgotPage() {
        return "auth/forgot";
    }


    @RequestMapping(value = "forgot", method = RequestMethod.POST)
    public String forgotPage(@RequestParam String email, @RequestParam String username) {
        service.sendMail(email, username);
        return "redirect:/auth/login";
    }

    @RequestMapping("/profil")
    private String profile(){
        return "auth/profil";
    }


}
