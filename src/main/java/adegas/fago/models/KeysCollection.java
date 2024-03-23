package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "keys")
public class KeysCollection {
    @Id
    private String ID;
    private String userId;
    private String publicKey;
    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}
