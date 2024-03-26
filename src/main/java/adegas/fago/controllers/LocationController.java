package adegas.fago.controllers;

import adegas.fago.interfaces.LocationRepository;
import adegas.fago.models.LocationCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @PostMapping(value="/location")
    public ResponseEntity<LocationCollection> Create(@RequestBody LocationCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }
}
