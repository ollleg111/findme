package com.findme.controller;

import com.findme.models.Message;
import com.findme.models.User;
import com.findme.service.MessageService;
import com.findme.service.SetDateAction;
import com.findme.service.UserService;
import com.findme.util.Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    public ResponseEntity<String> update(
            HttpSession session,
            @ModelAttribute Message message)
    {
        Utils.loginValidation(session);
        message.setUserFrom((User)session.getAttribute("user"));
        messageService.update(message, SetDateAction.UPDATE);
        log.info("Message was updated");
        return new ResponseEntity<>("Message was updated", HttpStatus.OK);
    }

    @PutMapping(value = "/read")
    public ResponseEntity<String> read(
            HttpSession session,
            @ModelAttribute Message message)
    {
        Utils.loginValidation(session);
        message.setUserFrom((User)session.getAttribute("user"));
        messageService.update(message, SetDateAction.READ);
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
        messageService.update(message, SetDateAction.DELETE);
        log.info("Message was deleted");
        return new ResponseEntity<>("Message was deleted", HttpStatus.OK);
    }

    @GetMapping(value = "/historyTo/{userToId}")
    public String historyTo(
            HttpSession session,
            Model model,
            @RequestParam String userToId,
            @RequestParam String messageIndex)
    {
        Utils.loginValidation(session);
        Integer indexFromList = 0;
        List<Message> messagesList;
        if (messageIndex != null){
            indexFromList = Integer.valueOf(messageIndex);
            messagesList = messageService.findMessagesToUserId(
                    ((User)session.getAttribute("user")).getId(),
                    Utils.stringToLong(userToId),
                    indexFromList);
            /*
            Сообщения должны подгружаться батчами по 20
             */
            indexFromList = indexFromList + 20;
        } else {
            messagesList = messageService.findMessagesToUserId(
                    ((User)session.getAttribute("user")).getId(),
                    Utils.stringToLong(userToId),
                    indexFromList);
            /*
            Сообщения должны подгружаться батчами по 20
             */
            indexFromList = 20;
        }
        model.addAttribute("messages", messagesList);
        model.addAttribute("messagesIndexFromList", indexFromList);
        log.info("Messages was found");
        return "messages/messageList";
    }


    @PutMapping(value = "/updateListMessages")
    public ResponseEntity<String> updateListMessages(
            HttpSession session,
            @ModelAttribute List<Message> messagesList)
    {
        Utils.loginValidation(session);
        for(Message message : messagesList){
            message.setUserFrom((User)session.getAttribute("user"));
        }
        messageService.updateListMessagesToUser(messagesList);
        log.info("Messages was deleted");
        return new ResponseEntity<>("Messages was deleted", HttpStatus.OK);
    }

    @PutMapping(value = "/updateAllToUser")
    public ResponseEntity<String> updateAllDateMessages(
            HttpSession session,
            @RequestParam String userToId)
    {
        Utils.loginValidation(session);
        messageService.updateAllMessagesToUser(
                ((User)session.getAttribute("user")).getId(),
                Utils.stringToLong(userToId));
        log.info("Messages to user with id: " + userToId + " was deleted");
        return new ResponseEntity<>("Messages to user with id: " + userToId +
                " was deleted", HttpStatus.OK);
    }
}
