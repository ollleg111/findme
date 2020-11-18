package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Post;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
public class PostService {
    private PostDAO postDAO;
    private Post post;
    private RelationshipService relationshipService;

    @Autowired
    public RelationshipService getRelationshipService() {
        return relationshipService;
    }

    @Autowired
    public Post getPost() {
        return post;
    }

    @Autowired
    public PostService(PostDAO postDAO) {
        this.postDAO = postDAO;
    }

//    @Autowired
//    public PostService(PostDAO postDAO, UserService userService, RelationshipService relationshipService) {
//        this.postDAO = postDAO;
//        this.userService = userService;
//        this.relationshipService = relationshipService;
//    }

    public Post findById(Long id) throws DaoException, NotFoundException {
        Post post = postDAO.findById(id);
        if (post == null) throw
                new NotFoundException("Post does not exist in method findById(Long id) from class " +
                        PostService.class.getName());
        return post;
    }

    public Post save(Post post) throws DaoException, BadRequestException {
        validate(post);
        return postDAO.save(post);
    }

    public Post update(Post post) throws DaoException {
        validate(post);
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

    private void validate(Post post) throws BadRequestException {
        Relationship relationship = getRelationshipService().getRelationship(
                post.getUserPosted().getId(),
                post.getUserPagePosted().getId());

        /*
        считаем что пользователь может оставлять посты на страницах всех людей с которыми он в друзьях, а так же у себя
         */
        if ((relationship == null || !relationship.getRelationshipStatus().equals(RelationshipStatus.FRIENDS)) &&
                !post.getUserPosted().equals(post.getUserPagePosted()))
            throw new BadRequestException("You are not friends and don't have permission to publish posts AND" +
                    " you can publish posts to yourself");

        if (post.getMessage().length() == 0)
            throw new BadRequestException("Post's message cannot be empty");

        /*
        максимальная допустимая длина 200 символов
         */
        if (post.getMessage().length() >= Constants.MAX_LENGTH_OF_MESSAGE)
            throw new BadRequestException("Post's message cannot be more than 200 symbols");

        /*
        максимальная допустимая длина 200 символов, а так же запрещаются ссылки
        https://stackoverrun.com/ru/q/2923225
         */
        if (Pattern.compile(Constants.URL_REGEX).matcher(post.getMessage()).find())
            throw new BadRequestException("Post's massage have URLs");

    }
}
