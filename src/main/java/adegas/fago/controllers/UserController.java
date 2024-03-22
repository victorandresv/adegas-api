package adegas.fago.controllers;

import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping(value="/users")
    public ResponseEntity<UserCollection> Create(@RequestBody UserCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }
}
