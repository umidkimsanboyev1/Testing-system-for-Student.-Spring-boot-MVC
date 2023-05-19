package uz.master.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.dto.test.OverAllResultDTO;
import uz.master.demotest.entity.Groups;
import uz.master.demotest.services.GroupService;
import uz.master.demotest.services.auth.AuthUserService;
import uz.master.demotest.services.test.TestService;
import uz.master.demotest.utils.SessionUser;

import java.util.List;

@Controller
@RequestMapping(value = "/oquvBolim/")
public class DekanatController {

    private final SessionUser sessionUser;
    private final AuthUserService authUserService;
    private final TestService testService;
    private final GroupService groupService;

    public DekanatController(SessionUser sessionUser, AuthUserService authUserService, TestService testService, GroupService groupService) {
        this.sessionUser = sessionUser;
        this.authUserService = authUserService;
        this.testService = testService;
        this.groupService = groupService;
    }


    @GetMapping(value = "/results")
    public String getResult(Model model) {
        model.addAttribute("user", sessionUser.getFullName());
        try {
            List<OverAllResultDTO> results = testService.getAllResults();
            List<Groups> allGroups = groupService.getAllGroups();
            model.addAttribute("groups", allGroups);
            model.addAttribute("results", results);
        } catch (Exception ex) {
            model.addAttribute("error", "Ma'lumotlar topilmadi!");
            return "/error/error";
        }
        return "/oquvBolim/results";
    }

    @GetMapping(value = "/results/{groupName}")
    public String getAllResultsByGroup(Model model, @PathVariable String groupName) {
        Long id = authUserService.setAuthUserSelectedGroup(groupName);
        List<OverAllResultDTO> allResult = testService.getAllResultsByGroup(id, groupName);
        List<Groups> allGroups = groupService.getAllGroups();
        model.addAttribute("groups", allGroups);
        model.addAttribute("user", sessionUser.getFullName());
        model.addAttribute("results", allResult);
        return "/oquvBolim/results";
    }
}
