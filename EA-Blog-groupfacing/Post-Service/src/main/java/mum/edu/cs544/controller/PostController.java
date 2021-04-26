package mum.edu.cs544.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import mum.edu.cs544.model.Post;
import mum.edu.cs544.service.PostService;

@RestController
public class PostController {
	@Autowired
	private PostService postService;

	@GetMapping("/posts/{id}")
	public Post getPost(@PathVariable Long id){
		//Post p = new Post(1L, "this is my first post");
		return postService.get(id);
	}
	@GetMapping("/posts")
	public List<Post> getAllPosts(){

		return postService.getAll();	
	}

	@PostMapping("/posts/add")
	public void addPost(@RequestBody Post post) {
		postService.add(post);
	}

	@PutMapping("/posts/{id}")
	public void updatePost(@RequestBody Post post, @PathVariable Long id) {
	    postService.update(id,post);
	}
	@DeleteMapping("/posts/{id}")
	public void deletePost(@PathVariable Long id) {
		postService.delete(id);
	}

}
