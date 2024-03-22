package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
public class CompanyCollection {
    @Id
    private String ID;
    private String name;
    private String company_id;

    public String getCompany_id() {
        return company_id;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "CompanyCollection{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", company_id='" + company_id + '\'' +
                '}';
    }
}
