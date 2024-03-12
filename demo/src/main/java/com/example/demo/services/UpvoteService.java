package com.example.demo.services;

import com.example.demo.domain.User;
import com.example.demo.repositories.UpvoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpvoteService {

    @Autowired
    UpvoteRepository upvoteRepository;

    public boolean checkUpvote(Long page_id, User user)
    {
        if(upvoteRepository.findByUser_idAndPage_id(user.getId(),page_id)!=null)
            return true;
        return false;
    }

    public void deleteUpvotesOnPage(Long page_id){
        upvoteRepository.deleteAllByPage_id(page_id);
    }
}
