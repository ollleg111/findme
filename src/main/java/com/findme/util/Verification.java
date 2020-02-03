package com.findme.util;

import com.findme.exceptions.BadRequestException;

public class Verification {
    public static Long idTest(String number) throws BadRequestException {
        long id = Long.parseLong(number);
        if (id <= 0) throw new BadRequestException(" Wrong entered ID number ");
        return id;
    }
}
