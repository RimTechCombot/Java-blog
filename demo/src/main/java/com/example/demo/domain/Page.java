package com.example.demo.domain;




import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// https://www.youtube.com/watch?v=UpK2jcJle6I


@Entity
public class Page {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    @Size(min=1, max=254)
    private String title;
    @NotEmpty
    @Size(min=1, max=9999)
    private String body;
    private Integer upvotes;
    private String date;
    @OneToMany(mappedBy = "page",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Comment> comments = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "page",fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<Upvote> upvotes_list = new ArrayList<>();

    public Page() {
    }

    public Page(String title, String body, Integer upvotes, User user, String date) {
        this.title = title;
        this.body = body;
        this.upvotes = upvotes;
        this.user = user;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(Integer upvotes) {
        this.upvotes = upvotes;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Upvote> getUpvotes_list() {
        return upvotes_list;
    }

    public void setUpvotes_list(List<Upvote> upvotes_list) {
        this.upvotes_list = upvotes_list;
    }
}
