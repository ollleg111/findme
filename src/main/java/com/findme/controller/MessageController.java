package com.findme.controller;

import com.findme.models.Message;
import com.findme.models.User;
import com.findme.service.MessageService;
import com.findme.service.UserService;
import com.findme.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/messages")
@AllArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;

    @GetMapping(path = "/findById/{messageId}")
    public String getMessage(
            HttpSession session,
            Model model,
            @PathVariable String messageId)
    {
        Utils.loginValidation(session);
        model.addAttribute("message", messageService.findById(Utils.stringToLong(messageId)));
        log.info("Get message with id: " + messageId);
        return "messages/successMessagePage";
    }

    @PostMapping(value = "/send")
    public ResponseEntity<String> send(
            HttpSession session,
            @RequestParam String userToId,
            @RequestParam String messageText)
    {
        Utils.loginValidation(session);
        Message message = new Message();
        message.setText(messageText);
        message.setUserFrom((User)session.getAttribute("user"));
        message.setUserTo(userService.findById(Utils.stringToLong(userToId)));
        messageService.save(message);
        log.info("Message from user: " + message.getUserFrom().getFirstName() + " was sent now to user:" +
                message.getUserTo().getFirstName());
        return new ResponseEntity<>("Message was send", HttpStatus.OK);
    }

    @PatchMapping(value = "/update")
    public String update(
            HttpSession session,
            @ModelAttribute("message") @Valid Message message,
            BindingResult bindingResult)
    {
        Utils.loginValidation(session);
        if(bindingResult.hasErrors()) return "messages/newMessage";
        message.setUserFrom((User)session.getAttribute("user"));
        messageService.update(message);
        log.info("Message was updated");
        return "messages/successMessagePage";
    }

    @PutMapping(value = "/read")
    public ResponseEntity<String> read(
            HttpSession session,
            @ModelAttribute Message message)
    {
        Utils.loginValidation(session);
        message.setUserFrom((User)session.getAttribute("user"));
        messageService.updateDateRead(message);
        log.info("Message was read");
        return new ResponseEntity<>("Message was read", HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> delete(
            HttpSession session,
            @ModelAttribute Message message)
    {
        Utils.loginValidation(session);
        message.setUserFrom((User)session.getAttribute("user"));
        messageService.updateDateDelete(message);
        log.info("Message was deleted");
        return new ResponseEntity<>("Message was deleted", HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteSelectedMessages")
    public ResponseEntity<String> deleteMessages(
            HttpSession session,
            @ModelAttribute List<Message> messages)
    {
        Utils.loginValidation(session);
        for(Message message : messages){
            message.setUserFrom((User)session.getAttribute("user"));
        }
        messageService.updateMessages(messages);
        log.info("Messages was deleted");
        return new ResponseEntity<>("Messages was deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/getList")
    public ResponseEntity<List<Message>> getAll(
            HttpSession session)
    {
            Utils.loginValidation(session);
            log.info("Get messages list from method getAll(HttpSession session)");
            List<Message> getAll = messageService.findAll();
            return new ResponseEntity<>(getAll, HttpStatus.OK);
    }
}
