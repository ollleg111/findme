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

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/users")
@AllArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping(path = "/findById/{userId}")
    public String getUser(
            Model model,
            @PathVariable String userId)
    {
            model.addAttribute("user", userService.findById(Utils.stringToLong(userId)));
            log.info("Get user with id: " + userId);
            return "users/successUserPage";
    }

    @DeleteMapping(value = "/deleteById/{userId}")
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

    @PatchMapping(value = "/update")
    public String update(
            @ModelAttribute("user") @Validated User user,
            BindingResult bindingResult)
    {
        if(bindingResult.hasErrors()) return "users/index";
        userService.update(user);
        log.info("Update user data: " + user.getFirstName() + " " + user.getLastName());
        return "users/successUserPage";
    }

    //http://localhost:8080/users/login?mail=aaaa@gmail.com&password=AAAA
    @PostMapping(path = "/login")
    public ResponseEntity<String> login(
            HttpSession session,
            @RequestParam("mail") String mail,
            @RequestParam("password") String password)
    {
            User user = userService.login(mail, password);
            session.setAttribute("user", user);
            log.info("login complete");
            return new ResponseEntity<>("login complete", HttpStatus.OK);
    }

    @GetMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpSession session)
    {
            session.invalidate();
            log.info("logout complete");
            return new ResponseEntity<>("logout complete", HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public String registerUser(
            @ModelAttribute("user") @Validated User user,
            BindingResult bindingResult)
    {
            user.setDateRegistered(new Date());
            user.setDateLastActive(new Date());

            if(bindingResult.hasErrors()) return "users/index";

            userService.save(user);
            log.info("Register user with data: " + user.getFirstName() + " " + user.getLastName());
            return "users/successUserPage";
    }
}
