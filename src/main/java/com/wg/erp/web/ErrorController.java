package com.wg.erp.web;


import org.hibernate.ObjectNotFoundException;
import org.hibernate.annotations.NotFound;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNoHandlerFoundException(NoHandlerFoundException ex) {
        ModelAndView modelAndView = new ModelAndView("error/404");
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        modelAndView.addObject("message", "The page not found!");
        return modelAndView;
    }


}
