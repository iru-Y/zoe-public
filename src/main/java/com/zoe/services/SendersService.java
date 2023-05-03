package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.Senders;
import com.zoe.repositories.SendersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SendersService {
    @Autowired
    private SendersRepository sendersRepository;

    public List<Senders> findAll() {
        return sendersRepository.findAll();
    }

    public Senders findById(UUID id) {
        Optional<Senders> sign = sendersRepository.findById(id);
        return sign.orElseThrow(() -> new ResourceNotFoundException("Remetente não encontrado para o ID: " +id));
    }
    public Senders insert(Senders sign) {
        return sendersRepository.save(sign);
    }

    public Senders update(UUID id, Senders sign) {
        try {
            Senders entity = sendersRepository.getReferenceById(id);
            updateData(entity, sign);
            return sendersRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Remetente não encontrado para o ID: " +id);
        }
    }

    public void updateData(Senders entity, Senders sign) {
        entity.setPhone(sign.getPhone());
        entity.setCity(sign.getCity());
        entity.setDistrict(sign.getDistrict());
        entity.setStreet(sign.getStreet());
        entity.setNumber(sign.getNumber());
        entity.setAddressLine2(sign.getAddressLine2());
        entity.setLandmark(sign.getLandmark());
        entity.setMail(sign.getMail());
        entity.setProductDescription(sign.getProductDescription());
    }

    public void delete(UUID id) {
        try {
            sendersRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Remetente não encontrado para o ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Não é possível excluir o remetente com o ID: "
                    + id
                    + ". Ele está sendo referenciado por outros registros no banco de dados.");
        }
    }
}
