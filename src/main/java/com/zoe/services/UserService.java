package com.zoe.services;

import com.zoe.exceptions.DataBaseException;
import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.UserModel;
import com.zoe.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //If you want to delete, create or forgot the ADMIN password,
    // please contact the person in charge of the project.
    @Autowired
    private UserRepository userRepository;
    public UserModel update(Long id, UserModel userModel) {
        try {
            UserModel entity = userRepository.getReferenceById(id);
            updateData(entity, userModel);
            return userRepository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado para o ID: " + id);
        }
    }

    public void updateData(UserModel entity, UserModel userModel) {
        entity.setUsername(userModel.getUsername());
        entity.setPassword(userModel.getPassword());
    }
}
