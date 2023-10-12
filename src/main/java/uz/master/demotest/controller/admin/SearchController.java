package uz.master.demotest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
import uz.master.demotest.services.GroupService;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.OverAllResultService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.util.List;

@Controller
@RequestMapping("/search/*")
public class SearchController {
    private final AuthUserService authUserService;
    private final OverAllResultService overAllResultService;
    private final TestService testService;
    private final SessionUser sessionUser;

    private final GroupService groupService;

    public SearchController(AuthUserService authUserService, OverAllResultService overAllResultService, TestService testService, SessionUser sessionUser, GroupService groupService) {
        this.authUserService = authUserService;
        this.overAllResultService = overAllResultService;
        this.testService = testService;
        this.sessionUser = sessionUser;
        this.groupService = groupService;
    }


    @GetMapping("/admin/teachers/")
    public String getTeachers(@RequestParam("text") String text, Model model){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<AuthUser> teachers = authUserService.getAllAuthUsersByRoleAndBySearch(text, Role.TEACHER);
        if(teachers.size() == 0){
            model.addAttribute("error", "Ushbu " + text + " so'z bo'yicha ma'lumotlar toplmadi");
            return "/admin/teachers";
        }
        model.addAttribute("teachers", teachers);
        return "/admin/teachers";
    }


    @GetMapping("/admin/test/")
    public String getTest(@RequestParam("text") String text, Model model){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<Test> tests = testService.getTestBySearch(text);
        if(tests.size() == 0){
            model.addAttribute("error", "Ushbu " + text + " so'z bo'yicha ma'lumotlar toplmadi");
            return "/admin/listTests";
        }
        model.addAttribute("tests", tests);
        model.addAttribute("currentPage", 1);
        model.addAttribute("page", 1);
        return "/admin/listTests";
    }

    @GetMapping("/admin/students/")
    public String getStudents(@RequestParam("text") String text, Model model){
        text =text.trim();
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<AuthUser> students;
        String selectedGroup = authUserService.getAuthUser().getSelectedGroup();
        if( selectedGroup != null){
            students = authUserService.getAllAuthUsersByRoleAndBySearch(text, Role.STUDENT, selectedGroup);
        } else {
            students = authUserService.getAllAuthUsersByRoleAndBySearch(text, Role.STUDENT);
        }
        if(students.size() == 0){
            model.addAttribute("error", "Ushbu " + text + " so'z bo'yicha ma'lumotlar toplmadi");
            return "/admin/students";
        }

        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("students", students);
        return "/admin/students";
    }

    @GetMapping("/admin/result/{id}")
    public String getStudents(@RequestParam("text") String text, Model model, @PathVariable Long id){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<OverAllResult> results;
        String selectedGroup = authUserService.getAuthUser().getSelectedGroup();
        if( selectedGroup != null){
            results = overAllResultService.findResultByTestIdAndGroupNameAndSearch(id,selectedGroup, text);
        } else {
            results = overAllResultService.findResultByTestIdAndSearch(id,text);
        }
        Test test = testService.getTest(id);
        model.addAttribute("test", test);
        if(results.size() == 0){
            model.addAttribute("error", "Ushbu " + text + " so'z bo'yicha ma'lumotlar toplmadi");
            return "/admin/result";
        }
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("allResults", results);
        return "/admin/result";
    }


}
