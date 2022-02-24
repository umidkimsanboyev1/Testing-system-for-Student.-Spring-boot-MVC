package uz.master.demotest.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project/")
public class ProjectController {

    @RequestMapping("list")
    public String list(){
        return "pages/project";
    }
}
