package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.Recipients;
import com.zoe.repositories.RecipientsRepository;
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
public class RecipientsService {
    @Autowired
    private RecipientsRepository recipientsRepository;

    public List<Recipients> findAll() {
        return recipientsRepository.findAll();
    }

    public Recipients findById(UUID id) {
        Optional<Recipients> sign = recipientsRepository.findById(id);
        return sign.orElseThrow(() -> new ResourceNotFoundException("Destinatário não encontrado para o ID: " + id));
    }
    @Transactional
    public Recipients insert(Recipients sign) {
        return recipientsRepository.save(sign);
    }
    @Transactional
    public Recipients update(UUID id, Recipients sign) {
        try {
            Recipients entity = recipientsRepository.getReferenceById(id);
            updateData(entity, sign);
            return recipientsRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Destinatário não encontrado para o ID: " + id);
        }
    }

    public void updateData(Recipients entity, Recipients sign) {
        entity.setPhone(sign.getPhone());
        entity.setCity(sign.getCity());
        entity.setDistrict(sign.getDistrict());
        entity.setStreet(sign.getStreet());
        entity.setNumber(sign.getNumber());
        entity.setAddressLine2(sign.getAddressLine2());
        entity.setLandmark(sign.getLandmark());
    }
    @Transactional
    public void delete(UUID id) {
        try {
            recipientsRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Destinatário não encontrado para o ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Não é possível excluir o destinatário com o ID: "
                    + id
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }
}
