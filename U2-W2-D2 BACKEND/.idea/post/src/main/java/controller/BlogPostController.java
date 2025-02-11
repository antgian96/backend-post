package com.example.blog.controller;

import com.example.blog.model.BlogPost;
import com.example.blog.repository.BlogPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/blogPosts")
@RequiredArgsConstructor
public class BlogPostController {

    private final BlogPostRepository blogPostRepository;

    @GetMapping
    public List<BlogPost> getAllBlogPosts() {
        return blogPostRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getBlogPostById(@PathVariable Long id) {
        return blogPostRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public BlogPost createBlogPost(@RequestBody BlogPost blogPost) {
        return blogPostRepository.save(blogPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> updateBlogPost(@PathVariable Long id, @RequestBody BlogPost updatedPost) {
        return blogPostRepository.findById(id)
                .map(post -> {
                    post.setCategoria(updatedPost.getCategoria());
                    post.setTitolo(updatedPost.getTitolo());
                    post.setCover(updatedPost.getCover());
                    post.setContenuto(updatedPost.getContenuto());
                    post.setTempoDiLettura(updatedPost.getTempoDiLettura());
                    return ResponseEntity.ok(blogPostRepository.save(post));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlogPost(@PathVariable Long id) {
        blogPostRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
