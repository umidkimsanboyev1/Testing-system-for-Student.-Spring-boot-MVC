package uz.master.demotest.controller.student;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.test.CheckingDto;
import uz.master.demotest.dto.test.RequestTestDto;
import uz.master.demotest.dto.test.ResultDto;
import uz.master.demotest.dto.test.TestIntroductionDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.test.SendQuestion;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.student.StudentService;
import uz.master.demotest.services.test.QuestionService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        AuthUser authUser = authUserService.getAuthUser();
        List<Test> testList = studentService.getTestList(authUser);
        if(testList.size() == 0){
            model.addAttribute("user", sessionUser.getFullName());
            return "/student/success";
        }
        model.addAttribute("tests", testList);
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
            model.addAttribute("error", "Uzr siz bu testni oldin ishlagansiz. Qayta topshirish uchun mutasaddilar bilan bog'laning");
            return "/error/error";
        }
        try{
            authUserService.saveToAuthUser(testId);
            model.addAttribute("question", questionService.getFirstQuestion(testId, sessionUser.getId()));
            LocalDateTime userTime = authUserService.getUserTime();
            System.out.println(userTime);
            model.addAttribute("time", userTime);
        } catch (Exception e){

        }
        return "/student/testStart";
    }


    @GetMapping(value = "/question/{generatedNumber}")
    public String getQuestion(@PathVariable Integer generatedNumber, Model model) {
        if (questionService.checkTime()) {
            return "redirect:/student/result";
        }
        return getModel(generatedNumber, model);
    }

    @NotNull
    private String getModel(@PathVariable Integer generatedNumber, Model model) {
        if (questionService.checkTestProgress(generatedNumber)) {
            LocalDateTime userTime = authUserService.getUserTime();
            System.out.println(userTime);
            model.addAttribute("time", userTime);
            return progressServices(generatedNumber, model);
        } else {
            System.out.println(sessionUser.getQuesNumber());
            return "redirect:/student/question/" + sessionUser.getQuesNumber();
        }
    }

    @PostMapping(value = "/question/")
    public String PostQuestion(Model model, @ModelAttribute CheckingDto checking) {
        Integer generatedNumber = checking.getGeneratedNumber();
        Long id = sessionUser.getId();
        questionService.mapAnswers(checking, id, generatedNumber);
        System.out.println(checking);
        return getModel(generatedNumber, model);
    }

    private @NotNull String progressServices(Integer generatedNumber, Model model) {
        SendQuestion question;
        try {
            question = questionService.getQuestionForSessionUser(generatedNumber);
            authUserService.setQuesNumber(generatedNumber);
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
