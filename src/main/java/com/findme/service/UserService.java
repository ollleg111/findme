package com.findme.service;

import com.findme.dao.UserDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
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

    public User findById(Long id) throws DaoException {
        User user = userDAO.findById(id);
        userNullValidator(user);
        return userDAO.findById(id);
    }

    public User save(User user) throws DaoException {
        return userDAO.save(user);
    }

    public User update(User user) throws DaoException {
        return userDAO.update(user);
    }

    public void delete(User user) throws DaoException {
        userDAO.delete(user);
    }

    public void deleteById(Long id) throws DaoException {
        User user = userDAO.findById(id);
        userNullValidator(user);
        userDAO.delete(user);
    }

    public List<User> findAll() throws DaoException {
        return userDAO.findAll();
    }

    private void userNullValidator(User user) throws ServiceException {
        if (user == null) throw new BadRequestException("Post does not exist in method" +
                " postNullValidator(Post post) from class " +
                UserService.class.getName());
    }
}
