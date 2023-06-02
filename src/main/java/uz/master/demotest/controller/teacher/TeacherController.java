package uz.master.demotest.controller.teacher;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.test.OverAllResultDTO;
import uz.master.demotest.dto.test.TestCreateDto;
import uz.master.demotest.entity.result.OverAllResult;
import uz.master.demotest.entity.test.Subject;
import uz.master.demotest.entity.test.Test;
import uz.master.demotest.enums.Role;
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

    public TeacherController(AuthUserService authUserService, TestService testService, SessionUser sessionUser, SubjectService subjectService, GroupService groupService, OverAllResultService overAllResultService) {
        this.authUserService = authUserService;
        this.testService = testService;
        this.sessionUser = sessionUser;
        this.subjectService = subjectService;
        this.groupService = groupService;
    }

    @GetMapping(value = "myTests")
    public String myTests(Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("tests", testService.getMyOwnerTests());
        return "teacher/myTests";
    }

    @GetMapping(value = "/createTest")
    public String getCreatePage(Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/createTest";
    }

    @GetMapping(value = "/createTest?error")
    public String getCreatePageWithError(Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("error", "Bunday nomli test mavjud yoki ma'lumotlar to'liq emas");
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/createTest";
    }

    @GetMapping(value = "/editTest/{id}")
    public String getEditPage(@PathVariable Long id, Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        Test testById = testService.getTest(id);
        model.addAttribute("test", testById);
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/editTest";
    }

    @PostMapping(value = "/editTest")
    public String updateTest(@ModelAttribute Test test) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        try {
            testService.updateTest(test);
        } catch (Exception e){
            return "redirect:/teacher/editTest/" + test.getId();
        }
        return "redirect:/home";
    }

    @GetMapping(value = "/createQuestion")
    public String getCreateQuestionPage(Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/createQuestion";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        testService.doAction(id);
        return "redirect:/teacher/myTests";
    }

    @GetMapping(value = "/deleteTest/{id}")
    public String delete(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        testService.delete(id);
        return "redirect:/teacher/myTests";
    }

    @PostMapping(value = "/createTest")
    public String createTest(@ModelAttribute TestCreateDto dto, Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        Long testId = null;
        try {
            testId = testService.createTest(dto);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "redirect:/teacher/createTest?error";
        }
        boolean b = testService.restoreExcelFileToTest(testId, dto.getFile());
        if(!b) {
            testService.delete(testId);
            model.addAttribute("error", "Test yaratishda hatolik \n Iltimos test faylidagi savollar soni testdagi savollar soniga mosligini hamda test fayli togri formalanganligini tekshiring!!!");
            return "/error/error";
        }
        return "redirect:/teacher/myTests";
    }

    @GetMapping(value = "/results")
    public String getResults(Model model) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        try {
            List<Test> tests = testService.getAllResultsForTeacher();
            model.addAttribute("tests", tests);
        } catch (Exception ex) {
            model.addAttribute("error", "Ma'lumotlar topilmadi!");
            return "/error/error";
        }
        return "/teacher/results";
    }

    @GetMapping(value = "/result/{id}")
    public String getResult(Model model, @PathVariable Long id) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        Long userId = authUserService.setAuthUserSelectedGroup(null);
        List<OverAllResult> allResult = testService.getOverAllResultByTestId(id);
        model.addAttribute("id", id);
        model.addAttribute("groups", groupService.getAllGroupsByTest(id));
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("allResults", allResult);
        return "/teacher/result";
    }

    @GetMapping(value = "/result/{testId}/{groupName}")
    public String getAllStudentsByGroup(Model model, @PathVariable String groupName, @PathVariable Long testId) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        try{
            Long userId = authUserService.setAuthUserSelectedGroup(groupName);
            List<OverAllResult> allResult = testService.getOverAllResultByTestIdAndGroupNameForTeacher(testId, groupName);
            model.addAttribute("id", testId);
            model.addAttribute("groups", groupService.getAllGroupsByTest(testId));
            model.addAttribute("user", sessionUser.getFullName());
            model.addAttribute("allResults", allResult);
        } catch(Exception e){
            model.addAttribute("error", "Bu sizning testingiz emas yoki bunday testmavjud emas!!!");
            return "/error/error";
        }
        return "/teacher/result";
    }


    @GetMapping(value = "/checkDeleteTest/{id}")
    public String checkDeleteTest(Model model, @PathVariable Long id) {
        if(!authUserService.checkToRole(Role.TEACHER)){
            return "redirect:/home";
        }
        model.addAttribute("testName", testService.getTestById(id));
        model.addAttribute("testId", id);
        model.addAttribute("user", sessionUser.getFullName());
        return "/teacher/checkDeleteTest";
    }



}
