package uz.master.demotest.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.student.StudentService;
import uz.master.demotest.utils.SessionUser;

@Controller
@RequestMapping("/student/*")
public class StudentController {

    private final AuthUserService authUserService;
    private final StudentService studentService;

    private final SessionUser sessionUser;


    public StudentController(AuthUserService authUserService, StudentService studentService, AuthUserService userService, SessionUser sessionUser) {
        this.authUserService = authUserService;
        this.studentService = studentService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/listTests")
    public String getTestList(Model model) {
        model.addAttribute("tests", studentService.getTestList());
        model.addAttribute("user", sessionUser.getFullName());
        return "student/listTests";
    }
}
