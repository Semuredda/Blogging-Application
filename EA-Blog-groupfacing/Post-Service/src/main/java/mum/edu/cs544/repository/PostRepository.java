package mum.edu.cs544.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mum.edu.cs544.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

}
