package uz.master.demotest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.master.demotest.dto.task.TaskCreateDto;
import uz.master.demotest.dto.task.TaskUpdateDto;
import uz.master.demotest.services.task.TaskService;

import javax.validation.Valid;

/**
 * @author Bekpulatov Shoxruh, Thu 10:35 AM. 2/24/2022
 */

@Controller
@RequestMapping("/task/")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @RequestMapping("")
    public String task(Model model) {
        model.addAttribute("tasks", taskService.getAll());
        return "task/all";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createPage(Model model) {
        model.addAttribute("dto", new TaskCreateDto());
        return "task/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dto") TaskCreateDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "task/create";
        }
        taskService.create(dto);
        return "redirect:/task/all";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {
        model.addAttribute("dto", taskService.get(id));
        return "task/delete";

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        taskService.delete(id);
        return "redirect:/task/all";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updatePage(@PathVariable(name = "id") Long id, Model model) {
        TaskUpdateDto dto = taskService.getUpdateDto(id);
        model.addAttribute("dto", dto);
        return "task/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(name = "id") Long id, @ModelAttribute TaskUpdateDto dto) {
        dto.setId(id);
        taskService.update(dto);
        return "redirect:/task/all";
    }


}
