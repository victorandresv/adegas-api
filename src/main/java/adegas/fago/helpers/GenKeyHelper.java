package adegas.fago.helpers;

import adegas.fago.interfaces.KeysRepository;
import adegas.fago.models.KeysCollection;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;

public class GenKeyHelper {
    public static Map<String, Object> GenerateKeys(){
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(2048);
            KeyPair pair = keyPairGen.generateKeyPair();
            Map<String, Object> data = new HashMap<>();
            data.put("PublicKey", pair.getPublic());
            data.put("PrivateKey", pair.getPrivate());
            return data;

        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
        return null;
    }

    public static String GetBase64Key(PublicKey key){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }

    public static String GetBase64Key(PrivateKey key){
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(key.getEncoded());
    }

    public static String GetJsonWebToken(String privateKey, String oid, String cid, String rol, int minutes){
        String jwt = Jwts.builder()
                .claim("oid", oid)
                .claim("cid", cid)
                .claim("rol", rol)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 1000 * 60 * minutes))
                .signWith(GenKeyHelper.GetPrivateKey(privateKey))
                .compact();

        return new String(jwt.getBytes());
    }

    public static JSONObject DecodeDataFromJWT(String jwt){
        try {
            jwt = jwt.replace("Bearer", "");
            jwt = jwt.trim();
            String[] jwtArr = jwt.split("\\.");

            byte[] decodedBytes = Base64.getDecoder().decode(jwtArr[1]);
            String decodedString = new String(decodedBytes);

            return new JSONObject(decodedString);
        } catch (Exception ignored){
            return null;
        }
    }

    public static PublicKey GetPublicKey(String b64key){
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(b64key));
            return kf.generatePublic(keySpec);
        } catch (Exception err){
            System.out.println(err.getMessage());
        }
        return null;
    }

    public static PrivateKey GetPrivateKey(String b64key){
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(b64key));
            return kf.generatePrivate(keySpecPKCS8);
        } catch (Exception err){
            System.out.println(err.getMessage());
        }
        return null;
    }

    public static JSONObject VerifyJsonWebToken(String jwt, KeysRepository repository, boolean avoidExp){
        JSONObject jwtData = GenKeyHelper.DecodeDataFromJWT(jwt);
        if(jwtData == null){
            return null;
        }

        String oid = jwtData.getString("oid");
        long exp = jwtData.getLong("exp");
        if(oid.isEmpty()){
            return null;
        }

        if(TimestampHelper.IsTimestampIsExpired(exp) && !avoidExp){
            return null;
        }

        KeysCollection user = repository.findOneByUserId(oid);
        PublicKey publicKey = GenKeyHelper.GetPublicKey(user.getPublicKey());
        try {
            jwt = jwt.replace("Bearer", "");
            jwt = jwt.trim();
            Jwts.parser().verifyWith(publicKey).build().parse(jwt);

            return jwtData;
        } catch (Exception err){
            return null;
        }
    }

    public static String GenerateRandomPass(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = length;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }
}
