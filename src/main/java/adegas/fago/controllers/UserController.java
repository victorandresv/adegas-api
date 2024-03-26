package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.KeysCollection;
import adegas.fago.models.ResponseModel;
import adegas.fago.models.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private KeysRepository keyRepository;

    @PostMapping(value="/users")
    public ResponseEntity<ResponseModel> Create(@RequestBody UserCollection payload, @RequestHeader Map<String, String> headers){

        ResponseModel response = new ResponseModel();

        if(!HeadersHelper.LetAccessAdmin(headers, repository, keyRepository, payload.getCompanyId())){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        UserCollection user = repository.findByCompanyIdAndPhone(payload.getCompanyId(), payload.getPhone());
        if(user == null){

            payload.setActive(true);
            repository.save(payload);
            user = repository.findByCompanyIdAndPhone(payload.getCompanyId(), payload.getPhone());

            Map<String, Object> keys = GenKeyHelper.GenerateKeys();
            PublicKey publicKey = (PublicKey)keys.get("PublicKey");
            PrivateKey privateKey = (PrivateKey)keys.get("PrivateKey");
            String stringPublicKey = GenKeyHelper.GetBase64Key(publicKey);
            String stringPrivateKey = GenKeyHelper.GetBase64Key(privateKey);

            KeysCollection keysCollection = new KeysCollection();
            keysCollection.setUserId(user.getID());
            keysCollection.setPublicKey(stringPublicKey);
            keysCollection.setPrivateKey(stringPrivateKey);

            keyRepository.save(keysCollection);

            response.setSuccess(true);
            response.setData(payload);

        } else {
            response.setSuccess(false);
            response.setMessage("El número de celular ingresado ya existe");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/users/{companyId}")
    public ResponseEntity<List<UserCollection>> Find(@PathVariable String companyId){
        List<UserCollection> list = repository.findByCompanyId(companyId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
