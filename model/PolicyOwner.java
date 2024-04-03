package model;

import lists.CustomerList;

import java.util.ArrayList;

public class PolicyOwner{
    private String policyOwnerId;
    private String policyOwnerName;
    private CustomerList objPolicyHolderList;

    public String getPolicyOwnerId() {
        return policyOwnerId;
    }

    public void setPolicyOwnerId(String policyOwnerId) {
        this.policyOwnerId = policyOwnerId;
    }

    public String getPolicyOwnerName() {
        return policyOwnerName;
    }

    public void setPolicyOwnerName(String policyOwnerName) {
        this.policyOwnerName = policyOwnerName;
    }

    public CustomerList getObjPolicyHolderList() {
        return objPolicyHolderList;
    }

    public void setObjPolicyHolderList(CustomerList objPolicyHolderList) {
        this.objPolicyHolderList = objPolicyHolderList;
    }

    public PolicyOwner(String policyOwnerId, String policyOwnerName) {
        this.policyOwnerId = policyOwnerId;
        this.policyOwnerName = policyOwnerName;
        this.objPolicyHolderList = new CustomerList();
    }

    public PolicyOwner() {
        this.policyOwnerId = "Default";
        this.policyOwnerName = "Default";
        this.objPolicyHolderList = new CustomerList();
    }

    @Override
    public String toString() {
        return "PolicyOwner{" +
                "policyOwnerId='" + policyOwnerId + '\'' +
                ", policyOwnerName='" + policyOwnerName + '\'' +
                '}';
    }
}
