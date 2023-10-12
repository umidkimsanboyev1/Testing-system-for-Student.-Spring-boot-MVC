package uz.master.demotest.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.TestService;

import java.util.List;

@Controller
@RequestMapping(value = "/teacher/*")
public class TeacherSearchController {

    private final AuthUserService authUserService;

    private final TestService testService;

    public TeacherSearchController(AuthUserService authUserService, TestService testService) {
        this.authUserService = authUserService;
        this.testService = testService;
    }


    @GetMapping("/teacher/test/")
    public String getTest(@RequestParam("text") String text, Model model){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<Test> tests = testService.getTestBySearch(text);
        if(tests.size() == 0){
            model.addAttribute("error", "Ushbu " + text + " so'z bo'yicha ma'lumotlar toplmadi");
            return "/teacher/myTests";
        }
        model.addAttribute("tests", tests);
        model.addAttribute("currentPage", 1);
        model.addAttribute("page", 1);
        return "/teacher/myTests";
    }

}
