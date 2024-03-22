package adegas.fago.controllers;

import adegas.fago.interfaces.JailRepository;
import adegas.fago.models.JailCollection;
import adegas.fago.models.PriceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JailController {

    @Autowired
    private JailRepository repository;

    @PostMapping(value="/jails")
    public ResponseEntity<JailCollection> Create(@RequestBody JailCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @GetMapping("/jails/{companyId}")
    public ResponseEntity<List<JailCollection>> Find(@PathVariable String companyId){
        List<JailCollection> list = repository.findByCompanyId(companyId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
