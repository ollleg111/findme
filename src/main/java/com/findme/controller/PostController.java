package com.findme.controller;

import com.findme.models.Post;
import com.findme.models.PostFilter;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/post")
@Slf4j
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/{postId}")
    public String getPost(HttpSession session, Model model, @PathVariable String postId) {
            Utils.loginValidation(session);
            model.addAttribute("post", postService.findById(Utils.stringToLong(postId)));
            log.info("Get post with id: " + postId);
            return "posts/successPostPage";
    }

    @DeleteMapping(value = "/delete-post/{postId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String postId) {
            Utils.loginValidation(session);
            postService.deleteById(Utils.stringToLong(postId));
            log.info("Delete post with id: " + postId);
            return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
    }

    @PostMapping(value = "/add-post")
    public String save(HttpSession session, @ModelAttribute("post") @Validated Post post,
                                       BindingResult bindingResult) {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "posts/newPost";
            postService.save(post);
            log.info("Add post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return "posts/newPost";
    }

    @PatchMapping(value = "/update-post")
    public String update(HttpSession session, @ModelAttribute("post") @Validated Post post,
                                         BindingResult bindingResult) {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "posts/newPost";
            postService.update(post);
            log.info("Update post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return "posts/newPost";
    }

    @DeleteMapping(value = "/delete-post")
    public ResponseEntity<String> delete(HttpSession session, @RequestBody Post post) {
            Utils.loginValidation(session);
            postService.delete(post);
            log.info("Delete post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/getDataSortedPostsList")
    public void getDataSortedPostsList(HttpSession session, Model model){
            Utils.loginValidation(session);
            log.info("Get posts list from method getDataSortedPostsList(HttpSession session, Model model)");
            List<Post> postList = postService.getDataSortedPostsList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", postList);
    }

    @GetMapping(value = "/getListWithFilter")
    public ResponseEntity<List<Post>> getListWithFilter(HttpSession session, Model model, @ModelAttribute PostFilter postFilter){
            Utils.loginValidation(session);
            log.info("Get posts list from method getListWithFilter(HttpSession session, Model model, @ModelAttribute PostFilter postFilter)");
            List<Post> postList = postService.getFilteredList((User)session.getAttribute("user"), postFilter);
            model.addAttribute("posts/postList", postList);
            return new ResponseEntity<>(postList, HttpStatus.OK);
    }

    @GetMapping(value = "/feed")
    public ResponseEntity<List<Post>> feed(HttpSession session, Model model){
            Utils.loginValidation(session);
            log.info("Get posts list from method feed(HttpSession session, Model model)");
            List<Post> feedList = postService.getFeedList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", feedList);
            return new ResponseEntity<>(feedList, HttpStatus.OK);
    }

    @GetMapping(value = "/getPostsList")
    public ResponseEntity<List<Post>> getAll() {
            log.info("Get posts list from method getAll()");
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
    }
}
