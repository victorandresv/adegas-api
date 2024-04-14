package adegas.fago.models;

public class DayResumeItems {
    private String paymentType;
    private int quantity;
    private String discountCode;
    private float value;
    private String product;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "DayResumeItems{" +
                "paymentType='" + paymentType + '\'' +
                ", quantity=" + quantity +
                ", discountCode='" + discountCode + '\'' +
                ", value=" + value +
                ", product='" + product + '\'' +
                '}';
    }
}
