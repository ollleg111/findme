package com.findme.dao;

import com.findme.exceptions.DaoException;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
class GeneralDAO<T> {
    private Class<T> typeParameterClass;

    @PersistenceContext
    private EntityManager entityManager;

    public void setTypeParameterClass(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private static final String SELECT_FROM = "SELECT * FROM = ?";

    public T findById(Long id) throws DaoException {
        try {
            return entityManager.find(typeParameterClass, id);
        } catch (DaoException exception) {
            System.err.println("findById is failed");
            System.err.println(exception.getMessage());
            throw new HibernateException(" The method findById(Long id) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    public T save(T t) throws DaoException {
        try {
            entityManager.persist(t);
        } catch (DaoException exception) {
            System.err.println("save is failed");
            System.err.println(exception.getMessage());
            throw new HibernateException(" The method save(T t) was failed in class "
                    + typeParameterClass.getName());
        }
        return t;
    }

    public T update(T t) throws DaoException {
        try {
            entityManager.merge(t);
        } catch (DaoException exception) {
            System.err.println("update is failed");
            System.err.println(exception.getMessage());
            throw new HibernateException(" The method update(T t) was failed in class "
                    + typeParameterClass.getName());
        }
        return t;
    }

    public void delete(T t) throws DaoException {
        try {
            entityManager.remove(t);
        } catch (DaoException exception) {
            System.err.println("delete is failed");
            System.err.println(exception.getMessage());
            throw new HibernateException(" The method delete(T t) was failed in class "
                    + typeParameterClass.getName());
        }
    }

    public List<T> findAll() throws DaoException {
        try {
            Query query = entityManager.createNativeQuery(SELECT_FROM, String.class);
            query.setParameter(1, typeParameterClass.getName());
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method findAll() from class "
                    + typeParameterClass.getName());
        }
    }
}
