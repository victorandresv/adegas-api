package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserCollection {
    @Id
    private String ID;
    private boolean isWorking;
    private LastLocationModel lastLatLng;
    private String name;
    private String phone;
    private String rol;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public LastLocationModel getLastLatLng() {
        return lastLatLng;
    }

    public void setWorking(boolean working) {
        isWorking = working;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setLastLatLng(LastLocationModel lastLatLng) {
        this.lastLatLng = lastLatLng;
    }

    public String getRol() {
        return rol;
    }

    public String getPhone() {
        return phone;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }
}
