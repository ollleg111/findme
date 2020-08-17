package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Post;
import com.findme.service.PostService;
import com.findme.util.Utils;
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
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/{postId}")
    public String getPost(HttpSession session, Model model, @PathVariable String postId) {
        try {
            Utils.loginValidation(session);
            model.addAttribute("post", postService.findById(Utils.stringToLong(postId)));
            return "profile";
        } catch (BadRequestException e) {
            return "BadRequestException " + e.getMessage();
        } catch (NotFoundException e) {
            return "Error 404 " + e.getMessage();
        } catch (InternalServerError e) {
            return "System Error " + e.getMessage();
        }
    }

    @DeleteMapping(value = "/delete-post/{postId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String postId) {
        try {
            Utils.loginValidation(session);
            postService.deleteById(Utils.stringToLong(postId));
            return new ResponseEntity<>(" Post was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //------------------------------------------------------------------------------------------
    @GetMapping(value = "/findById-post")
    public ResponseEntity<String> findById(HttpSession session, @RequestParam(value = "id") String postId) {
        try {
            Utils.loginValidation(session);
            postService.findById(Utils.stringToLong(postId));
            return new ResponseEntity<>(" ok ", HttpStatus.OK);
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
            return new ResponseEntity<>(" Post was saved", HttpStatus.CREATED);
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
            return new ResponseEntity<>(" Post was updated", HttpStatus.OK);
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
            return new ResponseEntity<>(" Post was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/findAll-posts")
    public ResponseEntity<List<Post>> getAll(HttpSession session) {
        try {
            Utils.loginValidation(session);
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
