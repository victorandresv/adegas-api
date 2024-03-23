package adegas.fago;

import adegas.fago.helpers.GenKeyHelper;
import io.jsonwebtoken.Jwts;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.Map;

@Configuration
@EnableWebMvc
public class ConfigApplication implements WebMvcConfigurer
{

    @Bean
    public HttpInterceptors interceptor(){
        return new HttpInterceptors();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        Map<String, Object> keys = GenKeyHelper.GenerateKeys();
        PrivateKey privateKey = (PrivateKey)keys.get("PrivateKey");
        PublicKey publicKey = (PublicKey)keys.get("PublicKey");

        System.out.println(GenKeyHelper.GetBase64Key(publicKey));
        System.out.println(GenKeyHelper.GetBase64Key(privateKey));

        String pk = GenKeyHelper.GetBase64Key(privateKey);
        PrivateKey npk = GenKeyHelper.GetPrivateKey(pk);

        String jwt = Jwts.builder()
                .claim("oid", "1234567890")
                .claim("cid", "0987654321")
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + 1000 * 60 * 10))
                .signWith(npk)
                .compact();

        System.out.println(new String(jwt.getBytes()));

        try {
            Jwts.parser().verifyWith(publicKey).build().parse(new String(jwt.getBytes()));
            System.out.println("Verified");
        } catch (Exception err){
            System.out.println("Invalid");
            System.out.println(err.getMessage());
        }

        //registry.addInterceptor(interceptor());
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
