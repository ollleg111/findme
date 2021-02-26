package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.User;
import com.findme.service.UserService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
@Slf4j
public class UserController {
    private UserService userService;

    @GetMapping(path = "/{userId}")
    public String getUser(Model model, @PathVariable String userId) {
            model.addAttribute("user", userService.findById(Utils.stringToLong(userId)));
            log.info("Get user with id: " + userId);
            return "users/successUserPage";
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
    public String registerUser(@ModelAttribute("user") @Validated User user,
                                               BindingResult bindingResult) {
            user.setDateRegistered(new Date());
            user.setDateLastActive(new Date());

            if(bindingResult.hasErrors()) return "users/index";
            userService.save(user);
            log.info("Register user data: " + user.getFirstName() + " " + user.getLastName());
            return "users/index";
    }

    @PatchMapping(value = "/update-user")
    public String update(@ModelAttribute("user") @Validated User user, BindingResult bindingResult) {
            if(bindingResult.hasErrors()) return "users/index";
            userService.update(user);
            log.info("Update user data: " + user.getFirstName() + " " + user.getLastName());
            return "users/index";
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
    public ResponseEntity<List<User>> getAll(HttpSession session, Model model) {
            Utils.loginValidation(session);
            log.info("Get users list from method getAll(HttpSession session, Model model)");
            List<User> getAll = userService.findAll();
            model.addAttribute("user/usersList", getAll);
            return new ResponseEntity<>(getAll, HttpStatus.OK);
    }
}
