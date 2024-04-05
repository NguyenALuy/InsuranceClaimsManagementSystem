package lists;

import interfaces.ReadAndWriteFile;
import model.Customer;
import model.Document;
import model.PolicyHolder;
import model.PolicyOwner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class PolicyOwnerList implements ReadAndWriteFile {
    private ArrayList<PolicyOwner> policyOwners;

    public ArrayList<PolicyOwner> getPolicyOwners() {
        return policyOwners;
    }

    public void setPolicyOwners(ArrayList<PolicyOwner> policyOwners) {
        this.policyOwners = policyOwners;
    }

    public PolicyOwnerList() {
        this.policyOwners = new ArrayList<PolicyOwner>();
    }

    public PolicyOwner addNewPolicyOwner() {
        Scanner scanner = new Scanner(System.in);
        String pOwnerId;
        boolean exists;
        do {
            System.out.println("Enter Policy Owner ID:");
            pOwnerId = scanner.nextLine();
            exists = false;
            for (PolicyOwner policyOwner : policyOwners) {
                if (pOwnerId.equalsIgnoreCase(policyOwner.getPolicyOwnerId())) {
                    System.out.println("Policy Owner ID already exists. Please enter a different ID.");
                    exists = true;
                    break;
                }
            }
        } while (exists);

        System.out.println("Enter Policy Owner name:");
        String pOwnerName = scanner.nextLine();

        PolicyOwner policyOwner = new PolicyOwner(pOwnerId, pOwnerName);
        System.out.println("Policy Owner added successfully.");
        return policyOwner;
    }

    public void addPolicyOwnerToList(PolicyOwner policyOwner){
        this.policyOwners.add(policyOwner);
    }

    public boolean deletePOwnerById(String pOwnerId) {
        Iterator<PolicyOwner> iterator = this.policyOwners.iterator();
        while (iterator.hasNext()) {
            PolicyOwner policyOwner = iterator.next();
            if (policyOwner.getPolicyOwnerId().equals(pOwnerId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public PolicyOwner findPOwnerById(String pOwnerId) {
        for (PolicyOwner policyOwner : this.policyOwners) {
            if (policyOwner.getPolicyOwnerId().equals(pOwnerId)) {
                return policyOwner;
            }
        }
        return null;
    }

    public void getAllPOwners() {
        sortPolicyOwnerById();
        for(PolicyOwner policyOwner : this.policyOwners){
            System.out.println(policyOwner.toString());
        }
    }

    public void sortPolicyOwnerById() {
        Collections.sort(policyOwners, new Comparator<PolicyOwner>() {
            @Override
            public int compare(PolicyOwner p1, PolicyOwner p2) {
                String id1 = p1.getPolicyOwnerId().replace("P", "").replace("-", "");
                String id2 = p2.getPolicyOwnerId().replace("P", "").replace("-", "");
                return id1.compareTo(id2);
            }
        });
    }

    @Override
    public void readFromFile() {
        try {
            FileReader reader = new FileReader("files/PolicyOwnerData.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] listInfo = line.split(" # ");
                PolicyOwner policyOwner = new PolicyOwner(listInfo[0], listInfo[1]);
                this.policyOwners.add(policyOwner);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {

    }
    public void printMenuPolicyOwner() {
        System.out.println("====================BANK MENU====================");
        System.out.println("1. ADD NEW POLICY OWNER");
        System.out.println("2. DELETE POLICY OWNER BY ID");
        System.out.println("3. GET POLICY OWNER BY ID");
        System.out.println("4. GET ALL POLICY OWNER");
        System.out.println("5. GET POLICY HOLDER LIST OF POLICY OWNER");
        System.out.println("0. EXIT POLICY OWNER MENU");
    }

    public void doMenuPolicyOwner(){
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            printMenuPolicyOwner();
            System.out.print("Enter your choice: ");
            number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1 -> {
                    PolicyOwner newPolicyOwner = addNewPolicyOwner();
                    addPolicyOwnerToList(newPolicyOwner);
                }
                case 2 -> {
                    System.out.print("Enter Policy Owner ID to delete: ");
                    String pOwnerIdToDelete = scanner.nextLine();
                    boolean deleted = deletePOwnerById(pOwnerIdToDelete);
                    if (deleted) {
                        System.out.println("Policy Owner with ID " + pOwnerIdToDelete + " deleted successfully.");
                    } else {
                        System.out.println("Policy Owner with ID " + pOwnerIdToDelete + " not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Policy Owner ID to search: ");
                    String pOwnerIdToSearch = scanner.nextLine();
                    PolicyOwner foundPolicyOwner = findPOwnerById(pOwnerIdToSearch);
                    if (foundPolicyOwner != null) {
                        System.out.println("Policy Owner found: " + foundPolicyOwner);
                    } else {
                        System.out.println("Policy Owner with ID " + pOwnerIdToSearch + " not found.");
                    }
                }
                case 4 -> {
                    System.out.println("All Policy Owners:");
                    getAllPOwners();
                }
                case 5 ->{
                    System.out.print("Enter Policy Owner ID to search for the Holder List: ");
                    String pOwnerIdToSearch = scanner.nextLine();
                    PolicyOwner foundPolicyOwner = findPOwnerById(pOwnerIdToSearch);
                    System.out.println(foundPolicyOwner.getPolicyOwnerName());
                    for(Customer customer :  foundPolicyOwner.getObjPolicyHolderList().getCustomers()){
                        if(customer instanceof PolicyHolder){
                            System.out.println(customer);
                        }
                    }
                }
                case 0 -> System.out.println("Exiting policy owner menu.");
                default -> System.out.println("Invalid choice. Please enter a number between 0 and 5.");
            }
        } while (number != 0);
    }
}
