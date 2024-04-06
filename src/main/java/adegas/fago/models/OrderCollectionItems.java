package adegas.fago.models;

public class OrderCollectionItems {
    private String paymentType;
    private String product;
    private int quantity;
    private float totalPrice;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public String getProduct() {
        return product;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderCollectionItems{" +
                "paymentType='" + paymentType + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
