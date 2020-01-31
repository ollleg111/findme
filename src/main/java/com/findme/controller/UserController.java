package com.findme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("text", "value");
        return "profile";
    }
}
