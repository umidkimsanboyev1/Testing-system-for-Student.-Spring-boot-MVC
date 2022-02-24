package uz.master.demotest.controller.Organization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uz.master.demotest.dto.Organization.OrganizationCreateDto;

@Controller
@RequestMapping(value = "/organization/**")
public class OrganizationController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String createPage(Model model){
        model.addAttribute("Organization", new OrganizationCreateDto());
        return "organization/register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String create(@ModelAttribute OrganizationCreateDto dto){

        return "index";
    }



}
