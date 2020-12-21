package com.findme.handler;

import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.InternalServerError;
import com.findme.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public ModelAndView handlerBadRequestException (BadRequestException e){
        log.error("Error 400. Bad Request. " + e.getMessage());
        return new ModelAndView("errors/errorBadRequest");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = InternalServerError.class)
    public ModelAndView handlerInternalServerError (InternalServerError e){
        log.error("Error 500. Internal Server Error. " + e.getMessage());
        return new ModelAndView("errors/errorInternalServerError");
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NotFoundException.class)
    public ModelAndView handlerNotFoundException (NotFoundException e) {
        log.error("Error 404. Not Found Exception. " + e.getMessage());
        return new ModelAndView("errors/errorNotFound");
    }
}
