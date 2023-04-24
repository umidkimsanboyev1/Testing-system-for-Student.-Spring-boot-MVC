package uz.master.demotest.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.TestService;

@Controller
@RequestMapping(value = "/teacher/*")
public class TeacherController {

    private final AuthUserService authUserService;

    private final TestService testService;

    public TeacherController(AuthUserService authUserService, TestService testService) {
        this.authUserService = authUserService;
        this.testService = testService;
    }

    @GetMapping(value = "myTests")
    public String getResults(Model model){
        model.addAttribute("user", authUserService.getUserName());
        model.addAttribute("tests", testService.getMyOwnerTests());
        return "teacher/myTests";
    }
    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id){
        testService.doAction(id);
        return "redirect:/teacher/myTests";
    }

}
