package mum.edu.cs544.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mum.edu.cs544.model.Post;
import mum.edu.cs544.repository.PostRepository;

@Service
@Transactional
public class PostService {
	@Autowired
	private PostRepository postRepository;

	public List<Post> getAll(){

		return postRepository.findAll();
	}
	public Post get(Long id) {
		return postRepository.findById(id).get();
	}

	public void add(Post post) {
		postRepository.save(post);
	}

	public void update(Long id, Post post) {
		Post p = postRepository.findById(id).get();
		p.setText(post.getText());
		postRepository.save(p);
	}

	public void delete(Long id) {
		postRepository.deleteById(id);
	}
}
