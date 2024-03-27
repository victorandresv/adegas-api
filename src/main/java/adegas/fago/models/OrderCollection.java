package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "orders")
public class OrderCollection {
    @Id
    private String ID;
    private String address;
    private List<Double> location;
    private String assignedTo;
    private long createdAt;
    private String name;
    private String phone;
    private String status;
    private ArrayList<OrderCollectionItems> items;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<OrderCollectionItems> getItems() {
        return items;
    }

    public List<Double> getLocation() {
        return location;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public String getAddress() {
        return address;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public String getPhone() {
        return phone;
    }

    public String getStatus() {
        return status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setItems(ArrayList<OrderCollectionItems> items) {
        this.items = items;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "OrderCollection{" +
                "ID='" + ID + '\'' +
                ", address='" + address + '\'' +
                ", location=" + location +
                ", assignedTo='" + assignedTo + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", items=" + items +
                '}';
    }
}
