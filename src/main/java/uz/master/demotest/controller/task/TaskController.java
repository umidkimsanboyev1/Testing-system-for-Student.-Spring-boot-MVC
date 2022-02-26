package uz.master.demotest.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.master.demotest.dto.comment.CommentDto;
import uz.master.demotest.dto.task.TaskCreateDto;
import uz.master.demotest.dto.task.TaskDto;
import uz.master.demotest.entity.action.Action;
import uz.master.demotest.entity.auth.AuthUser;
import uz.master.demotest.services.comment.CommentService;
import uz.master.demotest.services.task.TaskService;

import java.util.List;

/**
 * @author Bekpulatov Shoxruh, Thu 10:35 AM. 2/24/2022
 */

@Controller
@RequestMapping("/task/")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

    public TaskController(TaskService taskService, CommentService commentService) {
        this.taskService = taskService;
        this.commentService = commentService;
    }

    @GetMapping("{id}")
    public String taskPage(Model model, @PathVariable(name = "id") Long id) {
        List<Action> actions = taskService.getActions(id);
        model.addAttribute("actions", actions);

        TaskDto dto = taskService.get(id);
        model.addAttribute("dto", dto);

        List<CommentDto> comments = commentService.getAll(id);
        model.addAttribute("comments", comments);

        List<AuthUser> members = taskService.getMembers(id);
        model.addAttribute("members", members);

        return "task/task";
    }

    @GetMapping("/priority/{id}/{code}")
    public String changePriority(@PathVariable(name = "id") Long id, @PathVariable(name = "code") String code) {
        taskService.updatePriority(id, code);
        return "redirect:/task/" + id;

    }

    @GetMapping("/level/{id}/{code}")
    public String changeLevel(@PathVariable(name = "id") Long id, @PathVariable(name = "code") String code) {
        taskService.updateLevel(id, code);
        return "redirect:/task/" + id;
    }

    @GetMapping("/join/{id}")
    public String joinTask(@PathVariable(name = "id") Long id) {
        taskService.joinTask(id);
        return "redirect:/task/" + id;
    }

    @GetMapping("/create/{column_id}")
    public String createTaskPage(Model model, @PathVariable(name = "column_id") Long id) {
        model.addAttribute("id", id);
        return "task/create";
    }

    @PostMapping("/create/{id}")
    public String createTask(@PathVariable(name = "id") Long id, TaskCreateDto dto) {
        dto.setColumnId(id);
        taskService.create(dto);
        return "redirect:/project/all";
    }
}
