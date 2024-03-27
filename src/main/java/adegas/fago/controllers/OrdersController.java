package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.OrdersRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.OrderCollection;
import adegas.fago.models.UserCollection;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrdersController {
    @Autowired
    private OrdersRepository repository;
    @Autowired
    private KeysRepository keyRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(value="/orders")
    public ResponseEntity<OrderCollection> Create(@RequestBody OrderCollection payload){
        repository.save(payload);
        return new ResponseEntity<>(payload, HttpStatus.CREATED);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderCollection>> Get(@RequestHeader Map<String, String> headers){
        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, false);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        UserCollection user = userRepository.findOneById(jsonObject.getString("oid"));
        if(user == null){
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        List<OrderCollection> list = repository.findByCompanyIdJailId(user.getCompanyId(), user.getJailId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
