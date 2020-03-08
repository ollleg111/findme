package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.exceptions.ServiceException;
import com.findme.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User findById(Long id) throws DaoException, NotFoundException {
        User user = userDAO.findById(id);
        if (user == null) throw
                new NotFoundException("User does not exist in method findById(Long id) from class " +
                        UserService.class.getName());
        return user;
    }

    public User save(User user) throws ServiceException {
        validation(user);
        return userDAO.save(user);
    }

    public User update(User user) throws DaoException {
        return userDAO.update(user);
    }

    public void delete(User user) throws DaoException {
        userDAO.delete(user);
    }

    public void deleteById(Long id) throws DaoException, NotFoundException {
        User user = userDAO.findById(id);
        if (user == null) throw
                new NotFoundException("User does not exist in method deleteById(Long id) from class " +
                        UserService.class.getName());
        userDAO.delete(user);
    }

    public List<User> findAll() throws DaoException {
        return userDAO.findAll();
    }

    public void validation(User user) throws ServiceException {
        if(!userDAO.validationMailAndPhoneNumber(user.getPhoneNumber(), user.getMail()))
            throw new BadRequestException("User is already exist");
    }
}
