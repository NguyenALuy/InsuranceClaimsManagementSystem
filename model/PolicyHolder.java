/**
 * @author <Nguyen A Luy - S3891919>
 */
package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PolicyHolder extends Customer{
    private ArrayList<Dependent> objDependentList;

    public ArrayList<Dependent> getObjDependentList() {
        return objDependentList;
    }

    public void setObjDependentList(ArrayList<Dependent> objDependentList) {
        this.objDependentList = objDependentList;
    }

    public PolicyHolder(String id, String fullName) {
        super(id, fullName);
        this.objDependentList = new ArrayList<>();
    }

    public PolicyHolder() {
        this.objDependentList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "PolicyHolder{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", insuranceCard=" + getInsuranceCard() +
                ", dependentList=" + getObjDependentList() +
                "}";
    }

    public PolicyHolder addNewPolicyHolder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Policy Holder ID:");
        String pHolderId = scanner.nextLine();
        System.out.println("Enter Policy Holder name:");
        String pHolderName = scanner.nextLine();

        PolicyHolder policyHolder= new PolicyHolder(pHolderId, pHolderName);
        System.out.println("Policy Holder added successfully.");
        return policyHolder;
    }

    public boolean deletePHolderById(ArrayList<PolicyHolder> policyHolders, String pHolderId) {
        Iterator<PolicyHolder> iterator = policyHolders.iterator();
        while (iterator.hasNext()) {
            PolicyHolder policyHolder = iterator.next();
            if (policyHolder.getId().equals(pHolderId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean updatePHolderById(ArrayList<PolicyHolder> policyHolders, String pHolderId) {
        Scanner scan = new Scanner(System.in);
        for(PolicyHolder policyHolder : policyHolders){
            if(policyHolder.getId().equalsIgnoreCase(pHolderId)){
                policyHolder.setId(pHolderId);
                System.out.println("Enter Policy Holder name:");
                String newPHolderName = scan.nextLine();
                policyHolder.setFullName(newPHolderName);
                return true;
            }
        }
        return false;
    }

    public void getPHolderById(ArrayList<PolicyHolder> policyHolders, String pHolderId) {
        for (PolicyHolder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(pHolderId)) {
                System.out.println(policyHolder.toString());
            }
        }
    }

    public void getAllPolicyHolders(ArrayList<PolicyHolder> policyHolders) {
        for(PolicyHolder policyHolder : policyHolders){
            System.out.println(policyHolder.toString());
        }
    }
}
