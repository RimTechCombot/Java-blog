package com.example.demo.services;



import com.example.demo.domain.Page;
import com.example.demo.domain.Upvote;
import com.example.demo.domain.User;
import com.example.demo.repositories.CommentRepository;
import com.example.demo.repositories.PageRepository;
import com.example.demo.repositories.UpvoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class PageService {

    @Autowired
    PageRepository pageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    UpvoteRepository upvoteRepository;

    public void savePage(Page page, User user)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date today = new Date();
        page.setUser(user);
        page.setUpvotes(0);
        page.setDate(formatter.format(today));
        pageRepository.save(page);
    }

    public void updatePage(Page editedPage, Long page_id)
    {
        Page page = pageRepository.findById(page_id).get();
        page.setTitle(editedPage.getTitle());
        page.setBody(editedPage.getBody());
        pageRepository.save(page);
    }

    public void upvotePage(Long page_id, User user)
    {
        Page page = pageRepository.findById(page_id).get();
        Upvote upvote = upvoteRepository.findByUser_idAndPage_id(user.getId(), page_id);
        if(upvote!=null)
        {
            System.out.println("found the like");
            upvoteRepository.delete(upvote);
        }
        else
        {
            upvote = new Upvote(user, pageRepository.findById(page_id).get());
            upvoteRepository.save(upvote);
        }
        page.setUpvotes(upvoteRepository.countByPage_id(page_id));
        pageRepository.save(page);

    }

    public Page getPage(Long id)
    {
        return pageRepository.findById(id).get();
    }

    public org.springframework.data.domain.Page<Page> getPaginatedPages(String sort, int pageNumber){
        if (sort.equals("recent")==true)
        {
            Pageable pageable = PageRequest.of(pageNumber-1, 5, Sort.by("date").descending());
            return pageRepository.findAll(pageable);
        }
        else if(sort.equals("popular")==true)
        {
            Pageable pageable = PageRequest.of(pageNumber-1, 5, Sort.by("upvotes").descending());
            return pageRepository.findAll(pageable);
        }
        else if(sort.equals("alphabetically")==true)
        {
            Sort sortOrderIgnoreCase = Sort.by(new Sort.Order(Sort.Direction.ASC, "title").ignoreCase());
            Pageable pageable = PageRequest.of(pageNumber-1, 5, sortOrderIgnoreCase);
            return pageRepository.findAll(pageable);
        }
        else
        {
            Pageable pageable = PageRequest.of(pageNumber-1, 5);
            return pageRepository.findAll(pageable);
        }


    }
//    public org.springframework.data.domain.Page<Page> getPages(String sort, int pageNumber)
//    {
//
//        Pageable pageable = PageRequest.of(pageNumber-1,5, sort)
//        System.out.println(sort);
//        if (sort.equals("recent")==true)
//            return pageRepository.findAll(Sort.by(Sort.Order.desc("date")));
//        else if(sort.equals("popular")==true)
//            return pageRepository.findAll(Sort.by(Sort.Order.desc("upvotes")));
//        else if(sort.equals("alphabetically")==true)
//            return pageRepository.findAll(Sort.by(Sort.Order.asc("title").ignoreCase()));
//        else
//            return pageRepository.findAll();
//    }

    public List<Page> getPages(String sort)
    {
        System.out.println(sort);
        if (sort.equals("recent")==true)
            return pageRepository.findAll(Sort.by(Sort.Order.desc("date")));
        else if(sort.equals("popular")==true)
            return pageRepository.findAll(Sort.by(Sort.Order.desc("upvotes")));
        else if(sort.equals("alphabetically")==true)
            return pageRepository.findAll(Sort.by(Sort.Order.asc("title").ignoreCase()));
        else
            return pageRepository.findAll();
    }

    public void deletePage(Long id)
    {
        pageRepository.deleteById(id);
    }
}
