package uz.master.demotest.controller.auth;


import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.auth.AddAdminDto;
import uz.master.demotest.dto.auth.AuthUserCreateDto;
import uz.master.demotest.dto.auth.ResetPassword;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.comment.CommentService;
import uz.master.demotest.services.project.ProjectService;
import uz.master.demotest.services.task.TaskService;

@Controller
@RequestMapping("/auth/*")
public class AuthUserController {

    private final AuthUserService authService;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final CommentService commentService;

    public AuthUserController(AuthUserService authService, ProjectService projectService, TaskService taskService, CommentService commentService) {
        this.authService = authService;
        this.projectService = projectService;
        this.taskService = taskService;
        this.commentService = commentService;
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
        authService.create(dto);
        return "redirect:/project/all";
    }

    @RequestMapping(value = "addAdmin/{id}", method = RequestMethod.POST)
    public String addAdminPage(@ModelAttribute AddAdminDto dto, @PathVariable Long id) {
        authService.createAdmin(dto, id);
        return "redirect: /organization/list";
    }


    @RequestMapping(value = "addUser/", method = RequestMethod.POST)
    public String add(@ModelAttribute AuthUserCreateDto dto) {
        authService.create(dto);
        return "redirect:/project/all";
    }


    @RequestMapping(value = "reset/{token}")
    public String checkToken(@PathVariable String token) {
        if (authService.checkToken(token)) {
            return "redirect:/auth/login";
        }
        return "auth/reset";

    }

    @RequestMapping(value = "reset", method = RequestMethod.POST)
    public String checkToken(@ModelAttribute ResetPassword password) {
        authService.resetPassword(password);
        return "redirect:/auth/login";

    }


    @RequestMapping(value = "forgot", method = RequestMethod.GET)
    public String forgotPage() {
        return "auth/forgot";
    }


    @RequestMapping(value = "forgot", method = RequestMethod.POST)
    public String forgotPage(@RequestParam String email, @RequestParam String username) {
        authService.sendMail(email, username);
        return "redirect:/auth/login";
    }

    @RequestMapping("/profil")
    private String profile(Model model) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        int projects = projectService.getProjectCount(user.getId());
        model.addAttribute("projects", projects);

        int tasks = taskService.getTaskCount(user.getId());
        model.addAttribute("tasks", tasks);

        int comments = commentService.getCommentCount(user.getUsername());
        model.addAttribute("comments", comments);

        int actions = taskService.getActionCount(user.getUsername());
        model.addAttribute("actions", actions);

        return "auth/profile";
    }


}
