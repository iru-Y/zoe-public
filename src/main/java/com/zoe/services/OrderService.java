package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.Order;
import com.zoe.models.Senders;
import com.zoe.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(UUID id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ResourceNotFoundException("Ordem de pedido não encontrado para o ID: " + id));
    }
    @Transactional
    public Order insert(Order order) {
        return orderRepository.save(order);
    }
    @Transactional
    public Order update(UUID id, Order order) {
        try {
            Order entity = orderRepository.getReferenceById(id);
            updateData(entity, order);
            return orderRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Ordem de pedido não encontrado para o ID: " + id);
        }
    }
    public void updateData(Order entity, Order order) {
            entity.setStatus(order.getStatus());
    }
    @Transactional
    public void delete (UUID id){
        try{
            orderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Ordem de pedido não encontrado para o ID: " + id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Não é possível excluir a ordem de pedido com o ID: "
                    + id
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }

    public Order findByTracker(String tracker) {
        Optional<Order> optionalOrder = Optional.ofNullable(orderRepository.findByTracker(tracker));
        if (optionalOrder.isEmpty()) {
            throw new ResourceNotFoundException("Não encontrado, insira um tracker válido");
        }
        return optionalOrder.get();
    }

    @Transactional
    public void deleteByTracker (String tracker){
        try{
            orderRepository.deleteByTracker(tracker);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Ordem de pedido não encontrado para o rastreio: " + tracker);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Não é possível excluir a ordem de pedido com o rastreio: "
                    + tracker
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }
}
