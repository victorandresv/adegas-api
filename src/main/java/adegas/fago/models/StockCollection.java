package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "stocks")
public class StockCollection {
    @Id
    private String ID;
    private String companyId;
    private String jailId;
    private String state;
    private int t5K;
    private int t11K;
    private int t15K;
    private int t45K;
    private int aluminio;

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public void setAluminio(int aluminio) {
        this.aluminio = aluminio;
    }

    public void setT45K(int t45K) {
        this.t45K = t45K;
    }

    public void setT15K(int t15K) {
        this.t15K = t15K;
    }

    public void setT11K(int t11K) {
        this.t11K = t11K;
    }

    public void setT5K(int t5K) {
        this.t5K = t5K;
    }


    public int getAluminio() {
        return aluminio;
    }

    public int getT5K() {
        return t5K;
    }

    public int getT11K() {
        return t11K;
    }

    public int getT15K() {
        return t15K;
    }

    public String getJailId() {
        return jailId;
    }

    public int getT45K() {
        return t45K;
    }

    public void setJailId(String jailId) {
        this.jailId = jailId;
    }

}
