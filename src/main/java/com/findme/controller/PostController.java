package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.Post;
import com.findme.service.PostService;
import com.findme.util.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/post")
public class PostController {
    private PostService postService;
    private Verification verification;

    @Autowired
    public PostController(PostService postService, Verification verification) {
        this.postService = postService;
        this.verification = verification;
    }

    @RequestMapping(path = "/{postId}", method = RequestMethod.GET)
    public String getPost(Model model, @PathVariable String postId) throws DaoException {
        try {
            model.addAttribute("post", postService.findById(verification.stringToLong(postId)));
            return "profile";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error , BadRequestException";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error , InternalServerException";
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/findById",
            produces = "text/plain")
    public ResponseEntity<String> findById(@RequestParam(value = "id") Long id) throws DaoException {
        try {
            postService.findById(id);
            return new ResponseEntity<>(" ok ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/save",
            produces = "text/plain")
    public ResponseEntity<String> save(@RequestBody Post post) throws DaoException {
        try {
            postService.save(post);
            return new ResponseEntity<>(" Post was saved", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/update",
            produces = "text/plain")
    public ResponseEntity<String> update(@RequestBody Post post) throws DaoException {
        try {
            postService.update(post);
            return new ResponseEntity<>(" Post was updated", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/deletePost",
            produces = "text/plain")
    public ResponseEntity<String> delete(@RequestBody Post post) throws DaoException {
        try {
            postService.delete(post);
            return new ResponseEntity<>(" Post was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/delete/{postId}",
            produces = "text/plain")
    public ResponseEntity<String> deleteById(@PathVariable String postId) throws DaoException {
        try {
            postService.deleteById(verification.stringToLong(postId));
            return new ResponseEntity<>(" Post was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/findAll",
            produces = "text/plain")
    public ResponseEntity<List<Post>> getAll() throws DaoException {
        try {
            return new ResponseEntity<>(postService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
