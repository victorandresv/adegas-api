package adegas.fago.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "companies")
public class CompanyCollection {
    @Id
    private String ID;
    private String name;
    private String companyId;

    public String getCompany_id() {
        return companyId;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setCompany_id(String companyId) {
        this.companyId = companyId;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
