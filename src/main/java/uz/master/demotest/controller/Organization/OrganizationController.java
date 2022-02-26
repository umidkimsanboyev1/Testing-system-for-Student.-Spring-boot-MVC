package uz.master.demotest.controller.Organization;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.master.demotest.dto.organization.OrganizationCreateDto;
import uz.master.demotest.dto.organization.OrganizationUpdateDto;
import uz.master.demotest.services.organization.OrganizationService;

@Controller
@RequestMapping(value = "/organization/")
public class OrganizationController {

    private final OrganizationService service;

    public OrganizationController(OrganizationService service) {
        this.service = service;
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createPage(Model model){
        //model.addAttribute("Organization", new OrganizationCreateDto());
        return "organization/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@ModelAttribute OrganizationCreateDto dto){
        service.create(dto);
        return "/home";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateOrganization(Model model, @PathVariable Long id){
        model.addAttribute("organization", service.getOrganization(id));
        return "organization/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable Long id, @ModelAttribute OrganizationUpdateDto dto){
        service.update(dto, id);
        return "/home";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String delete(Model model, @PathVariable Long id){
        model.addAttribute("organization", service.getOrganization(id));
        return "organization/delete";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id){
        service.delete(id);
        return "/home";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String delete(Model model){
        model.addAttribute("organizations", service.getAll());
        return "organization/list";
    }

}
