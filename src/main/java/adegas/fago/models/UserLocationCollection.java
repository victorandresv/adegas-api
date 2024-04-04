package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;

@Document(collection = "location_users")
public class UserLocationCollection {
    @Id
    private String ID;
    private String userId;
    private double[] latLng;
    private float bearing;
    private float speed;
    private long timestamp;

    public String getUserId() {
        return userId;
    }

    public double[] getLatLng() {
        return latLng;
    }

    public float getBearing() {
        return bearing;
    }

    public float getSpeed() {
        return speed;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public void setLatLng(double[] latLng) {
        this.latLng = latLng;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UserLocationCollection{" +
                "ID='" + ID + '\'' +
                ", userId='" + userId + '\'' +
                ", latLng=" + Arrays.toString(latLng) +
                ", bearing=" + bearing +
                ", speed=" + speed +
                ", timestamp=" + timestamp +
                '}';
    }
}
