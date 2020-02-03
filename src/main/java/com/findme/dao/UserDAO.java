package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.models.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO extends GeneralDAO<User> {
    @PersistenceContext
    private EntityManager entityManager;

    public UserDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(User.class);
    }

    @Override
    public User findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public User save(User user) throws DaoException {
        return super.save(user);
    }

    @Override
    public User update(User user) throws DaoException {
        return super.update(user);
    }

    @Override
    public void delete(User user) throws DaoException {
        super.delete(user);
    }

    @Override
    public List<User> findAll() throws DaoException {
        return super.findAll();
    }
}
