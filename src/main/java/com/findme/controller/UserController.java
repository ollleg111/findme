package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.User;
import com.findme.service.UserService;
import com.findme.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/{userId}", method = RequestMethod.GET)
    public String getUser(Model model, @PathVariable String userId) throws DaoException {
/*
        Example from lesson!!!
        User user = new User();
        user.setFirstName("Andrey");
        user.setCity("TestCity");
        model.addAttribute("text", "value");
 */
        try {
            model.addAttribute("user", userService.findById(Utils.stringToLong(userId)));
            return "profile";
        } catch (BadRequestException e) {
            return "BadRequestException " + e.getMessage();
        } catch (Exception e) {
            return "System Error " + e.getMessage();
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/findById",
            produces = "text/plain")
    public ResponseEntity<String> findById(@RequestParam(value = "id") Long id) throws DaoException {
        try {
            userService.findById(id);
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
    public ResponseEntity<String> save(@RequestBody User user) throws DaoException {
        try {
            userService.save(user);
            return new ResponseEntity<>(" User was saved", HttpStatus.CREATED);
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
    public ResponseEntity<String> update(@RequestBody User user) throws DaoException {
        try {
            userService.update(user);
            return new ResponseEntity<>(" User was updated", HttpStatus.OK);
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
    public ResponseEntity<String> delete(@RequestBody User user) throws DaoException {
        try {
            userService.delete(user);
            return new ResponseEntity<>(" User was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/delete/{userId}",
            produces = "text/plain")
    public ResponseEntity<String> deleteById(@PathVariable String userId) throws DaoException {
        try {
            userService.deleteById(Utils.stringToLong(userId));
            return new ResponseEntity<>(" User was deleted ", HttpStatus.OK);
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
    public ResponseEntity<List<User>> getAll() throws DaoException {
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
