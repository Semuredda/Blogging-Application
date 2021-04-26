package edu.mum.cs544.controller;

import edu.mum.cs544.model.Comment;

import edu.mum.cs544.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post/{postId}")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public List<Comment> getAllComment(@PathVariable Long postId){
       // Comments comments = new Comments();
        return commentService.getAllCommnets(postId);
        //return comments;
    }
    @GetMapping("/comment/{commentId}")
    public Comment getComment(@PathVariable Long postId, @PathVariable Long commentId){
        return commentService.getComment(postId, commentId);
    }
    @PostMapping("/add/comment")
    public void addComment(@RequestBody Comment comment){
        System.out.println("I am here");
        commentService.addComment(comment);
    }

    @PutMapping("/update/comment/{commentId}")
    public void updateComment(@RequestBody Comment comment,@PathVariable Long postId, @PathVariable Long commentId){ //, @PathVariable Long postId, @PathVariable Long commentId
        commentService.UpdateComment(postId, commentId, comment);
    }

    @DeleteMapping("/delete/comment/{commentId}")
    public void deleteComment(@PathVariable  Long postId, @PathVariable Long commentId){
        commentService.deleteComment(postId, commentId);
    }
}
