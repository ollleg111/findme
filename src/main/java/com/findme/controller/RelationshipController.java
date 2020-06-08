package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import com.findme.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class RelationshipController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @PostMapping(value = "/relationship-add")
    public ResponseEntity<String> addRelationship(
            HttpSession session,
            @RequestParam(value = "userIdFrom") String userIdFrom,
            @RequestParam(value = "userIdTo") String userIdTo) {
        try {
            Utils.isUserWithLogin(session, Utils.stringToLong(userIdFrom));
            relationshipService
                    .addRelationship(
                            Utils.stringToLong(userIdFrom),
                            Utils.stringToLong(userIdTo));
            return new ResponseEntity<>(" Relationship was saved", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/relationship-update")
    public ResponseEntity<String> updateRelationship(
            HttpSession session,
            @RequestParam(value = "userIdFrom") String userIdFrom,
            @RequestParam(value = "userIdTo") String userIdTo,
            @RequestParam(value = "status") String status) {
        try {
            Utils.isUserWithLogin(session, Utils.stringToLong(userIdFrom));
            relationshipService
                    .updateRelationship(
                            Utils.stringToLong(userIdFrom),
                            Utils.stringToLong(userIdTo),
                            status);
            return new ResponseEntity<>(" Relationship was updated", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/relationship-get-income/{userId}")
    public ResponseEntity<List<User>> getIncomeRequest(
            HttpSession session,
            @PathVariable String userId) {
        try {
            Utils.isUserWithLogin(session, Utils.stringToLong(userId));
            return new ResponseEntity<>(
                    relationshipService
                            .getIn(Utils.stringToLong(userId)),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/relationship-get-outcome/{userId}")
    public ResponseEntity<List<User>> getOutcomeRequest(
            HttpSession session,
            @PathVariable String userId) {
        try {
            Utils.isUserWithLogin(session, Utils.stringToLong(userId));
            return new ResponseEntity<>(
                    relationshipService
                            .getOut(Utils.stringToLong(userId)),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/relationship-status/{userFromTo}/{userIdTo}")
    public ResponseEntity<RelationshipStatus> getRelationshipStatus(
            HttpSession session,
            @PathVariable String userFromTo,
            @PathVariable String userIdTo) {
        try {
            Utils.isUserWithLogin(session, Utils.stringToLong(userFromTo));
            return new ResponseEntity<>(
                    relationshipService
                            .getStatus(
                                    Utils.stringToLong(userFromTo),
                                    Utils.stringToLong(userIdTo)),
                    HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (InternalServerError e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
