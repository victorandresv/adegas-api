package adegas.fago.controllers;

import adegas.fago.interfaces.StockRepository;
import adegas.fago.models.StockCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StockController {

    @Autowired
    private StockRepository repository;

    @PutMapping(value="/stocks")
    public ResponseEntity<StockCollection> Set(@RequestBody StockCollection payload){
        StockCollection data = repository.findOneBy(payload.getCompanyId(), payload.getJailId(), payload.getState());
        if(data != null){
            payload.setID(data.getID());
        }
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @GetMapping("/stocks/{companyId}/{jailId}")
    public ResponseEntity<List<StockCollection>> Find(@PathVariable String companyId, @PathVariable String jailId){
        List<StockCollection> list = repository.findByCompanyIdAndJailId(companyId, jailId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/stocks/{companyId}/{jailId}/{state}")
    public ResponseEntity<StockCollection> Find(@PathVariable String companyId, @PathVariable String jailId, @PathVariable String state){
        StockCollection data = repository.findOneBy(companyId, jailId, state);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
