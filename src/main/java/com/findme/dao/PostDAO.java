package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.models.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostDAO extends GeneralDAO<Post> {
    @PersistenceContext
    private EntityManager entityManager;

    public PostDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Post.class);
    }

    @Override
    public Post findById(Long id) throws DaoException {
        return super.findById(id);
    }

    @Override
    public Post save(Post post) throws DaoException {
        return super.save(post);
    }

    @Override
    public Post update(Post post) throws DaoException {
        return super.update(post);
    }

    @Override
    public void delete(Post post) throws DaoException {
        super.delete(post);
    }

    @Override
    public List<Post> findAll() throws DaoException {
        return super.findAll();
    }
}
