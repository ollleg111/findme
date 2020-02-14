package com.findme.exceptions;

import org.hibernate.HibernateException;

public class DaoException extends HibernateException {
    public DaoException(String message) {
        super(message);
    }
}
