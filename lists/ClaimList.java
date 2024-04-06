/**
 * @author <Nguyen A Luy - S3891919>
 */
package lists;

import interfaces.ClaimProcessManager;
import interfaces.ReadAndWriteFile;
import model.*;
import java.io.*;
import java.util.*;

public class ClaimList implements ClaimProcessManager, ReadAndWriteFile {
    private ArrayList<Claim> claims;
    private InsuranceCardList insuranceCardList;
    private CustomerList customerList;
    private BankInfoList bankInfoList;
    private DocumentList documentList;

    public ArrayList<Claim> getClaims() {
        return claims;
    }

    public void setClaims(ArrayList<Claim> claims) {
        this.claims = claims;
    }

    public ClaimList() {
        this.claims = new ArrayList<Claim>();
    }

    public ClaimList(InsuranceCardList insuranceCardList, CustomerList customerList, BankInfoList bankInfoList, DocumentList documentList) {
        this.customerList = customerList;
        this.insuranceCardList = insuranceCardList;
        this.bankInfoList = bankInfoList;
        this.documentList = documentList;
        this.claims = new ArrayList<Claim>();
    }

    @Override
    public Claim addNewClaim() {
        Scanner scan = new Scanner(System.in);
        boolean success = false;
        Claim claim = null;

        do {
            System.out.println("Enter Claim Id: ");
            String claimId = scan.nextLine();
            boolean claimIdExists = false;
            for (Claim existingClaim : claims) {
                if (existingClaim.getClaimId().equalsIgnoreCase(claimId)) {
                    claimIdExists = true;
                    System.out.println("Claim Id already exists. Please enter a different Claim Id.");
                    break;
                }
            }
            if (claimIdExists) {
                continue;
            }

            System.out.println("Enter Claim Date: ");
            String claimDate = scan.nextLine();
            System.out.println("Enter Card Number: ");
            String cardNumber = scan.nextLine();
            boolean cardNumberExists = false;
            for (InsuranceCard insuranceCard : insuranceCardList.getInsuranceCards()) {
                if (insuranceCard.getCardNumber().equalsIgnoreCase(cardNumber)) {
                    cardNumberExists = true;
                    break;
                }
            }
            if (!cardNumberExists) {
                System.out.println("Card Number does not exist. Please enter a valid Card Number.");
                continue;
            }

            System.out.println("Enter Exam Date: ");
            String examDate = scan.nextLine();
            System.out.println("Enter Claim Amount: ");
            int claimAmount = Integer.parseInt(scan.nextLine());
            System.out.println("Enter Status (New/Processing/Done): ");
            String status = scan.nextLine();

            BankInfo bankinfo = null;
            do {
                System.out.println("Enter Bank Number:");
                String bankNum = scan.nextLine();

                boolean bankExists = false;
                for (BankInfo bankInfo : bankInfoList.getBankInfos()) {
                    if (bankInfo.getBankNumber().equalsIgnoreCase(bankNum)) {
                        bankinfo = bankInfo;
                        bankExists = true;
                        break;
                    }
                }

                if (!bankExists) {
                    System.out.println("Bank Number does not exist. Please enter a valid Bank Number.");
                }
            } while (bankinfo == null);

            claim = new Claim(claimId, claimDate, cardNumber, examDate, status, claimAmount);
            InsuranceCard insuranceCard = insuranceCardList.findInsuranceCardByNumber(cardNumber);
            insuranceCard.getCustomer().getObjClaimList().getClaims().add(claim);
            claim.setInsuredPerson(insuranceCard.getCustomer());
            claim.setReceiverBankingInfo(bankinfo);
            for(Document document : documentList.getDocuments()){
                if(document.getClaimId().equalsIgnoreCase(claimId)){
                    claim.getObjDocumentList().addDocumentToList(document);
                }
            }
            System.out.println("Claim added Successfully");
            success = true;

        } while (!success);

        return claim;
    }


    @Override
    public boolean updateClaimById(String claimId) {
        Scanner scan = new Scanner(System.in);
        for(Claim claim : this.claims){
            if(claim.getClaimId().equalsIgnoreCase(claimId)){
                claim.setClaimId(claimId);
                System.out.println("Enter New Claim Date (DD/MM/YYYY): ");
                String newDate = scan.nextLine();
                claim.setFormattedClaimDate(newDate);

                InsuranceCard newCard = null;
                do {
                    System.out.println("Enter New Card Number: ");
                    String newCardNumber = scan.nextLine();

                    boolean cardExists = false;
                    for (InsuranceCard insuranceCard : insuranceCardList.getInsuranceCards()) {
                        if (insuranceCard.getCardNumber().equalsIgnoreCase(newCardNumber)) {
                            newCard = insuranceCard;
                            cardExists = true;
                            break;
                        }
                    }

                    if (!cardExists) {
                        System.out.println("Card Number does not exist. Please enter a valid Card Number.");
                    }
                } while (newCard == null);
                claim.setCardNumber(newCard.getCardNumber());
                claim.setInsuredPerson(newCard.getCustomer());

                BankInfo bankinfo = null;
                do {
                    System.out.println("Enter Bank Number:");
                    String bankNum = scan.nextLine();

                    boolean bankExists = false;
                    for (BankInfo bankInfo : bankInfoList.getBankInfos()) {
                        if (bankInfo.getBankNumber().equalsIgnoreCase(bankNum)) {
                            bankinfo = bankInfo;
                            bankExists = true;
                            break;
                        }
                    }

                    if (!bankExists) {
                        System.out.println("Bank Number does not exist. Please enter a valid Bank Number.");
                    }
                } while (bankinfo == null);
                claim.setReceiverBankingInfo(bankinfo);
                System.out.println("Enter New Exam Date: ");
                String newExamDate = scan.nextLine();
                claim.setFormattedExamDate(newExamDate);
                System.out.println("Enter New Claim Amount: ");
                int newClaimAmount = Integer.parseInt(scan.nextLine());
                claim.setClaimAmount(newClaimAmount);
                System.out.println("Enter New Status (New/Processing/Done): ");
                String newStatus = scan.nextLine();
                claim.setStatus(newStatus);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteClaimById(String claimId) {
        Iterator<Claim> iterator = this.claims.iterator();
        while (iterator.hasNext()) {
            Claim claim = iterator.next();
            if (claim.getClaimId().equalsIgnoreCase(claimId)) {
                iterator.remove();
                documentList.getDocuments().removeIf(document -> document.getClaimId().equalsIgnoreCase(claim.getClaimId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public Claim getOneClaim(String claimId) {
        for (Claim claim : this.claims) {
            InsuranceCard insuranceCard = insuranceCardList.findInsuranceCardByNumber(claim.getCardNumber());
            if (insuranceCard != null) {
                if (claim.getClaimId().equalsIgnoreCase(claimId)) {
                    DocumentList claimDocuments = new DocumentList();
                    for (Document document : documentList.getDocuments()) {
                        if (document.getClaimId().equalsIgnoreCase(claimId)) {
                            claimDocuments.addDocumentToList(document);
                        }
                    }
                    claim.setObjDocumentList(claimDocuments);
                    return claim;
                }
            }
        }
        return null;
    }

    @Override
    public void getAllClaims() {
        sortClaimById();
        ArrayList<InsuranceCard> validInsuranceCards = new ArrayList<>();
        for (Claim claim : this.claims) {
            InsuranceCard insuranceCard = insuranceCardList.findInsuranceCardByNumber(claim.getCardNumber());
            if (insuranceCard != null && !validInsuranceCards.contains(insuranceCard)) {
                validInsuranceCards.add(insuranceCard);
            }
        }

        for (InsuranceCard insuranceCard : validInsuranceCards) {
            System.out.println("Insurance Card Number: " + insuranceCard.getCardNumber());
            System.out.println("Customer Name: " + insuranceCard.getCustomer().getFullName());
            System.out.println("Claims:");

            for (Claim claim : insuranceCard.getCustomer().getObjClaimList().getClaims()) {
                System.out.println("Claim: " + claim.toString());
                for (Document document : documentList.getDocuments()) {
                    if (document.getClaimId().equalsIgnoreCase(claim.getClaimId())) {
                        System.out.println("Associated Document: " + document.toString());
                    }
                }
            }
            System.out.println("-----END OF CLAIM-----");
        }
    }

    public void sortClaimById() {
        Collections.sort(claims, new Comparator<Claim>() {
            @Override
            public int compare(Claim claim1, Claim claim2) {
                String id1 = claim1.getClaimId().substring(1);
                String id2 = claim2.getClaimId().substring(1);
                long num1 = Long.parseLong(id1);
                long num2 = Long.parseLong(id2);
                return Long.compare(num1, num2);
            }
        });
    }

    public ClaimList getClaimsOfCustomerByCustomerId(String id) {
        ArrayList<Claim> customerClaims = new ArrayList<>();
        Customer customer = customerList.findCustomerById(id);
        if (customer != null) {
            for (Claim claim : claims) {
                if (claim.getInsuredPerson() != null && claim.getInsuredPerson().getId().equalsIgnoreCase(id)) {
                    customerClaims.add(claim);
                }
            }
        } else {
            System.out.println("Customer with ID " + id + " not found.");
        }
        ClaimList customerClaimList = new ClaimList();
        customerClaimList.setClaims(customerClaims);

        return customerClaimList;
    }


    @Override
    public void readFromFile() {
        ArrayList<String> validLines = new ArrayList<>();
        try (FileReader reader = new FileReader("files/ClaimData.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] listInfo = line.split(" # ");
                if (listInfo.length == 7) {
                    String claimId = listInfo[0];
                    String claimDate = listInfo[1];
                    String insuranceCardNum = listInfo[2];
                    String examDate = listInfo[3];
                    int claimAmount = Integer.parseInt(listInfo[4]);
                    String status = listInfo[5];
                    String bankNumber = listInfo[6];

                    InsuranceCard insuranceCard = insuranceCardList.findInsuranceCardByNumber(insuranceCardNum);
                    if (insuranceCard != null) {
                        Customer customer = insuranceCard.getCustomer();
                        BankInfo bankInfo = bankInfoList.findBankByBankNumber(bankNumber);

                        Claim claim = new Claim(claimId, claimDate, insuranceCardNum, examDate, status, claimAmount);
                        claim.setInsuredPerson(customer);
                        for(Document document : documentList.getDocuments()){
                            if(document.getClaimId().equalsIgnoreCase(claim.getClaimId())){
                                claim.getObjDocumentList().getDocuments().add(document);
                            }
                        }
                        if (customer != null) {
                            customer.getObjClaimList().getClaims().add(claim);
                        }
                        claim.setReceiverBankingInfo(bankInfo);
                        claims.add(claim);
                        validLines.add(line);
                    }
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("files/ClaimData.txt")) {
            for (String validLine : validLines) {
                writer.write(validLine);
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("files/ClaimData.txt");
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            for (Claim claim : claims) {
                String line = String.format("%s # %s # %s # %s # %d # %s # %s",
                        claim.getClaimId(),
                        claim.getFormattedClaimDate(),
                        claim.getCardNumber(),
                        claim.getFormattedExamDate(),
                        claim.getClaimAmount(),
                        claim.getStatus(),
                        claim.getReceiverBankingInfo().getBankNumber());

                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenuClaim(){
        System.out.println("====================CLAIM MENU====================");
        System.out.println("1. ADD NEW CLAIM");
        System.out.println("2. DELETE CLAIM BY NUMBER");
        System.out.println("3. GET CLAIM BY ID");
        System.out.println("4. GET ALL CLAIMS");
        System.out.println("5. UPDATE CLAIM BY CARD NUMBER");
        System.out.println("6. GET CLAIMS OF CUSTOMER BY ID");
        System.out.println("0. EXIT CLAIM MENU");
    }

    public void doMenuClaim(){
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenuClaim();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    Claim newClaim = addNewClaim();
                    if (newClaim != null) {
                        claims.add(newClaim);
                    }
                }
                case 2 -> {
                    System.out.print("Enter Claim ID to delete: ");
                    String claimIdToDelete = scanner.nextLine();
                    boolean deleteResult = deleteClaimById(claimIdToDelete);
                    if (deleteResult) {
                        System.out.println("Claim deleted successfully.");
                    } else {
                        System.out.println("Claim not with ID " +claimIdToDelete + " not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Claim ID to get claim: ");
                    String claimId = scanner.nextLine();
                    Claim claimById = getOneClaim(claimId);
                    if (claimById != null) {
                        System.out.println(claimById);
                    } else {
                        System.out.println("Claim not found for the given ID: "+ claimId);
                    }
                }
                case 4 -> getAllClaims();
                case 5 -> {
                    System.out.print("Enter Claim ID to update: ");
                    String claimIdToUpdate = scanner.nextLine();
                    boolean updateResult = updateClaimById(claimIdToUpdate);
                    if (updateResult) {
                        System.out.println("Claim updated successfully.");
                    } else {
                        System.out.println("Claim with ID " + claimIdToUpdate + " not found.");
                    }
                }
                case 6 -> {
                    boolean validCustomerId = false;
                    ClaimList objClaimsOfCustomer = null;

                    do {
                        System.out.println("Enter Customer Id to search: ");
                        String customerId = scanner.nextLine();
                        objClaimsOfCustomer = getClaimsOfCustomerByCustomerId(customerId);

                        if (objClaimsOfCustomer.getClaims().isEmpty()) {
                            System.out.println("No claims found for the provided customer ID. Please enter a valid Customer ID.");
                        } else {
                            validCustomerId = true;
                        }
                    } while (!validCustomerId);

                    for (Claim claim : objClaimsOfCustomer.getClaims()) {
                        System.out.println(claim);
                    }
                }
                case 0 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
