package adegas.fago;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.helpers.TimestampHelper;
import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.KeysCollection;
import adegas.fago.models.UserCollection;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.security.PublicKey;

@Component
public class HttpInterceptors implements HandlerInterceptor {

    @Autowired
    private KeysRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        if(jwt == null){
            response.setStatus(401);
            return false;
        }

        JSONObject jwtData = GenKeyHelper.DecodeDataFromJWT(jwt);
        if(jwtData == null){
            response.setStatus(401);
            return false;
        }

        String oid = jwtData.getString("oid");
        long exp = jwtData.getLong("exp");
        if(oid.isEmpty()){
            response.setStatus(401);
            return false;
        }

        if(TimestampHelper.IsTimestampIsExpired(exp)){
            response.setStatus(401);
            return false;
        }

        KeysCollection user = repository.findOneByUserId(oid);
        PublicKey publicKey = GenKeyHelper.GetPublicKey(user.getPublicKey());
        try {
            jwt = jwt.replace("Bearer", "");
            jwt = jwt.trim();
            Jwts.parser().verifyWith(publicKey).build().parse(jwt);
        } catch (Exception err){
            response.setStatus(500);
            return false;
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
