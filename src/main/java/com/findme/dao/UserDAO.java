package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

    private static final String GET_USER =
            "SELECT * FROM" +
                    " USERS WHERE" +
                    " USERS.E_MAIL = ? AND" +
                    " USERS.PASSWORD = ?";

    private static final String SELECT_FROM = "SELECT * FROM USERS";

    private String alarmMessage = UserDAO.class.getName();

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

    @Transactional
    public boolean validationMailAndPhoneNumber(String phoneNumber, String mail) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(VALIDATION_MAIL_AND_PHONE_NUMBER, Boolean.class);
            query.setParameter(1, phoneNumber);
            query.setParameter(2, mail);
            return query.getSingleResult() == null;
        } catch (InternalServerError exception) {
            System.err.println(exception.getMessage());
            throw new InternalServerError("Operation with User was filed in method" +
                    " testPhoneAndMail(String phoneNumber, String mail) from class " + alarmMessage);
        }
    }

    @Transactional
    public User getUser(String mail, String password) throws InternalServerError {
        try {
            Query query = entityManager.createNativeQuery(GET_USER, User.class);
            query.setParameter(1, mail);
            query.setParameter(2, password);
            return (User) query.getSingleResult();
        } catch (InternalServerError e) {
            throw new InternalServerError("Operation with user data was filed in method" +
                    "getUser(String email, String password) from class " + alarmMessage);
        }
    }
}
