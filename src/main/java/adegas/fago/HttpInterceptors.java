package adegas.fago;

import adegas.fago.helpers.GenKeyHelper;
import adegas.fago.interfaces.KeysRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptors implements HandlerInterceptor {

    @Autowired
    private KeysRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String ZenoAuthToken = request.getHeader("ZenoAuthToken");
        if(ZenoAuthToken != null){
            String ZENO_AUTH_TOKEN = System.getenv("ZENO_AUTH_TOKEN");
            if(ZENO_AUTH_TOKEN.equals(ZenoAuthToken)){
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        }

        String jwt = request.getHeader("Authorization");
        if(jwt == null){
            response.setStatus(401);
            return false;
        }

        JSONObject verifyJsonWebToken = GenKeyHelper.VerifyJsonWebToken(jwt, repository);
        if(verifyJsonWebToken == null){
            response.setStatus(401);
            return false;
        }


        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
