package com.test.usersapi.controllers;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {
    private static final Logger logger = Logger.getLogger(BaseController.class.getName());
    
    protected final static String created = "Successfully created new record!";
    protected final static String success = "Success";
    protected final static String error = "Error occurred during process!";
    protected final static String inputParameter = "Please check input parameter!";
    protected final static String notFound = "The specified record was not found!";
    protected final static String deleted = "The specified record was deleted!";
    protected final static String updated = "The specified record was updated!";
    protected final static String timeOut = "Request time out!";

    protected static String sort = "id";
    protected static String filter = "";
    protected static int offset = 0;
    protected static int limit = 0;

}
