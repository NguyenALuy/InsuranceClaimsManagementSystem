package lists;

import interfaces.ReadAndWriteFile;
import model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class InsuranceCardList implements ReadAndWriteFile {
    private ArrayList<InsuranceCard> insuranceCards;
    private CustomerList customerList;
    private PolicyOwnerList policyOwnerList;

    public ArrayList<InsuranceCard> getInsuranceCards() {
        return insuranceCards;
    }

    public void setInsuranceCards(ArrayList<InsuranceCard> insuranceCards) {
        this.insuranceCards = insuranceCards;
    }

    public InsuranceCardList(CustomerList customerList, PolicyOwnerList policyOwnerList) {
        this.insuranceCards = new ArrayList<>();
        this.customerList = customerList;
        this.policyOwnerList = policyOwnerList;
    }

    public void addInsuranceCardToList(InsuranceCard insuranceCard) {
        this.insuranceCards.add(insuranceCard);
    }

    public InsuranceCard findInsuranceCardByNumber(String cardNum) {
        for (InsuranceCard insuranceCard : insuranceCards) {
            if (insuranceCard.getCardNumber().equalsIgnoreCase(cardNum)) {
                return insuranceCard;
            }
        }
        return null;
    }

    public InsuranceCard addNewInsuranceCard() {
        Scanner scanner = new Scanner(System.in);
        boolean success = false;

        do {
//            System.out.print("Enter Customer ID: ");
//            String customerId = scanner.nextLine();
//            Customer customer = customerList.findCustomerById(customerId);
//
//            if (customer == null || customer.getInsuranceCard() != null) {
//                System.out.println("Invalid Customer ID or Customer already has an Insurance Card.");
//                continue;
//            }

            Customer customer = customerList.addNewCustomer();
            customerList.addCustomerToList(customer);
            String cardNumber;
            do {
                System.out.print("Enter Card Number: ");
                cardNumber = scanner.nextLine();

                if (isCardNumberExists(cardNumber)) {
                    System.out.println("Card already exists. Please enter a different card number.");
                } else {
                    break;
                }
            } while (true);

            System.out.print("Enter Formatted Expiration Date (DD/MM/YYYY): ");
            String formattedExpirationDate = scanner.nextLine();

            InsuranceCard newInsuranceCard = new InsuranceCard(cardNumber, formattedExpirationDate);
            insuranceCards.add(newInsuranceCard);

            newInsuranceCard.setCustomer(customer);
            customer.setInsuranceCard(newInsuranceCard);

            PolicyHolder policyHolder = selectPolicyHolder(customerList, scanner);

            if (customer instanceof Dependent) {
                newInsuranceCard.setCardHolder(policyHolder);
                policyHolder.getObjDependentList().add((Dependent) customer);
            } else {
                newInsuranceCard.setCardHolder(policyHolder);
            }

            PolicyOwner policyOwner = selectPolicyOwner(policyOwnerList, scanner);
            newInsuranceCard.setPolicyOwner(policyOwner);
            policyOwner.getObjPolicyHolderList().getCustomers().add(policyHolder);

            success = true;
            System.out.println("Insurance card added successfully.");
        } while (!success);
        return null;
    }

    private boolean isCardNumberExists(String cardNumber) {
        for (InsuranceCard card : insuranceCards) {
            if (card.getCardNumber().equalsIgnoreCase(cardNumber)) {
                return true;
            }
        }
        return false;
    }

    private PolicyHolder selectPolicyHolder(CustomerList customerList, Scanner scanner) {
        PolicyHolder policyHolder = null;
        do {
            System.out.print("Enter the Card Holder ID: ");
            String cardHolderId = scanner.nextLine();
            Customer cardHolder = customerList.findCustomerById(cardHolderId);

            if (!(cardHolder instanceof PolicyHolder)) {
                System.out.println("Invalid ID. The customer is not a Policy Holder. Please enter a valid Policy Holder ID.");
            } else {
                policyHolder = (PolicyHolder) cardHolder;
            }
        } while (policyHolder == null);
        return policyHolder;
    }

    private PolicyOwner selectPolicyOwner(PolicyOwnerList policyOwnerList, Scanner scanner) {
        PolicyOwner policyOwner = null;
        do {
            System.out.print("Enter the Policy Owner ID: ");
            String policyOwnerId = scanner.nextLine();
            policyOwner = policyOwnerList.findPOwnerById(policyOwnerId);
            if (policyOwner == null) {
                System.out.println("Policy Owner not found. Please enter a valid ID.");
            }
        } while (policyOwner == null);
        return policyOwner;
    }
    @Override
    public void readFromFile() {
        ArrayList<String> validLines = new ArrayList<>();
        try (FileReader reader = new FileReader("files/InsuranceCardData.txt");
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] listInfo = line.split(" # ");
                if (listInfo.length == 5) {
                    String customerId = listInfo[0];
                    String cardNumber = listInfo[1];
                    String expirationDate = listInfo[2];
                    String cardHolderId = listInfo[3];
                    String policyOwnerId = listInfo[4];

                    Customer customer = customerList.findCustomerById(customerId);
                    PolicyHolder cardHolder = (PolicyHolder) customerList.findCustomerById(cardHolderId);
                    PolicyOwner policyOwner = policyOwnerList.findPOwnerById(policyOwnerId);

                    if (customer != null && cardHolder != null && policyOwner != null) {
                        InsuranceCard insuranceCard = new InsuranceCard(cardNumber, expirationDate);
                        insuranceCard.setCustomer(customer);
                        insuranceCard.setCardHolder(cardHolder);
                        customer.setInsuranceCard(insuranceCard);
                        if (customer instanceof Dependent) {
                            cardHolder.getObjDependentList().add((Dependent) customer);
                        }
                        insuranceCard.setPolicyOwner(policyOwner);
                        policyOwner.getObjPolicyHolderList().getCustomers().add(customer);
                        insuranceCards.add(insuranceCard);
                        validLines.add(line);
                    } else {
                        System.out.println("Invalid customer, card holder, or policy owner: " + customerId + ", " + cardHolderId + ", " + policyOwnerId);
                    }
                } else {
                    System.out.println("Invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileWriter writer = new FileWriter("files/InsuranceCardData.txt")) {
            for (String validLine : validLines) {
                writer.write(validLine);
                writer.write("\n");
            }
            System.out.println("Valid insurance card information has been written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean deleteInsuranceCardByNumber(String cardNum) {
        for (InsuranceCard insuranceCard : this.insuranceCards) {
            if (insuranceCard.getCardNumber().equals(cardNum)) {
                insuranceCards.remove(insuranceCard);
                Customer customer = insuranceCard.getCustomer();
                if (customer instanceof Dependent) {
                    PolicyHolder policyHolder = insuranceCard.getCardHolder();
                    if (policyHolder != null) {
                        policyHolder.getObjDependentList().remove((Dependent) customer);
                    }
                }
                customerList.getCustomers().remove(customer);
                return true;
            }
        }
        return false;
    }

    public boolean updateInsuranceCardByNumber(String cardNum) {
        Scanner scan = new Scanner(System.in);
        for(InsuranceCard insuranceCard : this.insuranceCards){
            if(insuranceCard.getCardNumber().equalsIgnoreCase(cardNum)){
                insuranceCard.setCardNumber(cardNum);
                System.out.println("Enter New Expiration Date (DD/MM/YYYY):");
                String newExpDate = scan.nextLine();
                insuranceCard.setFormattedExpirationDate(newExpDate);
                return true;
            }
        }
        return false;
    }


    public void getAllInsuranceCards() {
        sortInsuranceCardByNumber();
        for (InsuranceCard insuranceCard : this.insuranceCards) {
            System.out.println(insuranceCard.toString());
        }
    }

    public void sortInsuranceCardByNumber() {
        Collections.sort(insuranceCards, new Comparator<InsuranceCard>() {
            @Override
            public int compare(InsuranceCard card1, InsuranceCard card2) {
                String num1 = card1.getCardNumber().replace("-", "");
                String num2 = card2.getCardNumber().replace("-", "");
                return num1.compareTo(num2);
            }
        });
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("files/InsuranceCardData.txt")) {
            for (InsuranceCard insuranceCard : insuranceCards) {
                String line = insuranceCard.getCustomer().getId() + " # " +
                        insuranceCard.getCardNumber() + " # " +
                        insuranceCard.getFormattedExpirationDate() + " # " +
                        insuranceCard.getCardHolder().getId() + " # " +
                        insuranceCard.getPolicyOwner().getPolicyOwnerId();
                writer.write(line);
                writer.write("\n");
            }
            System.out.println("Insurance card information has been written to the file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }    }

    public void printMenuInsuranceCard() {
        System.out.println("====================INSURANCE CARD MENU====================");
        System.out.println("1. ADD NEW INSURANCE CARD");
        System.out.println("2. GET INSURANCE CARD BY CARD NUMBER");
        System.out.println("3. GET ALL INSURANCE CARD");
        System.out.println("4. UPDATE INSURANCE CARD BY CARD NUMBER");
        System.out.println("5. DELETE INSURANCE CARD BY CARD NUMBER");
        System.out.println("0. EXIT INSURANCE CARD MENU");
    }

    public void doMenuInsuranceCard(){
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            printMenuInsuranceCard();
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> {
                    addNewInsuranceCard();
                }
                case 2 -> {
                    System.out.print("Enter Card Number to search: ");
                    String searchCardNum = scanner.nextLine();
                    InsuranceCard foundCard = findInsuranceCardByNumber(searchCardNum);
                    if (foundCard != null)
                        System.out.println(foundCard.toString());
                    else
                        System.out.println("Insurance Card not found.");
                }
                case 3 -> getAllInsuranceCards();
                case 4 -> {
                    System.out.print("Enter Card Number to update: ");
                    String updateCardNum = scanner.nextLine();
                    if (updateInsuranceCardByNumber(updateCardNum))
                        System.out.println("Insurance Card updated successfully.");
                    else
                        System.out.println("Insurance Card not found.");
                }
                case 5-> {
                    System.out.print("Enter Card Number to delete: ");
                    String deleteCardNum = scanner.nextLine();
                    if (deleteInsuranceCardByNumber(deleteCardNum))
                        System.out.println("Insurance Card deleted successfully.");
                    else
                        System.out.println("Insurance Card not found.");
                }
                case 0 -> exit = true;
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}
