package adegas.fago.helpers;

import adegas.fago.interfaces.KeysRepository;
import adegas.fago.interfaces.UserRepository;
import adegas.fago.models.UserCollection;
import org.json.JSONObject;

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

    public static boolean LetAccessAdmin(Map<String, String> headers, UserRepository repository, KeysRepository keyRepository, String companyId){
        boolean isZenoAuth = HeadersHelper.isZenoAuth(headers);

        String jwt = HeadersHelper.GetAccessTokenHeader(headers);
        JSONObject jsonObject = GenKeyHelper.VerifyJsonWebToken(jwt, keyRepository, false);
        if(jsonObject == null && !isZenoAuth){
            return false;
        }

        if(!isZenoAuth){
            UserCollection admin = repository.findOneById(jsonObject.getString("oid"));
            if(!admin.getRol().equals("admin")){
                return false;
            }

            if(!admin.getCompanyId().equals(companyId) && !isZenoAuth){
                return true;
            }
        }

        return true;
    }
}
