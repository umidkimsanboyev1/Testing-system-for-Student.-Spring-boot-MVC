package uz.master.demotest.controller.project;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.configs.security.UserDetails;
import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.services.organization.OrganizationService;
import uz.master.demotest.services.project.ProjectService;
import uz.master.demotest.utils.SessionUser;

import javax.validation.Valid;

@Controller
@RequestMapping("/project/")
public class ProjectController {

    private final SessionUser user;
    private final ProjectService projectService;
   private final OrganizationService organizationService;
    public ProjectController(SessionUser user, ProjectService projectService, OrganizationService organizationService) {
        this.user = user;
        this.projectService = projectService;
        this.organizationService = organizationService;
    }

    @RequestMapping("all/{org_id}")
    public String task(Model model,@PathVariable(name = "org_id") Long id) {
        model.addAttribute("projects", projectService.getAll(id));
        model.addAttribute("organization", organizationService.get(id));
        return "project/list";
    }

    @RequestMapping("{id}")
    public String getProjectPage(Model model, @PathVariable Long id) {
        model.addAttribute("project", projectService.get(id));
        model.addAttribute("projectMembers", projectService.getMembers(id));
        model.addAttribute("orgMembers", projectService.getMembersFromOrganization(id));
        Long orgId = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getOrganization();
        model.addAttribute("organization", organizationService.getOrganization(orgId));
        return "project/project";
    }



    @GetMapping("addMember/{projectId}/{memberId}")
    public String addMember(@PathVariable(name = "projectId") Long projectId, @PathVariable(name = "memberId") Long memberId) {
        projectService.addMember(projectId, memberId);
        return "redirect:/project/" + projectId;
    }


    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createPage(Model model) {
        model.addAttribute("dto", new ProjectCreateDto());
        return "project/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute ProjectCreateDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "project/create";
        }
        projectService.create(dto);
        return "redirect:/project/all/"+user.getOrgId();
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        projectService.delete(id);
        return "redirect:/project/all/"+user.getOrgId();
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updatePage(@PathVariable(name = "id") Long id, Model model) {
        ProjectUpdateDto dto = projectService.getUpdateDto(id);
        model.addAttribute("dto", dto);
        return "project/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(name = "id") Long id, @ModelAttribute ProjectUpdateDto dto) {
        dto.setId(id);
        projectService.update(dto);
        return "redirect:/project/all/"+user.getOrgId();
    }

    @RequestMapping("update/{column_id}/{task_id}")
    public void updateColumn(@PathVariable(name = "column_id") Long columnId, @PathVariable(name = "task_id") Long taskId){
        projectService.updateTaskColumn(columnId, taskId);
    }
}
