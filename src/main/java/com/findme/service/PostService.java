package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.ServiceException;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class PostService {
    private PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public Post findById(Long id) throws DaoException {
        Post post = postDAO.findById(id);
        postNullValidator(post);
        return postDAO.findById(id);
    }

    public Post save(Post post) throws DaoException {
        return postDAO.save(post);
    }

    public Post update(Post post) throws DaoException {
        return postDAO.update(post);
    }

    public void delete(Post post) throws DaoException {
        postDAO.delete(post);
    }

    public void deleteById(Long id) throws DaoException {
        Post post = postDAO.findById(id);
        postNullValidator(post);
        postDAO.delete(post);
    }

    public List<Post> findAll() throws DaoException {
        return postDAO.findAll();
    }

    private void postNullValidator(Post post) throws ServiceException {
        if (post == null) throw new BadRequestException("Post does not exist in method" +
                " postNullValidator(Post post) from class " +
                PostService.class.getName());
    }
}
