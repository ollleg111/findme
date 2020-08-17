package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Message;
import com.findme.service.MessageService;
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
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/{messageId}")
    public String getMessage(HttpSession session, Model model, @PathVariable String messageId) {
        try {
            Utils.loginValidation(session);
            model.addAttribute("message", messageService.findById(Utils.stringToLong(messageId)));
            return "profile";
        } catch (BadRequestException e) {
            return "BadRequestException " + e.getMessage();
        } catch (NotFoundException e) {
            return "Error 404 " + e.getMessage();
        } catch (InternalServerError e) {
            return "System Error " + e.getMessage();
        }
    }

    @DeleteMapping(value = "/delete-message/{messageId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String messageId) {
        try {
            Utils.loginValidation(session);
            messageService.deleteById(Utils.stringToLong(messageId));
            return new ResponseEntity<>(" Message was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-----------------------------------------------------------------------------------------------
    @GetMapping(value = "/findById-message")
    public ResponseEntity<String> findById(HttpSession session, @RequestParam(value = "id") String messageId) {
        try {
            Utils.loginValidation(session);
            messageService.findById(Utils.stringToLong(messageId));
            return new ResponseEntity<>(" ok ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add-message")
    public ResponseEntity<String> save(HttpSession session, @RequestBody Message message) {
        try {
            Utils.loginValidation(session);
            messageService.save(message);
            return new ResponseEntity<>(" Message was saved", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update-message")
    public ResponseEntity<String> update(HttpSession session, @RequestBody Message message) {
        try {
            Utils.loginValidation(session);
            messageService.update(message);
            return new ResponseEntity<>(" Message was updated", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-message")
    public ResponseEntity<String> delete(HttpSession session, @RequestBody Message message) {
        try {
            Utils.loginValidation(session);
            messageService.delete(message);
            return new ResponseEntity<>(" Message was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getAll-messages")
    public ResponseEntity<List<Message>> getAll(HttpSession session) {
        try {
            Utils.loginValidation(session);
            return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
