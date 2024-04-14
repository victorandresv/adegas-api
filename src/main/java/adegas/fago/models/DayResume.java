package adegas.fago.models;

import java.util.List;

public class DayResume {
    private String phone;
    private String address;
    private List<DayResumeItems> items;
    private float total;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public List<DayResumeItems> getItems() {
        return items;
    }

    public void setItems(List<DayResumeItems> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "DayResume{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", items=" + items +
                '}';
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
