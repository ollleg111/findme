package com.findme.service;

import com.findme.dao.MessageDAO;
import com.findme.exceptions.BadRequestException;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.ServiceException;
import com.findme.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private MessageDAO messageDAO;

    @Autowired
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    public Message findById(Long id) throws DaoException {
        Message message = messageDAO.findById(id);
        messageNullValidator(message);
        return messageDAO.findById(id);
    }

    public Message save(Message message) throws DaoException {
        return messageDAO.save(message);
    }

    public Message update(Message message) throws DaoException {
        return messageDAO.update(message);
    }

    public void delete(Message message) throws DaoException {
        messageDAO.delete(message);
    }

    public void deleteById(Long id) throws DaoException {
        Message message = messageDAO.findById(id);
        messageNullValidator(message);
        messageDAO.delete(message);
    }

    public List<Message> findAll() throws DaoException {
        return messageDAO.findAll();
    }

    private void messageNullValidator(Message message) throws ServiceException {
        if (message == null) throw new BadRequestException("Message does not exist in method" +
                " messageNullValidator(Message message) from class " +
                UserService.class.getName());
    }
}
