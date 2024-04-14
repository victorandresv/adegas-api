package adegas.fago.models;

public class DayResume {
    private String phone;
    private float total;
    private String paymentType;
    private int quantity;
    private String discountCode;
    private String product;

    public void setTotal(float total) {
        this.total = total;
    }

    public String getProduct() {
        return product;
    }

    public float getTotal() {
        return total;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "DayResume{" +
                "phone='" + phone + '\'' +
                ", total=" + total +
                ", paymentType='" + paymentType + '\'' +
                ", quantity=" + quantity +
                ", discountCode='" + discountCode + '\'' +
                ", product='" + product + '\'' +
                '}';
    }
}
