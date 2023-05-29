package uz.master.demotest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.auth.AddStudentDto;
import uz.master.demotest.dto.auth.StudentsCreateDto;
import uz.master.demotest.dto.test.OverAllResultDTO;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.entity.auth.AuthUser;
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
@RequestMapping(value = "/admin/*")
public class AdminController {
    private final TestService testService;
    private final GroupService groupService;
    private final SubjectService subjectService;

    private final SessionUser sessionUser;
    private final AuthUserService authUserService;
    private final OverAllResultService overAllResultService;

    public AdminController(TestService testService, GroupService groupService, SubjectService subjectService, SessionUser sessionUser, AuthUserService authUserService, OverAllResultService overAllResultService) {
        this.testService = testService;
        this.groupService = groupService;
        this.subjectService = subjectService;
        this.sessionUser = sessionUser;
        this.authUserService = authUserService;
        this.overAllResultService = overAllResultService;
    }

    @GetMapping(value = "allTests")
    public String getAllTests(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
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
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.setSelectedGroupName(null);
        List<AuthUser> allStudents = authUserService.getAllStudents();
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("students", allStudents);
        return "/admin/students";
    }

    @GetMapping(value = "/teachers")
    public String getAllTeachers(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        List<AuthUser> allStudents = authUserService.getAllTeachers();
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("teachers", allStudents);
        return "/admin/teachers";
    }

    @GetMapping(value = "/students/{groupName}")
    public String getAllStudentsByGroup(Model model, @PathVariable String groupName) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.setAuthUserSelectedGroup(groupName);
        List<AuthUser> allStudents = authUserService.getAllStudentsByGroup(groupName);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("students", allStudents);
        return "/admin/students";
    }


    @GetMapping(value = "/addStudent")
    public String getAddStudentPage(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroups());
        return "/admin/addStudent";
    }

    @GetMapping(value = "/groups")
    public String getGroupsPage(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroupsDTO());
        return "/admin/groups";
    }

    @GetMapping(value = "/deleteGroups/{name}")
    public String getDeleteGroupsPage(Model model, @PathVariable String name) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("groupName", name);
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/checkDeleteGroup";
    }

    @GetMapping(value = "/deleteGroup/{name}")
    public String getDeleteGroup(Model model, @PathVariable String name) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.deleteByGroupName(name);
        model.addAttribute("user", sessionUser.getFullName());
        return "redirect:/admin/groups/";
    }

    @GetMapping(value = "/addTeacher")
    public String getAddTeacherPage(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/addTeacher";
    }

    @PostMapping(value = "/addStudent")
    public String addStudent(@ModelAttribute AddStudentDto dto) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        String selectedGroup = authUserService.getAuthUser().getSelectedGroup();
        if(selectedGroup == null){
            return authUserService.addStudent(dto) ? "redirect:/admin/students/" : "redirect:/admin/addStudent";
        }
        return authUserService.addStudent(dto) ? "redirect:/admin/students/" + selectedGroup : "redirect:/admin/addStudent";
    }

    @PostMapping(value = "/addTeacher")
    public String addTeacher(@ModelAttribute AddStudentDto dto) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        return authUserService.addTeacher(dto) ? "redirect:/admin/teachers" : "redirect:/admin/addTeacher";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        testService.doAction(id);
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/doActionStudent/{id}")
    public String doActionStudent(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.doAction(id);
        if (authUserService.checkSelectedGroupName()) {
            return "redirect:/admin/students";
        }
        String selectedGroupName = authUserService.getSelectedGroupName();
        return "redirect:/admin/students/" + selectedGroupName;
    }

    @GetMapping(value = "/doActionTeacher/{id}")
    public String doActionTeacher(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.doAction(id);
        return "redirect:/admin/teachers/";
    }

    @GetMapping(value = "/blockStudents")
    public String blockStudents() {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.actionStudents(false);
        if (authUserService.checkSelectedGroupName()) {
            return "redirect:/admin/students";
        }
        String selectedGroupName = authUserService.getSelectedGroupName();
        return "redirect:/admin/students/" + selectedGroupName;
    }

    @GetMapping(value = "/unBlockStudents")
    public String unBlockStudents() {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
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
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.delete(id);
        return "redirect:/admin/students";
    }
    @GetMapping(value = "/deleteTeacher/{id}")
    public String deleteTeacher(@PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.delete(id);
        return "redirect:/admin/teachers";
    }


    @GetMapping(value = "/checkDeleteTest/{id}")
    public String checkDeleteTest(Model model, @PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("testName", testService.getTestById(id));
        model.addAttribute("testId", id);
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/checkDeleteTest";
    }

    @GetMapping(value = "/deleteTest/{id}")
    public String deleteTest(@PathVariable Long id){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        testService.delete(id);
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/deleteResult/{id}")
    public String deleteResult(@PathVariable Long id){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        overAllResultService.delete(id);
        Long viewedTestId = authUserService.getAuthUser().getViewedTestId();
        return "redirect:/admin/result/" + viewedTestId;
    }

    @GetMapping(value = "/results")
    public String getResult(Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        try {
            List<Test> tests = testService.getAllTest();
            model.addAttribute("tests", tests);
        } catch (Exception ex) {
            model.addAttribute("error", "Ma'lumotlar topilmadi!");
            return "/error/error";
        }
        return "/admin/results";
    }

//    @GetMapping(value = "/results/{groupName}")
//    public String getAllResultsByGroup(Model model, @PathVariable String groupName) {
//        if(!authUserService.checkToRole(Role.ADMIN)){
//            return "redirect:/home";
//        }
//        Long id = authUserService.setAuthUserSelectedGroup(groupName);
//        List<OverAllResultDTO> allResult = testService.getAllResultsByGroup(id, groupName);
//        List<Groups> allGroups = groupService.getAllGroups();
//        model.addAttribute("groups", allGroups);
//        model.addAttribute("user", sessionUser.getFullName());
//        model.addAttribute("results", allResult);
//        return "/admin/results";
//    }

    @GetMapping(value = "/result/{id}")
    public String getAllResult(Model model, @PathVariable Long id) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.setAuthUserSelectedGroup(null);
        authUserService.setAuthUserViewedTestId(id);
        List<OverAllResult> allResult = testService.getOverAllResultByTestId(id);
        model.addAttribute("id", id);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("allResults", allResult);
        return "/admin/result";
    }

    @GetMapping(value = "/addStudentFromExcel")
    public String getAddStudentFromExcelPage(Model model){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/addStudentFromExcel";
    }
    @PostMapping(value = "/addStudentFromExcel")
    public String addStudentFromExcel(@ModelAttribute StudentsCreateDto dto){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        try{
            authUserService.createStudents(dto);
        }catch(Exception e){
            return "redirect:/admin/addStudentFromExcel";
        }
        return "redirect:/admin/students";

    }

    @GetMapping(value = "/result/{testId}/{groupName}")
    public String getAllStudentsByGroup(Model model, @PathVariable String groupName, @PathVariable Long testId) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        authUserService.setAuthUserSelectedGroup(groupName);
        authUserService.setAuthUserViewedTestId(testId);
        List<OverAllResult> allResult = testService.getOverAllResultByTestIdAndGroupName(testId, groupName);
        model.addAttribute("id", testId);
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("allResults", allResult);
        return "/admin/result";
    }


    @GetMapping(value = "/editTest/{id}")
    public String getEditTestPage(@PathVariable Long id, Model model) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        Test testById = testService.getTest(id);
        model.addAttribute("test", testById);
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/editTest";
    }

    @PostMapping(value = "/editTest")
    public String updateTest(@ModelAttribute Test test) {
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        try {
            testService.updateTest(test);
        } catch (Exception e){
            return "redirect:/admin/editTest/" + test.getId();
        }
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/editStudent/{id}")
    public String getEditStudentPage(Model model, @PathVariable Long id){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        AuthUser authUser = authUserService.getAuthUserById(id);
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("groups", groupService.getAllGroups());
        model.addAttribute("student", authUser);
        return "/admin/editStudent";
    }
    @GetMapping(value = "/editTeacher/{id}")
    public String getEditTeacherPage(Model model, @PathVariable Long id){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        AuthUser authUser = authUserService.getAuthUserById(id);
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("student", authUser);
        return "/admin/editTeacher";
    }

    @PostMapping(value = "/editStudent")
    public String updateStudent(@ModelAttribute AuthUser user){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        try {
            authUserService.updateAuthUser(user);
        } catch (Exception e){
            return "redirect:/admin/editStudent/" + user.getId();
        }
        return "redirect:/home";
    }

    @PostMapping(value = "/editTeacher")
    public String updateTeacher(@ModelAttribute AuthUser user){
        if(!authUserService.checkToRole(Role.ADMIN)){
            return "redirect:/home";
        }
        try {
            authUserService.updateAuthUser(user);
        } catch (Exception e){
            return "redirect:/admin/editTeacher/" + user.getId();
        }
        return "redirect:/home";
    }
}
