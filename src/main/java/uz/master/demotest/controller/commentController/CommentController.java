package uz.master.demotest.controller.commentController;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.master.demotest.dto.comment.CommentCreateDto;
import uz.master.demotest.dto.comment.CommentUpdateDto;
import uz.master.demotest.services.comment.CommentService;

import javax.validation.Path;
import javax.validation.Valid;

/**
 * @author Bekpulatov Shoxruh, Thu 12:04 PM. 2/24/2022
 */
@Controller
@RequestMapping("/comment/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping(value = "create/{id}", method = RequestMethod.POST)
    public String comment(@ModelAttribute CommentCreateDto dto, @PathVariable(name = "id") Long id) {
        dto.setTaskId(id);
        commentService.create(dto);
        return "redirect:/task/" + id;
    }


}
