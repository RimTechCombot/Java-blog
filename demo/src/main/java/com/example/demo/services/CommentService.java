package com.example.demo.services;

import com.example.demo.domain.Comment;
import com.example.demo.domain.Page;
import com.example.demo.domain.User;
import com.example.demo.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    public List<Comment> getComments(Long id)
    {
        return commentRepository.findByPage_id(id);
    }
    public void saveComment(Comment comment, Page page, User user)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date today = new Date();
        comment.setPage(page);
        comment.setUser(user);
        comment.setDate(formatter.format(today));
        commentRepository.save(comment);
    }

    public void deleteComment(Long id){
        commentRepository.deleteById(id);
    }

    public void deleteCommentsOnPage(Long page_id){
        commentRepository.deleteAllByPage_id(page_id);
    }
}
