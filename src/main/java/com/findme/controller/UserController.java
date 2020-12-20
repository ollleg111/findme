package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
import com.findme.models.User;
import com.findme.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/{userId}")
    public String getUser(Model model, @PathVariable String userId) {
        try {
            model.addAttribute("user", userService.findById(Utils.stringToLong(userId)));
            log.info("Get user with id: " + userId);
            return "users/successUserPage";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorNotFound";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }
    }

    @DeleteMapping(value = "/delete-user/{userId}")
    public ResponseEntity<String> deleteById(@PathVariable String userId) {
        try {
            userService.deleteById(Utils.stringToLong(userId));
            log.info("Delete user with id: " + userId);
            return new ResponseEntity<>("User was deleted", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/login")
    public String home() {
        return "login";
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(HttpSession session, HttpServletRequest request) {
        try {
            User user = userService.login(
                    request.getParameter("mail"),
                    request.getParameter("password"));
            session.setAttribute("user", user);
            log.info("login complete");
            return new ResponseEntity<>("login complete", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        try {
            session.invalidate();
            log.info("logout complete");
            return new ResponseEntity<>("logout complete", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/register-user")
    public String registerUser(@ModelAttribute("user") @Validated User user, Model model,
                                               BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) return "users/index";
            userService.save(user);
            log.info("Register user data: " + user.getFirstName() + " " + user.getLastName());
            return "users/index";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }
    }

    @PatchMapping(value = "/update-user")
    public String update(@ModelAttribute("user") @Validated User user, Model model, BindingResult bindingResult) {
        try {
            if(bindingResult.hasErrors()) return "users/index";
            userService.update(user);
            log.info("Update user data: " + user.getFirstName() + " " + user.getLastName());
            return "users/index";
        } catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }
    }

    @DeleteMapping(value = "/delete-user")
    public ResponseEntity<String> delete(@RequestBody User user) {
        try {
            userService.delete(user);
            log.info("Delete user data: " + user.getFirstName() + " " + user.getLastName());
            return new ResponseEntity<>("User was deleted", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getUsersList")
    public ResponseEntity<List<User>> getAll() {
        try {
            log.info("Get users list from method getAll()");
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
