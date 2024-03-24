package adegas.fago.helpers;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HeadersHelper {
    public static String GetAccessTokenHeader(Map<String, String> headers){
        AtomicReference<String> bearerToken = new AtomicReference<>("");
        headers.forEach((key, value) -> {
            if(key.equals("authorization")){
                bearerToken.set(value);
            }
        });

        return bearerToken.get();
    }

    public static boolean isZenoAuth(Map<String, String> headers){
        AtomicBoolean isZenoAuth = new AtomicBoolean(false);
        headers.forEach((key, value) -> {
            if(key.equals("zenoauthtoken")){
                if(value.equals(System.getenv("ZENO_AUTH_TOKEN"))){
                    isZenoAuth.set(true);
                }
            }
        });

        if(isZenoAuth.get()){
            return true;
        }
        return false;
    }
}
