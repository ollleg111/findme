package com.findme.controller;

import com.findme.models.Message;
import com.findme.service.MessageService;
import com.findme.util.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String getMessage(HttpSession session, Model model, @PathVariable String messageId) {
        //try {
            Utils.loginValidation(session);
            model.addAttribute("message", messageService.findById(Utils.stringToLong(messageId)));
            log.info("Get message with id: " + messageId);
            return "messages/successMessagePage";

        /*} catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (NotFoundException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorNotFound";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }*/
    }

    @DeleteMapping(value = "/delete-message/{messageId}")
    public ResponseEntity<String> deleteById(HttpSession session, @PathVariable String messageId) {
        //try {
            Utils.loginValidation(session);
            messageService.deleteById(Utils.stringToLong(messageId));
            log.info("Delete message with id: " + messageId);
            return new ResponseEntity<>("Message was deleted", HttpStatus.OK);
        /*} catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @PostMapping(value = "/add-message")
    public String save(HttpSession session, Model model, @ModelAttribute("message") @Valid Message message,
                                       BindingResult bindingResult) {
        //try {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "messages/newMessage";
            messageService.save(message);
            log.info("Add message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
            return "messages/newMessage";
        /*} catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }*/
    }

    @PatchMapping(value = "/update-message")
    public String update(HttpSession session, Model model, @ModelAttribute("message") @Valid Message message,
                                         BindingResult bindingResult) {
        //try {
            Utils.loginValidation(session);
            if(bindingResult.hasErrors()) return "messages/newMessage";
            messageService.update(message);
            log.info("Update message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
            return "messages/newMessage";
        /*} catch (BadRequestException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorBadRequest";
        } catch (InternalServerError e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "errors/errorInternalServerError";
        }*/
    }

    @DeleteMapping(value = "/delete-message")
    public ResponseEntity<String> delete(HttpSession session, @RequestBody Message message) {
        //try {
            Utils.loginValidation(session);
            messageService.delete(message);
            log.info("Delete message data: " + message.getId() + " " + message.getDateRead() + " " +
                    message.getDateSent() + " " + message.getText());
            return new ResponseEntity<>("Message was deleted", HttpStatus.OK);
        /*} catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }

    @GetMapping(value = "/getMessagesList")
    public ResponseEntity<List<Message>> getAll() {
        //try {
            log.info("Get messages list from method getAll()");
            return new ResponseEntity<>(messageService.findAll(), HttpStatus.OK);
        /*} catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
    }
}
