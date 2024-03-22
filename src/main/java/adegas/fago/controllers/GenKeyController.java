package adegas.fago.controllers;

import adegas.fago.helpers.JwtHelper;
import adegas.fago.helpers.QRCodesHelper;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.GenKeyDto;
import adegas.fago.models.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;

@RestController
public class GenKeyController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/access-token/{companyId}/{phone}")
    public ResponseEntity<GenKeyDto> AccessToken(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);

            String publicKey = "";
            String privateKey = "";
            if(user.getPublicKey() != null){
                publicKey = user.getPublicKey();
                privateKey = user.getPrivateKey();
            } else {
                try {
                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                    keyPairGen.initialize(2048);
                    KeyPair pair = keyPairGen.generateKeyPair();
                    Base64.Encoder encoder = Base64.getEncoder();
                    publicKey = encoder.encodeToString(pair.getPublic().getEncoded());
                    privateKey = encoder.encodeToString(pair.getPrivate().getEncoded());

                    user.setPrivateKey(privateKey);
                    user.setPublicKey(publicKey);

                    repository.save(user);
                } catch (Exception ignored) { }
            }
            if(!publicKey.isEmpty() && !privateKey.isEmpty()){

                JwtHelper jwtHelper = new JwtHelper(user.getID(), publicKey, privateKey);
                GenKeyDto genKeyDto = new GenKeyDto();
                genKeyDto.setJwt(jwtHelper.GetToken());
                return new ResponseEntity<>(genKeyDto, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(new GenKeyDto(), HttpStatus.OK);

    }

    @GetMapping("/access-qr-code/{companyId}/{phone}")
    public ResponseEntity<GenKeyDto> AccessQrCode(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);

            String publicKey = "";
            String privateKey = "";
            if(user.getPublicKey() != null){
                publicKey = user.getPublicKey();
                privateKey = user.getPrivateKey();
            } else {
                try {
                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                    keyPairGen.initialize(2048);
                    KeyPair pair = keyPairGen.generateKeyPair();
                    Base64.Encoder encoder = Base64.getEncoder();
                    publicKey = encoder.encodeToString(pair.getPublic().getEncoded());
                    privateKey = encoder.encodeToString(pair.getPrivate().getEncoded());

                    user.setPrivateKey(privateKey);
                    user.setPublicKey(publicKey);

                    repository.save(user);
                } catch (Exception ignored) { }
            }
            if(!publicKey.isEmpty() && !privateKey.isEmpty()){

                JwtHelper jwtHelper = new JwtHelper(user.getID(), publicKey, privateKey);

                String base64Image = QRCodesHelper.GenerateAztec(jwtHelper.GetToken());
                GenKeyDto genKeyDto = new GenKeyDto();
                genKeyDto.setQrCode(base64Image);
                return new ResponseEntity<>(genKeyDto, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(new GenKeyDto(), HttpStatus.OK);

    }

}
