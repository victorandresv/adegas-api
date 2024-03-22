package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "prices")
public class PriceCollection {

    @Id
    private String ID;
    private String companyId;
    private double t5K;
    private double t11K;
    private double t15K;
    private double t45K;
    private double aluminio;
    private Date timestamp;

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public double getAluminio() {
        return aluminio;
    }

    public double getT5K() {
        return t5K;
    }

    public double getT11K() {
        return t11K;
    }

    public double getT15K() {
        return t15K;
    }

    public double getT45K() {
        return t45K;
    }

    public void setAluminio(double aluminio) {
        this.aluminio = aluminio;
    }

    public void setT5K(double t5K) {
        this.t5K = t5K;
    }

    public void setT11K(double t11K) {
        this.t11K = t11K;
    }

    public void setT15K(double t15K) {
        this.t15K = t15K;
    }

    public void setT45K(double t45K) {
        this.t45K = t45K;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
