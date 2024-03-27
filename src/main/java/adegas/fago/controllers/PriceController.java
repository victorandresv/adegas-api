package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.PricesRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.PriceCollection;
import adegas.fago.models.UserCollection;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class PriceController {

    @Autowired
    private PricesRepository repository;
    @Autowired
    private KeysRepository keyRepository;
    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/price/current")
    public ResponseEntity<PriceCollection> Find(@RequestHeader Map<String, String> headers){
        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, false);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        UserCollection user = userRepository.findOneById(jsonObject.getString("oid"));
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        PriceCollection priceCollection = repository.findCurrentByCompanyId(user.getCompanyId());
        return new ResponseEntity<>(priceCollection, HttpStatus.OK);
    }
}
