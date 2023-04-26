package uz.master.demotest.controller.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.test.RequestTestDto;
import uz.master.demotest.dto.test.TestIntroductionDto;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.student.StudentService;
import uz.master.demotest.services.test.QuestionService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

@Controller
@RequestMapping("/student/*")
public class StudentController {

    private final AuthUserService authUserService;
    private final TestService testService;
    private final StudentService studentService;
    private final QuestionService questionService;

    private final SessionUser sessionUser;


    public StudentController(AuthUserService authUserService, StudentService studentService, AuthUserService userService, TestService testService, QuestionService questionService, SessionUser sessionUser) {
        this.authUserService = authUserService;
        this.studentService = studentService;
        this.testService = testService;
        this.questionService = questionService;
        this.sessionUser = sessionUser;
    }

    @GetMapping("/listTests")
    public String getTestList(Model model) {
        model.addAttribute("tests", studentService.getTestList());
        model.addAttribute("user", sessionUser.getFullName());
        return "student/listTests";
    }

    @PostMapping(value = "/introduction")
    public String postIntroduction(@ModelAttribute RequestTestDto dto, Model model){
        TestIntroductionDto test = testService.getTestIntroduction(dto.getId());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("test", test);
        return "/student/introductionTest";
    }

    @GetMapping(value = "/startTest/{id}")
    public String startTest(Model model, @PathVariable Long id){
        model.addAttribute("user", sessionUser.getFullName());
        if(!authUserService.testStart(id)){
            return "/error/403";
        }
        questionService.startTest(id);
//        model.addAttribute("test", );
        model.addAttribute("time", authUserService.getSessionUserTime(id));
        model.addAttribute("question", questionService.getQuestionForSessionUse(1));
        return "/student/test";
    }

}
