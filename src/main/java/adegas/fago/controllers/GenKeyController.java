package adegas.fago.controllers;

import adegas.fago.helpers.QRCodesHelper;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.GenKeyCollection;
import adegas.fago.models.GenKeyDto;
import adegas.fago.models.StockCollection;
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

    @GetMapping("/gen-key/{companyId}/{phone}")
    public ResponseEntity<GenKeyDto> Generate(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);

            String key = "";
            if(user.getPublicKey() != null){
                key = user.getPublicKey();
            } else {
                try {
                    KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
                    keyPairGen.initialize(2048);
                    KeyPair pair = keyPairGen.generateKeyPair();
                    Base64.Encoder encoder = Base64.getEncoder();
                    String publicKey = encoder.encodeToString(pair.getPublic().getEncoded());
                    String privateKey = encoder.encodeToString(pair.getPrivate().getEncoded());

                    user.setPrivateKey(privateKey);
                    user.setPublicKey(publicKey);

                    repository.save(user);

                    key = publicKey;
                } catch (Exception ignored) { }
            }
            if(!key.isEmpty()){
                String base64Image = QRCodesHelper.GenerateAztec(key);
                GenKeyDto GenKeyDto = new GenKeyDto();
                GenKeyDto.setQrCode(base64Image);
                return new ResponseEntity<>(GenKeyDto, HttpStatus.OK);
            }

        }

        return new ResponseEntity<>(new GenKeyDto(), HttpStatus.OK);

    }

}
