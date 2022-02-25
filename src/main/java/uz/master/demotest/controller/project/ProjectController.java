package uz.master.demotest.controller.project;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.project.ProjectCreateDto;
import uz.master.demotest.dto.project.ProjectUpdateDto;
import uz.master.demotest.services.project.ProjectService;
import uz.master.demotest.services.task.TaskService;

import javax.validation.Valid;

/**
 * @author Bekpulatov Shoxruh, Thu 10:35 AM. 2/24/2022
 */

@Controller
@RequestMapping("/project/")
public class ProjectController {

    private final ProjectService projectService;
    private final TaskService taskService;

    public ProjectController(ProjectService projectService, TaskService taskService) {
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @RequestMapping("all")
    public String task(Model model) {
//        model.addAttribute("tasks", projectService.getAll());
        return "project/list";
    }

    @RequestMapping("{id}")
    public String getProjectPage(Model model, @PathVariable String id) {
        model.addAttribute("project", projectService.get(Long.parseLong(id)));
        return "project/project";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createPage(Model model) {
        model.addAttribute("dto", new ProjectCreateDto());
        return "project/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dto") ProjectCreateDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "project/create";
        }
        projectService.create(dto);
        return "redirect:/project/all";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {
        model.addAttribute("dto", projectService.get(id));
        return "project/delete";

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        projectService.delete(id);
        return "redirect:/project/all";
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
        return "redirect:/project/all";
    }
}
