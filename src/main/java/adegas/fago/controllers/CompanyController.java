package adegas.fago.controllers;

import adegas.fago.interfaces.CompanyRepository;
import adegas.fago.models.CompanyCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CompanyController {

    @Autowired
    private CompanyRepository repository;

    @PostMapping(value="/companies")
    public ResponseEntity<CompanyCollection> Create(@RequestBody CompanyCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }
}
