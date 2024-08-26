package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findAllByUser(User user);

}
