package com.zoe.controllers;

import com.zoe.exceptions.ResourceNotFoundException;
import com.zoe.models.UserModel;
import com.zoe.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.Serial;
import java.io.Serializable;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {
    //If you want to delete, create or forgot the ADMIN password,
    // please contact the person in charge of the project.
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserModel> update(@PathVariable Long id, @RequestBody UserModel userModel) {
        userModel = userService.update(id, userModel);
        return ResponseEntity.ok().body(userModel);
    }
}
