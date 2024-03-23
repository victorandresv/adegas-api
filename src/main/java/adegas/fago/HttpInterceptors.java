package adegas.fago;

import adegas.fago.interfaces.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptors implements HandlerInterceptor {

    @Autowired
    private UserRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwt = request.getHeader("Authorization");
        if(jwt == null){
            response.setStatus(401);
            return false;
        }
/*
        jwt = jwt.replace("Bearer", "");
        jwt = jwt.trim();
        JwtHelper jwtHelper = new JwtHelper(jwt);
        JSONObject jsonObject = jwtHelper.GetTokenData();
        if(jsonObject != null){

            if(TimestampHelper.IsTimestampIsExpired(jsonObject.getLong("exp"))){
                response.setStatus(401);
                return false;
            }

            String oid = jsonObject.getString("oid");
            UserCollection user = repository.findOneById(oid);
            jwtHelper.IsValidToken(user.getPublicKey());

        }
 */

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
