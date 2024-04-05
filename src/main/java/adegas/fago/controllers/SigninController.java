package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.HeadersHelper;
import adegas.fago.helpers.QRCodesHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.KeysCollection;
import adegas.fago.models.ResponseModel;
import adegas.fago.models.UserCollection;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class SigninController {

    @Autowired
    private UserRepository repository;
    @Autowired
    private KeysRepository keyRepository;

    @GetMapping("/auth/qr-code/{companyId}/{phone}")
    public ResponseEntity<ResponseModel> AuthQrCode(@PathVariable String companyId, @PathVariable String phone, @RequestHeader Map<String, String> headers){

        if(!companyId.isEmpty() && !phone.isEmpty()){

            if(!HeadersHelper.LetAccessAdmin(headers, repository, keyRepository, companyId)){
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }

            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);
            if(user == null){
                ResponseModel response = new ResponseModel();
                response.setSuccess(false);
                response.setMessage("Usuario no encontrado");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }

            KeysCollection keysCollection = keyRepository.findOneByUserId(user.getID());
            String jwtString = GenKeyHelper.GetJsonWebToken(keysCollection.getPrivateKey(), user.getID(), user.getCompanyId(), user.getRol(), 20);
            String base64Image = QRCodesHelper.GenerateAztec(jwtString);

            ResponseModel response = new ResponseModel();
            response.setSuccess(true);
            response.setData(base64Image);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/auth/access-token/{companyId}/{phone}")
    public ResponseEntity<ResponseModel> AuthAccessToken(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);
            if(user == null){
                ResponseModel response = new ResponseModel();
                response.setSuccess(false);
                response.setMessage("Usuario no encontrado");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            KeysCollection keysCollection = keyRepository.findOneByUserId(user.getID());

            String jwtString = GenKeyHelper.GetJsonWebToken(keysCollection.getPrivateKey(), user.getID(), user.getCompanyId(), user.getRol(), 60*4);

            ResponseModel response = new ResponseModel();
            response.setSuccess(true);
            response.setData(jwtString);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }

        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/auth/access-token")
    public ResponseEntity<ResponseModel> AuthAccessToken(@RequestHeader Map<String, String> headers){

        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, false);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        UserCollection user = repository.findOneById(jsonObject.getString("oid"));
        KeysCollection key = keyRepository.findOneByUserId(jsonObject.getString("oid"));
        String jwtString = GenKeyHelper.GetJsonWebToken(key.getPrivateKey(), user.getID(), user.getCompanyId(), user.getRol(), 60*4);

        ResponseModel response = new ResponseModel();
        response.setSuccess(true);
        response.setData(jwtString);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @GetMapping("/auth/refresh-token")
    public ResponseEntity<ResponseModel> AuthRefreshToken(@RequestHeader Map<String, String> headers){

        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, true);
        if(jsonObject == null){
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        UserCollection user = repository.findOneById(jsonObject.getString("oid"));

        if(user.isActive()){
            KeysCollection key = keyRepository.findOneByUserId(jsonObject.getString("oid"));
            String jwtString = GenKeyHelper.GetJsonWebToken(key.getPrivateKey(), user.getID(), user.getCompanyId(), user.getRol(), 60*4);

            ResponseModel response = new ResponseModel();
            response.setSuccess(true);
            response.setData(jwtString);

            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/auth/phone/{phone}")
    public ResponseEntity<ResponseModel> AuthByPhone(@PathVariable String phone){

        ResponseModel response = new ResponseModel();
        UserCollection user = repository.findOneByPhone(phone);
        if(user == null){
            response.setSuccess(false);
            response.setMessage("El número de celular ingresado no se encuentra autorizado para iniciar sesión");
        } else {
            if(!user.isActive()){
                response.setSuccess(false);
                response.setMessage("El número de celular ingresado se encuentra desactivado para iniciar sesión");
            } else if(user.isLoggedInByPhone()){
                response.setSuccess(false);
                response.setMessage("No es posible iniciar sesión con el número de celular ingresado");
            } else {
                KeysCollection key = keyRepository.findOneByUserId(user.getID());
                String jwtString = GenKeyHelper.GetJsonWebToken(key.getPrivateKey(), user.getID(), user.getCompanyId(), user.getRol(), 60*4);

                user.setLoggedInByPhone(true);
                repository.save(user);

                response.setSuccess(true);
                response.setData(jwtString);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


}
