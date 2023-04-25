package uz.master.demotest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.util.List;

@Controller
@RequestMapping(value = "/admin/*")
public class AdminController {
    private final AuthUserService authUserService;
    private final TestService testService;

    private final SessionUser sessionUser;

    public AdminController(AuthUserService authUserService, TestService testService, SessionUser sessionUser) {
        this.authUserService = authUserService;
        this.testService = testService;
        this.sessionUser = sessionUser;
    }

    @GetMapping(value = "allTests")
    public String getAllTests(Model model) {
        List<TestDto> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        model.addAttribute("user", sessionUser.getFullName());
        return "/admin/listTests";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id) {
        testService.doAction(id);
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/deleteTest/{id}")
    public String delete(@PathVariable Long id) {
        testService.delete(id);
        return "redirect:/admin/allTests";
    }

    @GetMapping(value = "/checkDeleteTest/{id}")
    public String checkDeleteTest(Model model, @PathVariable Long id){
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

}
