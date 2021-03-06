package com.findme.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(path = "/")
    public String home() {
        return "index";
    }

    @GetMapping(path = "/test-ajax")
    public String trouble() {
        return "trouble";
    }

    @GetMapping(path = "/test-ajax2")
    public ResponseEntity<String> testAjax()
    {
        return new ResponseEntity<String>("trouble", HttpStatus.NOT_FOUND);
    }
}
