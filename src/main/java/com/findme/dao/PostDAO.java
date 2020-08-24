package com.findme.dao;

import com.findme.exceptions.DaoException;
import com.findme.exceptions.InternalServerError;
import com.findme.models.Post;
import com.findme.models.PostFilter;
import com.findme.models.Relationship;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostDAO extends GeneralDAO<Post> {
    @PersistenceContext
    private EntityManager entityManager;

    public PostDAO() {
        setEntityManager(entityManager);
        setTypeParameterClass(Post.class);
    }

    private static final String GET_ALL_POSTS_SORTED_BY_DATE = "SELECT P.* FROM POST P INNER JOIN RELATIONSHIP R ON" +
            " (R.USER_FROM_ID = ?1 AND R.USER_TO_ID = P.USER_POSTED_ID) OR" +
            " (R.USER_TO_ID = ?1 AND R.USER_FROM_ID = P.USER_POSTED_ID) WHERE R.STATUS = 'FRIENDS' ORDER BY" +
            " P.DATE_POSTED DESC";

    private static final String GET_ALL_USER_POSTS = "SELECT * FROM POST WHERE USER_ID = ?1";
    private static final String GET_ALL_OWNER_FRIENDS_POSTS = "SELECT P.* FROM POST INNER JOIN RELATIONSHIP R ON" +
            " P.USER_ID = ?1 WHERE R.STATUS = 'FRIENDS'" ;

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

    public List<Post> getDataSortedPostsList(Long userId) throws DaoException{
        try {
            Query query = entityManager.createNativeQuery(GET_ALL_POSTS_SORTED_BY_DATE, Post.class);
            query.setParameter(1, userId);
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method getDataSortedPostsList(Long userId)" +
                    " from class" + PostDAO.class.getName());
        }
    }

    public List<Post> getFilteredPostsById(Long ownerId) throws DaoException{
        try {
            Query query = entityManager.createNativeQuery(GET_ALL_USER_POSTS, Post.class);
            query.setParameter(1, ownerId);
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method getFilteredPostsById(Long ownerId)" +
                    " from class" + PostDAO.class.getName());
        }
    }

    public List<Post> getFilteredByFriends(Long ownerId) throws DaoException{
        try {
            Query query = entityManager.createNativeQuery(GET_ALL_OWNER_FRIENDS_POSTS, Post.class);
            query.setParameter(1, ownerId);
            return query.getResultList();
        } catch (DaoException e) {
            throw new HibernateException("Operation filed in method getFilteredByFriends(Long ownerId)" +
                    " from class" + PostDAO.class.getName());
        }
    }

}
