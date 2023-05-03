package uz.master.demotest.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.test.OverAllResultDTO;
import uz.master.demotest.dto.test.TestCreateDto;
import uz.master.demotest.entity.test.Subject;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.services.GroupService;
import uz.master.demotest.services.SubjectService;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.OverAllResultService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.util.List;

@Controller
@RequestMapping(value = "/teacher/*")
public class TeacherController {
    private final AuthUserService authUserService;

    private final TestService testService;
    private final SessionUser sessionUser;
    private final SubjectService subjectService;
    private final GroupService groupService;
    private final OverAllResultService overAllResultService;

    public TeacherController(AuthUserService authUserService, TestService testService, SessionUser sessionUser, SubjectService subjectService, GroupService groupService, OverAllResultService overAllResultService) {
        this.authUserService = authUserService;
        this.testService = testService;
        this.sessionUser = sessionUser;
        this.subjectService = subjectService;
        this.groupService = groupService;
        this.overAllResultService = overAllResultService;
    }

    @GetMapping(value = "myTests")
    public String getResults(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("tests", testService.getMyOwnerTests());
        return "teacher/myTests";
    }

    @GetMapping(value = "/createTest")
    public String getCreatePage(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "/teacher/createTest";
    }

    @GetMapping(value = "/createTest?error")
    public String getCreatePageWithError(Model model) {
        model.addAttribute("error", "Bunday nomli test mavjud yoki ma'lumotlar to'liq emas");
        model.addAttribute("user", sessionUser.getFullName());
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        return "/teacher/createTest";
    }

    @GetMapping(value = "/editTest/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        Test testById = testService.getTest(id);
        model.addAttribute("test", testById);
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/editTest";
    }

    @PostMapping(value = "/editTest")
    public String updateTest(@ModelAttribute Test test) {
        try {
            testService.updateTest(test);
        } catch (Exception e){
            return "redirect:/teacher/editTest/" + test.getId();
        }
        return "redirect:/home";
    }

    @GetMapping(value = "/createQuestion")
    public String getCreateQuestionPage(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/createQuestion";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id) {
        testService.doAction(id);
        return "redirect:/teacher/myTests";
    }

    @GetMapping(value = "/deleteTest/{id}")
    public String delete(@PathVariable Long id) {
        testService.delete(id);
        return "redirect:/teacher/myTests";
    }

    @PostMapping(value = "/createTest")
    public String createTest(@ModelAttribute TestCreateDto dto) {
        try {
            Long testId = testService.createTest(dto);
            testService.restoreExcelFileToTest(testId, dto.getFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/teacher/createTest?error";
        }
        return "redirect:/teacher/myTests";
    }

    @GetMapping(value = "/results")
    public String getResult(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        try {
            List<OverAllResultDTO> results = testService.getAllResultsForTeacher();
            model.addAttribute("groups", groupService.getAllGroups());
            model.addAttribute("results", results);

        } catch (Exception ex) {
            model.addAttribute("error", "Ma'lumotlar topilmadi!");
            return "/error/error";
        }
        return "/teacher/results";
    }

    @GetMapping(value = "/results/{groupName}")
    public String getAllStudentsByGroup(Model model, @PathVariable String groupName) {
        Long id = authUserService.setAuthUserSelectedGroup(groupName);
        List<OverAllResultDTO> allResult = testService.getAllResultsByGroup(id, groupName);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("results", allResult);
        return "/teacher/results";
    }


    @GetMapping(value = "/checkDeleteTest/{id}")
    public String checkDeleteTest(Model model, @PathVariable Long id) {
        model.addAttribute("testName", testService.getTestById(id));
        model.addAttribute("testId", id);
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/checkDeleteTest";
    }



}
