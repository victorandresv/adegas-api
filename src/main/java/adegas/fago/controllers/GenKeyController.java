package adegas.fago.controllers;

import adegas.fago.helpers.QRCodesHelper;
import adegas.fago.models.GenKeyCollection;
import adegas.fago.models.GenKeyDto;
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

    @GetMapping("/gen-key/{companyId}/{phone}")
    public ResponseEntity<GenKeyDto> Generate(@PathVariable String companyId, @PathVariable String phone){
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            GenKeyCollection genKeyCollection = new GenKeyCollection();
            genKeyCollection.setCompanyId(companyId);
            genKeyCollection.setUserId("1234567890");

            Base64.Encoder encoder = Base64.getEncoder();
            String publicKey = encoder.encodeToString(pair.getPublic().getEncoded());
            String privateKey = encoder.encodeToString(pair.getPrivate().getEncoded());

            genKeyCollection.setPublicKey(publicKey);
            genKeyCollection.setPrivateKey(privateKey);

            String base64Image = QRCodesHelper.GenerateAztec(publicKey);

            GenKeyDto GenKeyDto = new GenKeyDto();
            GenKeyDto.setQrCode(base64Image);

            return new ResponseEntity<>(GenKeyDto, HttpStatus.OK);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

}
