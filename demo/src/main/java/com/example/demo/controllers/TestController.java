package com.example.demo.controllers;


import com.example.demo.domain.CustomUserDetails;
import com.example.demo.services.CommentService;
import com.example.demo.services.PageService;
import com.example.demo.services.UpvoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class TestController {

    @Autowired
    PageService pageService;

    @Autowired
    CommentService commentService;

    @Autowired
    UpvoteService upvoteService;

    @GetMapping("/")
    String mainpage() { return "index";}

    @GetMapping("/deleteComment")
    String deleteComment(@RequestParam String comment_id,
                  @RequestParam String page_id){

        commentService.deleteComment(Long.parseLong(comment_id));
        return "redirect:/pages/" + page_id;
    }

    @GetMapping("/deletePage")
    String deletePage(@RequestParam String page_id){
        upvoteService.deleteUpvotesOnPage(Long.parseLong(page_id));
        commentService.deleteCommentsOnPage(Long.parseLong(page_id));
        pageService.deletePage(Long.parseLong(page_id));
        return "redirect:/pages";
    }

    @GetMapping("/upvotePage")
    String likePage(@RequestParam String page_id,
                    @AuthenticationPrincipal CustomUserDetails user_details)
    {
        pageService.upvotePage(Long.parseLong(page_id), user_details.getUser());
        return "redirect:/pages/" + page_id;
    }


}
