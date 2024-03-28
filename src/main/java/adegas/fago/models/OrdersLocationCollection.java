package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "orders")
public class OrdersLocationCollection {
    @Id
    private String ID;
    private String orderId;
    private List<Double> latLng;
    private long timeStamp;
    private float bearing;

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getID() {
        return ID;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<Double> getLatLng() {
        return latLng;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public void setLatLng(List<Double> latLng) {
        this.latLng = latLng;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "OrdersLocationCollection{" +
                "ID='" + ID + '\'' +
                ", orderId='" + orderId + '\'' +
                ", latLng=" + latLng +
                ", timeStamp=" + timeStamp +
                ", bearing=" + bearing +
                '}';
    }
}
