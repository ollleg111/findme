package com.findme.util;

import com.findme.exceptions.BadRequestException;

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
}
