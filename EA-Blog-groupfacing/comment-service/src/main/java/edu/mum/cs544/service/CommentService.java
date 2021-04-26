package edu.mum.cs544.service;

import edu.mum.cs544.model.Comment;
import edu.mum.cs544.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional

public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllCommnets(Long postId){
         return commentRepository.findByPostId(postId);
    }

    public Comment getComment(Long postId, Long commentId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        for(Comment comment: comments){
            if(comment.getId()== commentId) return comment;
        }
        return null;
    }

    public void addComment(Comment comment){

            commentRepository.save(comment);
    }
    public void UpdateComment(Long postId, Long commentId, Comment comment){
        List<Comment> comments = commentRepository.findByPostId(postId);
        for(Comment com: comments){
            if(com.getId() == commentId) com.setText(comment.getText());
        }
    }

    public void deleteComment(Long postId, Long commentId){
        List<Comment> comments = commentRepository.findByPostId(postId);
        for(Comment com: comments){
            if(com.getId() == commentId) commentRepository.deleteById(commentId);
        }
    }

}
