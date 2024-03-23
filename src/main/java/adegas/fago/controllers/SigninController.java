package adegas.fago.controllers;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.QRCodesHelper;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.GenKeyDto;
import adegas.fago.models.ResponseModel;
import adegas.fago.models.UserCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SigninController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/generate-qr-code/{companyId}/{phone}")
    public ResponseEntity<ResponseModel> GenerateQrCode(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);

            String jwtString = GenKeyHelper.GetJsonWebToken(user.getPrivateKey(), user.getID(), user.getCompanyId(), 10);

            String base64Image = QRCodesHelper.GenerateAztec(jwtString);

            GenKeyDto genKeyDto = new GenKeyDto();
            genKeyDto.setQrCode(base64Image);

            ResponseModel response = new ResponseModel();
            response.setSuccess(true);
            response.setData(genKeyDto);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }

    @GetMapping("/generate-access-token/{companyId}/{phone}")
    public ResponseEntity<ResponseModel> GenerateAccessToken(@PathVariable String companyId, @PathVariable String phone){

        if(!companyId.isEmpty() && !phone.isEmpty()){
            UserCollection user = repository.findByCompanyIdAndPhone(companyId, phone);

            String jwtString = GenKeyHelper.GetJsonWebToken(user.getPrivateKey(), user.getID(), user.getCompanyId(), 60*12);

            GenKeyDto genKeyDto = new GenKeyDto();
            genKeyDto.setJwt(jwtString);

            ResponseModel response = new ResponseModel();
            response.setSuccess(true);
            response.setData(genKeyDto);

            return new ResponseEntity<>(response, HttpStatus.OK);

        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

    }


}
