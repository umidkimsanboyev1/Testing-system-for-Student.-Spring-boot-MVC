package uz.master.demotest.controller.auth;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.auth.AddAdminDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.AuthUserUpdateDto;
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


    @RequestMapping(value = "addUser", method = RequestMethod.GET)
    public String addPage() {
        return "auth/addUser";
    }


    @RequestMapping(value = "addAdmin/{id}", method = RequestMethod.GET)
    public String addAdminPage(Model model,@PathVariable Long id) {
        model.addAttribute("organizationId", id);
        return "auth/addAdmin";
    }

    @RequestMapping(value = "addUser", method = RequestMethod.POST)
    public String addAdmin(@ModelAttribute AuthUserCreateDto dto) {
        dto.setOrganizationId(user.getOrgId());
        service.create(dto);
        return "redirect:/project/all/"+user.getOrgId();
    }

    @RequestMapping(value = "addAdmin/{id}", method = RequestMethod.POST)
    public String addAdminPage(@ModelAttribute AddAdminDto dto, @PathVariable Long id) {
        service.createAdmin(dto, id);
        return "redirect: /organization/list";
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
    private String profile(Model model){
        model.addAttribute("authUser",service.get(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId()));
        return "auth/profil";
    }



    @RequestMapping("update")
    public String update(@ModelAttribute AuthUserUpdateDto dto){
        dto.setId(user.getId());
        service.update(dto);
        return "redirect:auth/profil";
    }
}
