package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.Contact;
import com.zoe.repositories.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ContactService{
    @Autowired
    private ContactRepository contactRepository;

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Contact findById(UUID id) {
        Optional<Contact> contact = contactRepository.findById(id);
        return contact.orElseThrow(() -> new ResourceNotFoundException("Contato não encontrado para o ID: " + id));
    }
    @Transactional
    public Contact insert(Contact contact) {
        return contactRepository.save(contact);
    }
    @Transactional
    public Contact update(UUID id, Contact contact) {
        try {
            Contact entity = contactRepository.getReferenceById(id);
            updateData(entity, contact);
            return contactRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Contato não encontrado para o ID: " + id);
        }
    }

    public void updateData(Contact entity, Contact contact) {
        entity.setFullName(contact.getFullName());
        entity.setPhone(contact.getPhone());
        entity.setDoubt(contact.getDoubt());
    }
    @Transactional
    public void delete (UUID id){
        try{
            contactRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Contato não encontrado para o ID: " + id);
        } catch (DataIntegrityViolationException e){
            throw new DataBaseException("Não é possível excluir o Contato com o ID: "
                    + id
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }
}
