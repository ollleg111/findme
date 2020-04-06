package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {
    private PostDAO postDAO;

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

    public Post findById(Long id) throws DaoException, NotFoundException {
        Post post = postDAO.findById(id);
        if (post == null) throw
                new NotFoundException("Post does not exist in method findById(Long id) from class " +
                        PostService.class.getName());
        return post;
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

    public void deleteById(Long id) throws DaoException, NotFoundException {
        Post post = postDAO.findById(id);
        if (post == null) throw
                new NotFoundException("Post does not exist in method postNullValidator(Post post) from class " +
                        PostService.class.getName());
        postDAO.delete(post);
    }

    public List<Post> findAll() throws DaoException {
        return postDAO.findAll();
    }
}
