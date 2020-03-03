package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.models.User;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDAO extends GeneralDAO<User> {
    @PersistenceContext
    private EntityManager entityManager;

    public UserDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(User.class);
    }

    private static final String VALIDATION_MAIL_AND_PHONE_NUMBER =
            "SELECT * FROM" +
                    " USERS WHERE" +
                    " USERS.PHONE_NUMBER = ? AND" +
                    " USERS.E_MAIL = ?";

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

    @Transactional
    public boolean testPhoneAndMail(String phoneNumber, String mail) throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(VALIDATION_MAIL_AND_PHONE_NUMBER, Boolean.class);
            query.setParameter(1, phoneNumber);
            query.setParameter(2, mail);
            return (boolean) query.getSingleResult();
        } catch (DaoException exception) {
            System.err.println(exception.getMessage());
            throw new HibernateException("Operation with User was filed in method" +
                    " testPhoneAndMail(String phoneNumber, String mail) from class " +
                    UserDAO.class.getName());
        }
    }
}
