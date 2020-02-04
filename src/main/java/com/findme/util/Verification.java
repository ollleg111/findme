package com.findme.util;

import com.findme.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class Verification {
    public Long stringToLong(String number) throws BadRequestException {
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
