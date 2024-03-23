package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserRepository repository;

    @PostMapping(value="/users")
    public ResponseEntity<ResponseModel> Create(@RequestBody UserCollection payload){

        ResponseModel response = new ResponseModel();

        UserCollection user = repository.findByCompanyIdAndPhone(payload.getCompanyId(), payload.getPhone());
        if(user == null){

            Map<String, Object> keys = GenKeyHelper.GenerateKeys();
            PublicKey publicKey = (PublicKey)keys.get("PublicKey");
            PrivateKey privateKey = (PrivateKey)keys.get("PrivateKey");
            String stringPublicKey = GenKeyHelper.GetBase64Key(publicKey);
            String stringPrivateKey = GenKeyHelper.GetBase64Key(privateKey);
            payload.setPublicKey(stringPublicKey);
            payload.setPrivateKey(stringPrivateKey);

            repository.save(payload);
            payload.setPrivateKey(null);
            payload.setPublicKey(null);

            response.setSuccess(true);
            response.setData(payload);

        } else {
            response.setSuccess(false);
            response.setMessage("El teléfono ingresado ya existe");
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
