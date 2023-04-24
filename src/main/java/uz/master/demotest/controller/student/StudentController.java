package uz.master.demotest.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.student.StudentService;

@Controller
@RequestMapping("/student/*")
public class StudentController {

    private final AuthUserService authUserService;
    private final StudentService studentService;


    public StudentController(AuthUserService authUserService, StudentService studentService, AuthUserService userService) {
        this.authUserService = authUserService;
        this.studentService = studentService;
    }

    @GetMapping("/listTests")
    public String getTestList(Model model) {
        model.addAttribute("tests", studentService.getTestList());
        model.addAttribute("user", authUserService.getUserName());
        return "student/listTests";
    }
}
