package adegas.fago.controllers;

import adegas.fago.interfaces.OrdersRepository;
import adegas.fago.models.OrderCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class OrdersController {
    @Autowired
    private OrdersRepository repository;

    @PostMapping(value="/orders")
    public ResponseEntity<OrderCollection> Create(@RequestBody OrderCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

}
