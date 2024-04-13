package adegas.fago.models;

public class DayResume {
    private String phone;
    private String address;
    private String typePayment;
    private float value;
    private Integer[] quantities;

    public Integer[] getQuantities() {
        return quantities;
    }

    public float getValue() {
        return value;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getTypePayment() {
        return typePayment;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setQuantities(Integer[] quantities) {
        this.quantities = quantities;
    }

    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "DayResume{" +
                "phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", typePayment='" + typePayment + '\'' +
                ", value=" + value +
                ", quantities=" + quantities +
                '}';
    }
}
