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
        registrationValidation(user);
        return userDAO.save(user);
    }

    public User update(User user) throws DaoException {
        nullValidation(user);
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

    public void nullValidation(User user) throws ServiceException {
        if (user.getFirstName() == null || user.getFirstName().isEmpty())
            throw new BadRequestException("You do not write first name");
        if (user.getLastName() == null || user.getLastName().isEmpty())
            throw new BadRequestException("You do not write last name");
        if (user.getPhoneNumber() == null || user.getPhoneNumber().isEmpty())
            throw new BadRequestException("You do not write phone number");
        if (user.getMail() == null || user.getMail().isEmpty())
            throw new BadRequestException("You do not write e-mail");
        if (user.getCountry() == null || user.getCountry().isEmpty())
            throw new BadRequestException("You do not write country");
        if (user.getCity() == null || user.getCity().isEmpty())
            throw new BadRequestException("You do not write city");
        if (user.getAge() == null)
            throw new BadRequestException("You do not write age");
        if (user.getSchool() == null || user.getSchool().isEmpty())
            throw new BadRequestException("You do not write school");
        if (user.getUniversity() == null || user.getUniversity().isEmpty())
            throw new BadRequestException("You do not write university");
    }

    public void registrationValidation(User user) throws ServiceException {
        nullValidation(user);
        if(!userDAO.testPhoneAndMail(user.getPhoneNumber(), user.getMail()))
            throw new BadRequestException("User is already exist");
    }
}
