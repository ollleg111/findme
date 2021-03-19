package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.PostFilter;
import com.findme.models.User;
import com.findme.service.PostService;
import com.findme.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@AllArgsConstructor
@Slf4j
public class PostController {
    private PostService postService;

    @GetMapping(path = "/{postId}")
    public String getPost(
            HttpSession session,
            Model model,
            @PathVariable String postId)
    {
            Utils.loginValidation(session);
            model.addAttribute("post", postService.findById(Utils.stringToLong(postId)));
            log.info("Get post with id: " + postId);
            return "posts/successPostPage";
    }

    @DeleteMapping(value = "/delete-post/{postId}")
    public ResponseEntity<String> deleteById(
            HttpSession session,
            @PathVariable String postId)
    {
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
    public String save(
            HttpSession session,
            @ModelAttribute("post") @Validated Post post,
            BindingResult bindingResult)
    {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "posts/newPost";
            postService.save(post);
            log.info("Add post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return "posts/newPost";
    }

    @PatchMapping(value = "/update-post")
    public String update(
            HttpSession session,
            @ModelAttribute("post") @Validated Post post,
            BindingResult bindingResult)
    {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "posts/newPost";
            postService.update(post);
            log.info("Update post data: " + post.getMessage() + " " + post.getLocation() + " " +
                    post.getDatePosted() );
            return "posts/newPost";
    }

    @DeleteMapping(value = "/delete-post")
    public ResponseEntity<String> delete(
            HttpSession session,
            @RequestBody Post post)
    {
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
            Utils.loginValidation(session);
            log.info("Get posts list from method getDataSortedPostsList(HttpSession session, Model model)");
            List<Post> postDataList = postService.getDataSortedPostsList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", postDataList);
            return new ResponseEntity<>(postDataList, HttpStatus.OK);
    }

    @GetMapping(value = "/getListWithFilter")
    public ResponseEntity<List<Post>> getListWithFilter(
            HttpSession session,
            Model model,
            @ModelAttribute PostFilter postFilter)
    {
            Utils.loginValidation(session);
            log.info("Get posts list from method getListWithFilter(HttpSession session, Model model, @ModelAttribute PostFilter postFilter)");
            List<Post> postFilterList = postService.getFilteredList((User)session.getAttribute("user"), postFilter);
            model.addAttribute("posts/postList", postFilterList);
            return new ResponseEntity<>(postFilterList, HttpStatus.OK);
    }

    @GetMapping(value = "/feed")
    public ResponseEntity<List<Post>> feed(
            HttpSession session,
            Model model)
    {
            Utils.loginValidation(session);
            log.info("Get posts list from method feed(HttpSession session, Model model)");
            List<Post> feedList = postService.getFeedList((User)session.getAttribute("user"));
            model.addAttribute("posts/postList", feedList);
            return new ResponseEntity<>(feedList, HttpStatus.OK);
    }

    @GetMapping(value = "/getPostsList")
    public ResponseEntity<List<Post>> getAll(
            HttpSession session,
            Model model)
    {
            Utils.loginValidation(session);
            log.info("Get posts list from method getAll(HttpSession session, Model model)");
            List<Post> getAll = postService.findAll();
            model.addAttribute("posts/postList", getAll);
            return new ResponseEntity<>(getAll, HttpStatus.OK);
    }
}
