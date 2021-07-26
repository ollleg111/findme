package com.findme.service;

import com.findme.dao.MessageDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
import com.findme.models.Message;
import com.findme.models.Relationship;
import com.findme.models.RelationshipStatus;
import com.findme.util.Constants;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageDAO messageDAO;
    private final RelationshipService relationshipService;

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
        if(message.getText() != null && message.getText().length() < Constants.MAX_SYMBOL_QUANTITY)
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
        if(message.getText() != null && message.getText().length() < Constants.MAX_SYMBOL_QUANTITY)
            throw new BadRequestException("Message can not be more than 140 symbols");

        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);
        message.setDateEdited(new Date());
        return messageDAO.update(message);
    }

    public Message updateDateRead(Message message) throws DaoException {
        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);
        message.setDateRead(new Date());
        return messageDAO.update(message);
    }

    public Message updateDateDelete(Message message) throws DaoException {
        /*
        Также должна быть возможность удалить любое свое сообщение с переписки
         */
        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);
        message.setDateDeleted(new Date());
        return messageDAO.update(message);
    }

    public void updateAllMessagesToUser(Long userFromId, Long userToId) throws DaoException {
        /*
        Или удалить всю переписку сразу
         */
        messageDAO.updateAllMessagesToUser(userFromId, userToId);
    }

    public void updateListMessagesToUser(List<Message> messages) throws DaoException {
        /*
        Также должна быть возможность удалить любое свое сообщение с переписки (или несколько, максимум 10 за раз)
         */
        if(messages.size() > Constants.MESSAGE_LIST_SIZE)
            throw new BadRequestException("You can update not more than ten messages");
        List<Long> messageIDList = new ArrayList<>();
        for(Message message : messages){
            Message m = findById(message.getId());
            updateValidation(m, message);
            messageIDList.add(m.getId());
        }
        messageDAO.updateListMessages(messageIDList);
    }
    /*
    Залогиненный пользователь может открывать любую из истории своих переписок и видеть ее сообщения.
     */
    public List<Message> findMessagesToUserId(Long userFromId, Long userToId, Integer index){
        List<Message> messageList = messageDAO.getMessages(userFromId, userToId, index);

        return null;
    }

    public void delete(Message message) throws DaoException {
        Message updatingMessage = findById(message.getId());
        updateValidation(message , updatingMessage);
        messageDAO.delete(message);
    }

     /*
    Пользователь который отправил сообщение может его редактировать и удалять, если оно не было прочитано
     */
    private void updateValidation(Message message, Message updatingMessage) throws BadRequestException {
        /*
        Пользователь который отправил сообщение
         */
        if(updatingMessage.getUserFrom().getId().equals(message.getUserFrom().getId())) throw
                new BadRequestException("User do not have permissions to update or delete this message");

        /*
        если оно не было прочитано
         */
        if(message.getDateRead() != null) throw
                new BadRequestException("Message has been reading. You can not change this message");
    }
}
