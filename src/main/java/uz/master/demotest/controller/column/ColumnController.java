package uz.master.demotest.controller.column;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.master.demotest.dto.column.ColumnCreateDto;
import uz.master.demotest.dto.column.ColumnUpdateDto;
import uz.master.demotest.services.column.ColumnService;
import uz.master.demotest.services.project.ProjectService;
import uz.master.demotest.services.task.TaskService;

@Controller
@RequestMapping("/project/column/")
public class ColumnController {
    private final ColumnService service;
    private final ProjectService projectService;


    public ColumnController(
            ColumnService service,
            TaskService taskService, ProjectService projectService) {
        this.service = service;

        this.projectService = projectService;
    }

    @RequestMapping("list/{id}")
    public String list(Model model, @PathVariable(name = "id") Long id) {
        model.addAttribute("project", projectService.get(id));
        model.addAttribute("columns", service.getAll(id));
        return "project/project";
    }

    @RequestMapping(value = "create/", method = RequestMethod.POST)
    public String create(@ModelAttribute ColumnCreateDto dto) {
        service.create(dto);
        return "redirect:/project/project";
    }

    @RequestMapping(value = "update/", method = RequestMethod.POST)
    public String create(@ModelAttribute ColumnUpdateDto dto) {
        service.update(dto);
        return "redirect:/project/project";
    }

    @RequestMapping(value = "delete/", method = RequestMethod.DELETE)
    public String delete(@RequestParam Long id) {
        service.delete(id);
        return "redirect:/project/project";
    }
}
