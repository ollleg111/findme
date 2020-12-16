package com.findme.service;

import com.findme.dao.PostDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.models.*;
import com.findme.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

@Service
@Slf4j
public class PostService {

    private PostDAO postDAO;
    private RelationshipService relationshipService;

    // - максимальное колличество постов от друзей
    private final int MAX_FEED_LIST = 10;

    @Autowired
    public RelationshipService getRelationshipService() {
        return relationshipService;
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

    public List<Post> getDataSortedPostsList(User user) throws DaoException {
        return postDAO.getDataSortedPostsList(user.getId());
    }

    /*
        Так же необходимо сделать фильтрацию
        - показвывть все посты (по умолчанию)
        - показывать посты только юзера чья страница
        - показывать посты друзей
        - показывать посты определенного пользователя
     */
    /*
        public class PostFilter {
        boolean ownerPosts;
        boolean friendsPosts;
        long userId;
        }
     */
    public List<Post> getFilteredList(User owner, PostFilter postFilter) throws DaoException {
        if (postFilter != null) {
            List<Post> posts = new ArrayList<>();

            // - показывать посты только юзера чья страница
            if (postFilter.isOwnerPosts()) {
                posts.addAll(postDAO.getFilteredPostsById(owner.getId()));
            }
            // - показывать посты друзей
            if (postFilter.isFriendsPosts()) {
                posts.addAll(postDAO.getFilteredByFriends(owner.getId()));
            }
            // - показывать посты определенного пользователя
            if (postFilter.getUserId() > 0) {
                posts.addAll(postDAO.getFilteredPostsById(postFilter.getUserId()));
            }
            return posts;
        } else {
            // - показвывть все посты (по умолчанию)
            log.info("Show all posts)");
            return findAll();
        }
    }
    public List<Post> getFeedList(User owner) throws DaoException, NotFoundException {
        // - показывать посты друзей, колличество постов ограничено константой
        List<Post> posts = postDAO.getFilteredByFriends(owner.getId());

        if(posts != null) {
            if(posts.size() >= MAX_FEED_LIST) {
                ArrayList<Post> returnList = new ArrayList<>(posts);
                returnList.ensureCapacity(MAX_FEED_LIST);
                return returnList;
            }
            return posts;
        }
        log.info("Do not have any posts)");
        return Collections.emptyList();
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
