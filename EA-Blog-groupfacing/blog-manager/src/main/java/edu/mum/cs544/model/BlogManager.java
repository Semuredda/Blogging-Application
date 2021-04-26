package edu.mum.cs544.model;

import java.util.ArrayList;
import java.util.List;

public class BlogManager {

    //private Long PostId;
    //private String text;
    //private String comment;
    private Post post;
    private Comment comments;

    public BlogManager() {
    }

    public BlogManager(Post post, Comment comments) {
        this.post = post;
        this.comments = comments;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comments;
    }

    private void setComment(Comment comments) {
        this.comments = comments;
    }

}
