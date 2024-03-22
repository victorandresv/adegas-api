package adegas.fago.helpers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtHelper {

    private String publicKey;
    private String privateKey;
    private String oid;
    public JwtHelper(String oid, String publicKey, String privateKey){
        this.oid = oid;
        this.publicKey = publicKey;
        this.privateKey = privateKey;

    }
    public String GetToken(){
        return Jwts.builder()
                .claim("oid", oid)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 1000*60*10))
                .signWith(SignatureAlgorithm.HS512, privateKey.getBytes())
                .compact();
    }
}
