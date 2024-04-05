import lists.*;

import java.util.Scanner;

public class Main {
    static final String YELLOW_COLOR = "\u001B[33m";
    static final String RESET_COLOR = "\u001B[0m";

    public static void main(String[] args) {
        doInsuranceManagementSystemMenu();
    }

    public static void printInsuranceManagementSystemMenu(){
        System.out.println(YELLOW_COLOR + "==========INSURANCE MANAGEMENT SYSTEM MENU==========" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "1. CUSTOMER MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "2. POLICY OWNER MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "3. I6NSURANCE CARD MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "4. BANK INFORMATION MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "5. CLAIM MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "6. DOCUMENT MENU" + RESET_COLOR);
        System.out.println(YELLOW_COLOR + "0. EXIT THE SYSTEM" + RESET_COLOR);
    }

    public static void doInsuranceManagementSystemMenu(){
        CustomerList customerList = new CustomerList();
        PolicyOwnerList policyOwnerList = new PolicyOwnerList();
        InsuranceCardList insuranceCardList = new InsuranceCardList(customerList, policyOwnerList);
        BankInfoList bankInfoList = new BankInfoList();
        DocumentList documentList = new DocumentList();
        ClaimList claimList = new ClaimList(insuranceCardList, customerList, bankInfoList, documentList);
        customerList.readFromFile();
        policyOwnerList.readFromFile();
        insuranceCardList.readFromFile();
        bankInfoList.readFromFile();
        documentList.readFromFile();
        claimList.readFromFile();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            printInsuranceManagementSystemMenu();
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> customerList.doMenuCustomer();
                case 2 -> policyOwnerList.doMenuPolicyOwner();
                case 3 -> insuranceCardList.doMenuInsuranceCard();
                case 4 -> bankInfoList.doMenuBank();
                case 5 -> claimList.doMenuClaim();
                case 6 -> documentList.doMenuDocument();
                case 0 -> {
                    customerList.writeToFile();
                    bankInfoList.writeToFile();
                    policyOwnerList.writeToFile();
                    insuranceCardList.writeToFile();
                    claimList.writeToFile();
                    documentList.writeToFile();
                    System.out.println("Exiting the system. Thank you!");
                }
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (choice != 0);
    }

}
