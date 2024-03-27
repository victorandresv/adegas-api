package adegas.fago.models;

public class OrderCollectionItems {
    private String paymentType;
    private String product;
    private int quantity;
    private float unitPrice;

    public float getUnitPrice() {
        return unitPrice;
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

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderCollectionItems{" +
                "paymentType='" + paymentType + '\'' +
                ", product='" + product + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
