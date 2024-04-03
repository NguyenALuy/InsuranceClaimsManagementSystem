package model;

import lists.ClaimList;

import java.util.ArrayList;

public abstract class Customer {
    private String id;
    private String fullName;
    private InsuranceCard insuranceCard;
    private ClaimList objClaimList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public InsuranceCard getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(InsuranceCard insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public ClaimList getObjClaimList() {
        return objClaimList;
    }

    public void setObjClaimList(ClaimList objClaimList) {
        this.objClaimList = objClaimList;
    }

    public Customer(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.insuranceCard = null;
        this.objClaimList = new ClaimList();
    }

    public Customer() {
        this.id = "Default";
        this.fullName = "Default";
        this.insuranceCard = null;
        this.objClaimList = new ClaimList();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", insuranceCard=" + insuranceCard +
                '}';
    }

}
