package uz.master.demotest.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.dto.test.TestDto;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.services.auth.AuthUserService;

import java.util.List;

@Controller
@RequestMapping(value = "admin/*")
public class AdminController {
    private final AuthUserService authUserService;
    private final TestService testService;
    public AdminController(AuthUserService authUserService, TestService testService) {
        this.authUserService = authUserService;
        this.testService = testService;
    }

    @GetMapping(value = "allTests")
    public String getAllTests(Model model){
        List<TestDto> tests = testService.getAllTests();
        model.addAttribute("tests", tests);
        model.addAttribute("user", authUserService.getUserName());
        return "/admin/listTests";
    }

    @GetMapping(value = "/doAction/{id}")
    public String doAction(@PathVariable Long id){
        testService.doAction(id);
        return "redirect:/admin/allTests";
    }
}
