package uz.master.demotest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.auth.AddStudentDto;
import uz.master.demotest.dto.test.OverAllResultDTO;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.services.GroupService;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.OverAllResultService;
import uz.master.demotest.services.test.QuestionService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/*")
public class AdminController {
    private final TestService testService;
    private final GroupService groupService;

    private final SessionUser sessionUser;
    private final AuthUserService authUserService;
    private final OverAllResultService overAllResultService;
    private final QuestionService questionService;

    public AdminController(TestService testService, GroupService groupService, SessionUser sessionUser, AuthUserService authUserService, OverAllResultService overAllResultService, QuestionService questionService) {
        this.testService = testService;
        this.groupService = groupService;
        this.sessionUser = sessionUser;
        this.authUserService = authUserService;
        this.overAllResultService = overAllResultService;
        this.questionService = questionService;
    }

    @GetMapping(value = "allTests")
    public String getAllTests(Model model) {
        try {
            List<TestDto> tests = testService.getAllTests();
            model.addAttribute("tests", tests);
            model.addAttribute("user", sessionUser.getFullName());
        } catch (Exception ex) {
            model.addAttribute("error", "Xatolik sodir bo'ldi");
            return "/error/error";
        }
        return "/admin/listTests";
    }

    @GetMapping(value = "/students")
    public String getAllStudents(Model model) {
        authUserService.setSelectedGroupName(null);
        List<AuthUser> allStudents = authUserService.getAllStudents();
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("students", allStudents);
        return "/admin/students";
    }

    @GetMapping(value = "/students/{groupName}")
    public String getAllStudentsByGroup(Model model, @PathVariable String groupName) {
        List<AuthUser> allStudents = authUserService.getAllStudentsByGroup(groupName);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("students", allStudents);
        return "/admin/students";
    }


    @GetMapping(value = "/addStudent")
    public String getAddStudentPage(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroups());
        return "/admin/addStudent";
    }

    @PostMapping(value = "/addStudent")
    public String addStudent(@ModelAttribute AddStudentDto dto, Model model) {
        return authUserService.addStudent(dto) ? "redirect:/admin/students" : "redirect:/admin/addStudent";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id) {
        testService.doAction(id);
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/doActionStudent/{id}")
    public String doActionStudent(@PathVariable Long id) {
        authUserService.doAction(id);
        if (authUserService.checkSelectedGroupName()) {
            return "redirect:/admin/students";
        }
        String selectedGroupName = authUserService.getSelectedGroupName();
        return "redirect:/admin/students/" + selectedGroupName;
    }

    @GetMapping(value = "/blockStudents")
    public String blockStudents() {
        authUserService.actionStudents(false);
        if (authUserService.checkSelectedGroupName()) {
            return "redirect:/admin/students";
        }
        String selectedGroupName = authUserService.getSelectedGroupName();
        return "redirect:/admin/students/" + selectedGroupName;
    }

    @GetMapping(value = "/unBlockStudents")
    public String unBlockStudents() {
        authUserService.actionStudents(true);
        if (authUserService.checkSelectedGroupName()) {
            return "redirect:/admin/students";
        } else {
            String selectedGroupName = authUserService.getSelectedGroupName();
            return "redirect:/admin/students/" + selectedGroupName;
        }
    }

    @GetMapping(value = "/deleteStudent/{id}")
    public String delete(@PathVariable Long id) {
        authUserService.delete(id);
        return "redirect:/admin/students";
    }


    @GetMapping(value = "/checkDeleteTest/{id}")
    public String checkDeleteTest(Model model, @PathVariable Long id) {
        model.addAttribute("testName", testService.getTestById(id));
        model.addAttribute("testId", id);
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/checkDeleteTest";
    }

    @GetMapping(value = "/editTest/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/editTest";
    }

    @GetMapping(value = "/deleteResult/{id}")
    public String deleteResult(@PathVariable Long id){
        overAllResultService.delete(id);
        return "redirect:/admin/results";
    }

    @GetMapping(value = "/results")
    public String getResult(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        try {
            List<OverAllResultDTO> results = testService.getAllResults();
            model.addAttribute("results", results);
        } catch (Exception ex) {
            model.addAttribute("error", "Ma'lumotlar topilmadi!");
            return "/error/error";
        }
        return "/admin/results";
    }
}
