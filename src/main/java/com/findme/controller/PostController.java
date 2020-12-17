package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
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
    public ResponseEntity<String> getPost(HttpSession session, Model model, @PathVariable String postId) {
        try {
            Utils.loginValidation(session);
            model.addAttribute("post", postService.findById(Utils.stringToLong(postId)));
            log.info("Get post with id: " + postId);
            return new ResponseEntity<>("posts/successPostPage", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-post/{postId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String postId) {
        try {
            Utils.loginValidation(session);
            postService.deleteById(Utils.stringToLong(postId));
            log.info("Delete post with id: " + postId);
            return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add-post")
    public ResponseEntity<String> save(HttpSession session, @RequestBody Post post) {
        try {
            Utils.loginValidation(session);
            postService.save(post);
            log.info("Add post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return new ResponseEntity<>("posts/newPost", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update-post")
    public ResponseEntity<String> update(HttpSession session, @RequestBody Post post) {
        try {
            Utils.loginValidation(session);
            postService.update(post);
            log.info("Update post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            //TODO
            return new ResponseEntity<>("posts/newPost", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-post")
    public ResponseEntity<String> delete(HttpSession session, @RequestBody Post post) {
        try {
            Utils.loginValidation(session);
            postService.delete(post);
            log.info("Delete post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return new ResponseEntity<>("Post was deleted", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getDataSortedPostsList")
    public ResponseEntity<List<Post>> getDataSortedPostsList(HttpSession session, Model model){
        try {
            Utils.loginValidation(session);
            log.info("Get posts list from method getDataSortedPostsList(HttpSession session, Model model)");
            List<Post> postList = postService.getDataSortedPostsList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", postList);
            return new ResponseEntity<>(postList, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getListWithFilter")
    public ResponseEntity<List<Post>> getListWithFilter(HttpSession session, Model model, @ModelAttribute PostFilter postFilter){
        try {
            Utils.loginValidation(session);
            log.info("Get posts list from method getListWithFilter(HttpSession session, Model model, @ModelAttribute PostFilter postFilter)");
            List<Post> postList = postService.getFilteredList((User)session.getAttribute("user"), postFilter);
            model.addAttribute("posts/postList", postList);
            return new ResponseEntity<>(postList, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/feed")
    public ResponseEntity<List<Post>> feed(HttpSession session, Model model){
        try {
            Utils.loginValidation(session);
            log.info("Get posts list from method feed(HttpSession session, Model model)");
            List<Post> feedList = postService.getFeedList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", feedList);
            return new ResponseEntity<>(feedList, HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getPostsList")
    public ResponseEntity<List<Post>> getAll() {
        try {
            log.info("Get posts list from method getAll()");
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
