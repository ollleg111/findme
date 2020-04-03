package com.findme.controller;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.models.User;
import com.findme.service.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RelationshipController {
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/relationship-add",
            produces = "text/plain")
    public ResponseEntity<String> addRelationship(
            @RequestParam(value = "userIdFrom") String userIdFrom,
            @RequestParam(value = "userIdTo") String userIdTo) throws DaoException {
        try {
            relationshipService.addRelationship(userIdFrom, userIdTo);
            return new ResponseEntity<>(" Relationship was saved", HttpStatus.CREATED);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/relationship-update",
            produces = "text/plain")
    public ResponseEntity<String> updateRelationship(
            @RequestParam(value = "userIdFrom") String userIdFrom,
            @RequestParam(value = "userIdTo") String userIdTo,
            @RequestParam(value = "status") String status) throws DaoException {
        try {
            relationshipService.updateRelationship(userIdFrom, userIdTo, status);
            return new ResponseEntity<>(" Relationship was updated", HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/relationship-getIn",
            produces = "text/plain")
    public ResponseEntity<List<User>> getIncomeRequest
            (@RequestParam(value = "userId") String userId) throws DaoException {
        try {
            return new ResponseEntity<>(relationshipService.getIn(userId), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/relationship-getOut",
            produces = "text/plain")
    public ResponseEntity<List<User>> getOutcomeRequest(
            @RequestParam(value = "userId") String userId) throws DaoException {
        try {
            return new ResponseEntity<>(relationshipService.getOut(userId), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/relationship-status",
            produces = "text/plain")
    public ResponseEntity<RelationshipStatus> getRelationshipStatus(
            @RequestParam(value = "userId") String userFromTo,
            @RequestParam(value = "userId") String userIdTo) throws DaoException {
        try {
            return new ResponseEntity<>(relationshipService.getRelationshipStatus(userFromTo, userIdTo), HttpStatus.OK);
        } catch (BadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
