package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Message;
import com.findme.service.MessageService;
import com.findme.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/message")
@Slf4j
public class MessageController {
    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(path = "/{messageId}")
    public ResponseEntity<String> getMessage(HttpSession session, Model model, @PathVariable String messageId) {
        try {
            Utils.loginValidation(session);
            model.addAttribute("message", messageService.findById(Utils.stringToLong(messageId)));
            log.info("Get message id: " + messageId);
            return new ResponseEntity<>(" ok ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(value = "/delete-message/{messageId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String messageId) {
        try {
            Utils.loginValidation(session);
            messageService.deleteById(Utils.stringToLong(messageId));
            log.info("delete message id: " + messageId);
            return new ResponseEntity<>(" Message was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //-----------------------------------------------------------------------------------------------
    @GetMapping(value = "/findById-message")
    public ResponseEntity<String> findById(HttpSession session, Model model, @RequestParam(value = "id") String messageId) {
        try {
            Utils.loginValidation(session);
            model.addAttribute("message", messageService.findById(Utils.stringToLong(messageId)));
            log.info("Find message id: " + messageId);
            return new ResponseEntity<>(" ok ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/add-message")
    public ResponseEntity<String> save(HttpSession session, @ModelAttribute("message") @Valid Message message) {
        try {
            Utils.loginValidation(session);
            messageService.save(message);
            log.info("Add message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
            return new ResponseEntity<>(" Message was saved", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/update-message")
    public ResponseEntity<String> update(HttpSession session, @ModelAttribute("message") @Valid Message message) {
        try {
            Utils.loginValidation(session);
            messageService.update(message);
            log.info("Update message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
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
            log.info("Delete message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
            return new ResponseEntity<>(" Message was deleted", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getMessagesList")
    public ResponseEntity<List<Message>> getAll() {
        try {
            log.info("Get messages list from method getAll()");
            return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
