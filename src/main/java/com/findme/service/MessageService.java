package com.findme.service;

import com.findme.dao.MessageDAO;
import com.findme.exceptions.DaoException;
import com.findme.exceptions.NotFoundException;
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

    public Message findById(Long id) throws DaoException, NotFoundException{
        Message message = messageDAO.findById(id);
        if (message == null) throw
                new NotFoundException("Message does not exist in method findById(Long id) from class " +
                UserService.class.getName());
        return message;
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

    public void deleteById(Long id) throws DaoException, NotFoundException {
        Message message = messageDAO.findById(id);
        if (message == null) throw
                new NotFoundException("Message does not exist in method deleteById(Long id) from class " +
                UserService.class.getName());
        messageDAO.delete(message);
    }

    public List<Message> findAll() throws DaoException {
        return messageDAO.findAll();
    }
}
