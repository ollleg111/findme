package com.findme.service;

import com.findme.dao.MessageDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Message;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private MessageDAO messageDAO;
    private RelationshipService relationshipService;

    public Message findById(Long id) throws DaoException, NotFoundException{
        Message message = messageDAO.findById(id);
        if (message == null) throw
                new NotFoundException("Message does not exist in method findById(Long id) from class " +
                UserService.class.getName());
        return message;
    }

    public Message save(Message message) throws DaoException {
        /*
        Максимальная длина сообщения 140 символов
         */
        if(message.getText() != null && message.getText().length() < 140)
            throw new BadRequestException("Message can not be more than 140 symbols");

        Relationship relationship = relationshipService.
                getRelationship(message.getUserFrom().getId(),message.getUserTo().getId());
        if(relationship == null) throw new BadRequestException("Users: " + message.getUserFrom().getFirstName()
                + " and " + message.getUserTo().getFirstName() + " does not have any relationship");

        if(!relationship.getRelationshipStatus().equals(RelationshipStatus.FRIENDS))
            throw new BadRequestException("Users: " + message.getUserFrom().getFirstName()
                    + " and " + message.getUserTo().getFirstName() + " are not friends");

        message.setDateSent(new Date());
        return messageDAO.save(message);
    }

    public Message update(Message message) throws DaoException {
        /*
        Максимальная длина сообщения 140 символов
         */
        if(message.getText() != null && message.getText().length() < 140)
            throw new BadRequestException("Message can not be more than 140 symbols");
        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);

        message.setDateSent(new Date());
        return messageDAO.update(message);
    }

    public void delete(Message message) throws DaoException {
        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);
        messageDAO.delete(message);
    }

    public List<Message> findAll() throws DaoException {
        return messageDAO.findAll();
    }

     /*
    Пользователь который отправил сообщение может его редактировать и удалять, если оно не было прочитано
     */
    private void updateValidation(Message message, Message updatingMessage) throws BadRequestException {
        /*
        Пользователь который отправил
         */
        if(updatingMessage.getUserFrom().getId() != message.getUserFrom().getId()) throw
                new BadRequestException("User do not have permissions to update or delete this message");

        /*
        если оно не было прочитано
         */
        if(message.getDateRead() != null) throw
                new BadRequestException("Message has been reading. You can not change this message");
    }
}
