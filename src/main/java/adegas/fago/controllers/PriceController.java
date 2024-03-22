package adegas.fago.controllers;

import adegas.fago.interfaces.PricesRepository;
import adegas.fago.models.PriceCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PriceController {

    @Autowired
    private PricesRepository repository;

    @PostMapping(value="/prices")
    public ResponseEntity<PriceCollection> Create(@RequestBody PriceCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @GetMapping("/prices/{companyId}")
    public ResponseEntity<List<PriceCollection>> Find(@PathVariable String companyId){
        List<PriceCollection> list = repository.findByCompanyId(companyId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
