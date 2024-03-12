package com.example.demo.controllers;
import com.example.demo.domain.Comment;
import com.example.demo.domain.CustomUserDetails;
import com.example.demo.domain.Page;
import com.example.demo.services.CommentService;
import com.example.demo.services.PageService;
import com.example.demo.services.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class PageController {

    @Autowired
    PageService pageService;

    @Autowired
    CommentService commentService;

    @Autowired
    UpvoteService upvoteService;

//    @GetMapping("/pages")
//    String pages(Model model,
//                 @RequestParam(value="sort", defaultValue = "recent") String sort) {
//        model.addAttribute("data", pageService.getPages(sort));
//        return "pages";
//    }

    @GetMapping("/pages")
    String pages(Model model,
                @RequestParam(value="sort", defaultValue = "recent") String sort) {
        return pagination(model,1, sort);
    }

    @GetMapping("/pages/paginated/{pageNum}")
    String pagination(Model model, @PathVariable(value="pageNum") int currentPage,
                      @RequestParam(value="sort", defaultValue = "recent") String sort){
        org.springframework.data.domain.Page<Page> page = pageService.getPaginatedPages(sort, currentPage);
        int totalPages = page.getTotalPages();
        long totalItems = page.getTotalElements();
        model.addAttribute("sort", sort);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("data", page.getContent());
        return "pages";
    }

    @GetMapping("/pages/{id}")
    String page(Model model, @PathVariable(value="id") Long id,
                @AuthenticationPrincipal CustomUserDetails userDetails,
                Comment comment){
        model.addAttribute("page", pageService.getPage(id));
        model.addAttribute("upvote", upvoteService.checkUpvote(id, userDetails.getUser()));
        return "page";

    }
    @PostMapping("/pages/{id}")
    String pagepost(Model model,
                    @PathVariable(value="id") Long id,
                    @Valid Comment comment,
                    BindingResult result,
                    @AuthenticationPrincipal CustomUserDetails user_details){
        if (result.hasErrors()){
            model.addAttribute("page", pageService.getPage(id));
            return "page";
        }
        commentService.saveComment(comment, pageService.getPage(id), user_details.getUser());

        return "redirect:/pages/{id}";

    }

    @GetMapping("/pages/{id}/edit")
    String pageedit(Model model, @PathVariable(value="id") Long id, Page page){
        model.addAttribute("page", pageService.getPage(id));
        return "edit_page";
    }

    @PostMapping("/pages/{id}/edit")
    String pageeditpost(Model model,
                        @PathVariable(value="id") Long id,
                        @Valid Page page,
                        BindingResult result){
        if (result.hasErrors()){
            return "edit_page";
        }
        pageService.updatePage(page, id);
        return "redirect:/pages/{id}";

    }

    @GetMapping("/pages/create")
    String testo(Page page){
        return "create_page";
    }

    @PostMapping("/pages/create")
    String testo2(@Valid Page page,
                  BindingResult result,
                  @AuthenticationPrincipal CustomUserDetails userDetails){
        if (result.hasErrors()){
            return "create_page";
        }
        pageService.savePage(page, userDetails.getUser());
        return "redirect:/pages";
    }

}
