package adegas.fago.helpers;

import java.util.Map;
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
}
