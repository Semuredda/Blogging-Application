package edu.mum.cs544.Controller;

import edu.mum.cs544.model.Comment;
import edu.mum.cs544.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/mypost")
public class BlogManagerController {
    @Autowired
    private RestTemplate restTemplate;
    private final String commentUrl = "http://localhost:8080/post/";
    private final String postUrl = "http://localhost:8081/posts/";

    @ResponseBody @GetMapping("/{postId}") //returns all comments of a single blog post
     public List<Comment> getAllComments(@PathVariable Long postId){
        ResponseEntity<List<Comment>> response = restTemplate.exchange(commentUrl + postId + "/comments",
        HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {});

        return response.getBody();
    }
    @ResponseBody @PostMapping("/{postId}/comment") // adds a comment to a single blog post
    public void addComment(@PathVariable Long postId, @RequestBody Comment comment){
        System.out.println(commentUrl+postId+"/add/comment");
        URI uri = restTemplate.postForLocation(commentUrl+postId+"/add/comment",comment, Comment.class);

    }
    @ResponseBody @PostMapping("/addpost") //adds a new post
    public void addPost(@RequestBody Post post){
        System.out.println(postUrl);
        URI uri = restTemplate.postForLocation(postUrl+"add",post, Post.class);

    }
    @ResponseBody @GetMapping("/{postId}/comment/{commentId}")
    public Comment getComment(@PathVariable Long postId, @PathVariable Long commentId){
        Comment comment = restTemplate.getForObject(commentUrl + postId + "/comment/"+commentId, Comment.class);
        return comment;
    }
    //update a post

    @ResponseBody @PutMapping("/updatepost/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody Post post){
        System.out.println(postId + " post id");
        restTemplate.put(postUrl + postId, post, postId);
    }
    @ResponseBody @PutMapping("/{postId}/updatecomment/{commentId}")
    public void updateComment(@PathVariable Long postId, @PathVariable Long commentId, @RequestBody Comment comment){
        System.out.println(commentId +" and post id: " + postId);
        restTemplate.put(commentUrl + postId + "/update/comment/" + commentId,comment, postId, commentId);
    }


    //Deleting comment and post
    @ResponseBody @DeleteMapping("/deletepost/{postId}")
    public void deletePost(@PathVariable Long postId){
        ResponseEntity<List<Comment>> response = restTemplate.exchange(commentUrl+ postId + "/comments",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {});

        List<Comment> comments = response.getBody();
        for(Comment comment: comments){
            restTemplate.delete(commentUrl+postId+"/delete/comment/"+comment.getId(), comment.getPostId(), comment.getId());
        }
        restTemplate.delete(postUrl + postId, postId);
    }
    @ResponseBody @DeleteMapping("/{postId}/deletecomment/{commentId}")
    public void deleteComment(@PathVariable Long postId, @PathVariable Long commentId){

          restTemplate.delete(commentUrl + postId +"/delete/comment/"+commentId, postId, commentId);
    }
    @ResponseBody @GetMapping("/")
    public List<Post> allPosts(){
        ResponseEntity<List<Post>> response = restTemplate.exchange(postUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});

        return response.getBody();
    }
    //This is why Microservices are restISH
    //
    //this is for the controller
    //
    // ///////////////////////============

    @GetMapping("/view")
    public String getAllPosts(Model model){
        ResponseEntity<List<Post>> response = restTemplate.exchange(postUrl,
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Post>>() {});

        List<Comment> commentList = new ArrayList<>();
        for(Post post: response.getBody()){

            ResponseEntity<List<Comment>> commentRes = restTemplate.exchange(commentUrl + post.getId() + "/comments",
                            HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {});
            for(Comment comment: commentRes.getBody()){
                commentList.add(comment);
            }

        }

        model.addAttribute("postList", response.getBody());
        model.addAttribute("commentList", commentList);

        return "index.html";
    }
    @RequestMapping("/new")
    public String createNewPost(Model model){
        model.addAttribute("post", new Post());
        return  "new_post";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute("post") Post post){
        URI uri = restTemplate.postForLocation(postUrl+"add",post, Post.class);
        return "redirect:/mypost/view";
    }
    @GetMapping("/edit/{postId}")
    public ModelAndView editPost(@PathVariable Long postId){

        ModelAndView mav = new ModelAndView("edit_post");
        Post post = restTemplate.getForObject(postUrl + postId , Post.class);

        mav.addObject("post", post);
        return mav;
    }
    @GetMapping("/remove/{postId}")
    public String removePost(@PathVariable Long postId){

        ResponseEntity<List<Comment>> response = restTemplate.exchange(commentUrl+ postId + "/comments",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Comment>>() {});

        List<Comment> comments = response.getBody();
        for(Comment comment: comments){
            restTemplate.delete(commentUrl+postId+"/delete/comment/"+comment.getId(), comment.getPostId(), comment.getId());
        }
        restTemplate.delete(postUrl + postId, postId);
        return "redirect:/mypost/view";
    }

    @RequestMapping("/{postId}/addnewcomment")
    public String createNewComment(Model model, @PathVariable Long postId){
        Comment comment = new Comment();
        comment.setPostId(postId);
        model.addAttribute("comment", comment);
        return  "new_comment";
    }

    @PostMapping("/savecomment")
    public String saveComment(@ModelAttribute("comment") Comment comment){
        URI uri = restTemplate.postForLocation(commentUrl+comment.getPostId()+"/add/comment",comment, Comment.class);
        return "redirect:/mypost/view";
    }

    @GetMapping("/{postId}/editcomment/{commentId}")

    public ModelAndView editComment(@PathVariable Long postId, @PathVariable Long commentId){

        ModelAndView mav = new ModelAndView("edit_comment");
        Comment comment = restTemplate.getForObject(commentUrl + postId + "/comment/"+commentId, Comment.class);

        mav.addObject("comment", comment);
        return mav;
    }

    @GetMapping("/{postId}/removecomment/{commentId}")
    public String removeComment(@PathVariable Long postId, @PathVariable Long commentId){


        restTemplate.delete(commentUrl+postId+"/delete/comment/"+commentId, postId, commentId);
        return "redirect:/mypost/view";
    }

}
