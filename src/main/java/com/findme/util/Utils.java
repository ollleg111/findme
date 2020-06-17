package com.findme.util;

import com.findme.exceptions.BadRequestException;
import com.findme.models.User;

import javax.servlet.http.HttpSession;

public class Utils {
    public static Long stringToLong(String number) throws BadRequestException {
        try {
            long id = Long.parseLong(number);
            if (id <= 0) {
                throw new BadRequestException(" Wrong entered number: " + number);
            }
            return id;
        } catch (NumberFormatException e) {
            throw new BadRequestException(" Incorrect format ");
        }
    }

    public static void loginValidation(HttpSession session) throws BadRequestException {
        isUserWithLogin(session, ((User) session.getAttribute("user")).getId());
    }

    public static void isUserWithLogin(HttpSession session, Long userId) throws BadRequestException {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() != userId) throw new BadRequestException("You must write the login");
    }
}
