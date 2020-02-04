package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.Message;
import com.findme.service.MessageService;
import com.findme.util.Verification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {
    private MessageService messageService;
    private Verification verification;

    @Autowired
    public MessageController(MessageService messageService, Verification verification) {
        this.messageService = messageService;
        this.verification = verification;
    }

    @RequestMapping(path = "/{messageId}", method = RequestMethod.GET)
    public String getMessage(Model model, @PathVariable String messageId) throws DaoException {
        try {
            model.addAttribute("message", messageService.findById(verification.stringToLong(messageId)));
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
            messageService.findById(id);
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
    public ResponseEntity<String> save(@RequestBody Message message) throws DaoException {
        try {
            messageService.save(message);
            return new ResponseEntity<>(" Message was saved", HttpStatus.CREATED);
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
    public ResponseEntity<String> update(@RequestBody Message message) throws DaoException {
        try {
            messageService.update(message);
            return new ResponseEntity<>(" Message was updated", HttpStatus.OK);
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
    public ResponseEntity<String> delete(@RequestBody Message message) throws DaoException {
        try {
            messageService.delete(message);
            return new ResponseEntity<>(" Message was deleted ", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/delete/{messageId}",
            produces = "text/plain")
    public ResponseEntity<String> deleteById(@PathVariable String messageId) throws DaoException {
        try {
            messageService.deleteById(verification.stringToLong(messageId));
            return new ResponseEntity<>(" Message was deleted ", HttpStatus.OK);
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
    public ResponseEntity<List<Message>> getAll() throws DaoException {
        try {
            return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
