package uz.master.demotest.controller.student;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.test.CheckingDto;
import uz.master.demotest.dto.test.RequestTestDto;
import uz.master.demotest.dto.test.ResultDto;
import uz.master.demotest.dto.test.TestIntroductionDto;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.student.StudentService;
import uz.master.demotest.services.test.QuestionService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/student/*")
public class StudentController {

    private final AuthUserService authUserService;
    private final TestService testService;
    private final StudentService studentService;
    private final QuestionService questionService;

    private final SessionUser sessionUser;


    public StudentController(AuthUserService authUserService, StudentService studentService, TestService testService, QuestionService questionService, SessionUser sessionUser) {
        this.authUserService = authUserService;
        this.studentService = studentService;
        this.testService = testService;
        this.questionService = questionService;
        this.sessionUser = sessionUser;
    }

    @GetMapping(value = "/endTest")
    public String getConfirmPage(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        return "/student/confirm";
    }

    @GetMapping(value = "/result")
    public String getResults(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        ResultDto result = testService.getResult();
        model.addAttribute("result", result);
        return "/student/result";
    }

    @GetMapping("/listTests")
    public String getTestList(Model model) {
        model.addAttribute("tests", studentService.getTestList());
        model.addAttribute("user", sessionUser.getFullName());
        return "student/listTests";
    }

    @PostMapping(value = "/introduction")
    public String postIntroduction(@ModelAttribute RequestTestDto dto, Model model) {
        TestIntroductionDto test = testService.getTestIntroduction(dto.getId());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("test", test);
        return "/student/introductionTest";
    }

    @GetMapping(value = "/startTest/{testId}")
    public String startTest(Model model, @PathVariable Long testId) {
        model.addAttribute("user", sessionUser.getFullName());
        questionService.startTest(testId);
        boolean check = authUserService.checkTest(testId);
        if (!check) {
            return "/error/403";
        }
        authUserService.saveToAuthUser(testId);
        model.addAttribute("question", questionService.getFirstQuestion(testId, sessionUser.getId()));
        model.addAttribute("time", sessionUser.getTime());
        return "/student/testStart";
    }


    @GetMapping(value = "/question/{generatedNumber}")
    public String getQuestion(@PathVariable Integer generatedNumber, Model model) {
        if (questionService.checkTime()) {
            return "redirect:/student/result";
        }
        if (questionService.checkTestProgress(generatedNumber)) {
            return progressServices(generatedNumber, model);
        } else {
            System.out.println(sessionUser.getQuesNumber());
            return "redirect:/student/question/" + sessionUser.getQuesNumber();
        }
    }

    @PostMapping(value = "/question/{generatedNumber}")
    public String PostQuestion(@PathVariable Integer generatedNumber, Model model, @ModelAttribute CheckingDto checking) {
        Long id = sessionUser.getId();
        questionService.mapAnswers(checking, id, generatedNumber);
        System.out.println(checking);
        if (questionService.checkTestProgress(generatedNumber)) {
            model.addAttribute("time", sessionUser.getTime());
            return progressServices(generatedNumber, model);
        } else {
            System.out.println(sessionUser.getQuesNumber());
            return "redirect:/student/question/" + sessionUser.getQuesNumber();
        }
    }

    private @NotNull String progressServices(Integer generatedNumber, Model model) {
        SendQuestion question;
        try {
            question = questionService.getQuestionForSessionUser(generatedNumber);
            authUserService.setQuesNumber(generatedNumber);
            LocalDateTime time = sessionUser.getTime();
            System.out.println(time);
            model.addAttribute("question", question);
            model.addAttribute("user", sessionUser.getFullName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "/error/error";
        }
        if (generatedNumber.equals(1)) return "/student/testStart";
        if (questionService.checkToEndTest(generatedNumber)) return "/student/testEnd";
        return "/student/test";
    }
}
