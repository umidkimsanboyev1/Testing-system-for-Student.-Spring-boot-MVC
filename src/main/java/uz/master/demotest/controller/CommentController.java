package uz.master.demotest.controller;

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

import javax.validation.Valid;

/**
 * @author Bekpulatov Shoxruh, Thu 12:04 PM. 2/24/2022
 */
@Controller
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("")
    public String comment(Model model) {
        model.addAttribute("comments", commentService.getAll());
        return "comment/all";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createPage(Model model) {
        model.addAttribute("dto", new CommentCreateDto());
        return "comment/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String create(@Valid @ModelAttribute("dto") CommentCreateDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "comment/create";
        }
        commentService.create(dto);
        return "redirect:/comment/all";
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
    public String deletePage(@PathVariable Long id, Model model) {
        model.addAttribute("dto", commentService.get(id));
        return "comment/delete";

    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.POST)
    public String delete(@PathVariable Long id) {
        commentService.delete(id);
        return "redirect:/comment/all";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updatePage(@PathVariable(name = "id") Long id, Model model) {
        CommentUpdateDto dto = commentService.getUpdateDto(id);
        model.addAttribute("dto", dto);
        return "comment/update";
    }

    @RequestMapping(value = "update/{id}", method = RequestMethod.POST)
    public String update(@PathVariable(name = "id") Long id, @ModelAttribute CommentUpdateDto dto) {
        dto.setId(id);
        commentService.update(dto);
        return "redirect:/comment/all";
    }

}
