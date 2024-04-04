package lists;

import interfaces.ReadAndWriteFile;
import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustomerList implements ReadAndWriteFile {
    private ArrayList<Customer> customers;
    private  InsuranceCardList insuranceCardList;

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public CustomerList() {
        this.customers = new ArrayList<Customer>();
    }

    public Customer findCustomerById(String customerId) {
        for (Customer customer : this.customers) {
            if (customer.getId().equalsIgnoreCase(customerId)) {
                return customer;
            }
        }
        return null;
    }

    public Customer addNewCustomer() {
        Scanner scanner = new Scanner(System.in);
        String customerId;
        String customerName;
        int choice;
        boolean invalidChoice;

        do {
            invalidChoice = false;

            System.out.println("Enter Customer ID:");
            customerId = scanner.nextLine();

            if (findCustomerById(customerId) != null) {
                System.out.println("Customer ID already exists. Please enter a different ID.");
                invalidChoice = true;
            }

            if (!invalidChoice) {
                System.out.println("Enter Customer name:");
                customerName = scanner.nextLine();
                System.out.println("Choose the type of customer:");
                System.out.println("1. Dependent");
                System.out.println("2. Policy Holder");
                System.out.print("Enter your choice (1 or 2): ");
                while (!scanner.hasNextInt()) {
                    System.out.println("Invalid input. Please enter 1 or 2.");
                    scanner.next();
                }
                choice = scanner.nextInt();
                scanner.nextLine();

                if (choice != 1 && choice != 2) {
                    System.out.println("Invalid choice. Please enter 1 or 2.");
                    invalidChoice = true;
                }

                if (!invalidChoice) {
                    Customer customer;
                    if (choice == 1) {
                        customer = new Dependent(customerId, customerName);
                        System.out.println("Dependent added successfully.");
                    } else {
                        customer = new PolicyHolder(customerId, customerName);
                        System.out.println("Policy Holder added successfully.");
                    }
                    return customer;
                }
            }
        } while (true);
    }
    public void addCustomerToList(Customer customer){
        this.customers.add(customer);
    }

    public boolean deleteCustomerById(String customerId) {
        for (Customer customer : this.customers) {
            if (customer.getId().equalsIgnoreCase(customerId)) {
                customers.remove(customer);
                return true;
            }
        }
        return false;
    }

    public boolean updateCustomerById(String customerId) {
        Scanner scan = new Scanner(System.in);
        for(Customer customer : this.customers){
            if(customer.getId().equalsIgnoreCase(customerId)){
                customer.setId(customerId);
                System.out.println("Enter Customer name:");
                String newCustomerName = scan.nextLine();
                customer.setFullName(newCustomerName);
                return true;
            }
        }
        return false;
    }

    public void getAllCustomers() {
        for (Customer customer : this.customers) {
            System.out.println(customer.toString());
        }
    }

    @Override
    public void readFromFile() {
        try (Scanner scanner = new Scanner(new File("files/CustomerData.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] listInfo = line.split(" # ");
                if (listInfo.length == 3) {
                    String id = listInfo[0];
                    String fullName = listInfo[1];

                    Customer customer;
                    if (listInfo[listInfo.length - 1].equalsIgnoreCase("true")) {
                        customer = new Dependent(id, fullName);
                    } else {
                        customer = new PolicyHolder(id, fullName);
                    }
                    customers.add(customer);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("files/CustomerData.txt")) {
            for (Customer customer : customers) {
                String isDependent = (customer instanceof Dependent) ? "true" : "false";
                writer.write(customer.getId() + " # " + customer.getFullName() + " # " + isDependent + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenuCustomer(){
        System.out.println("====================CUSTOMER MENU====================");
        System.out.println("1. GET CUSTOMER BY ID");
        System.out.println("2. GET ALL CUSTOMERS");
        System.out.println("3. UPDATE CUSTOMER BY ID");
        System.out.println("4. GET DEPENDENT LIST OF A POLICY HOLDER BY ID");
        System.out.println("0. EXIT CUSTOMER MENU");
    }

    public void doMenuCustomer(){
        Scanner scanner = new Scanner(System.in);
        int number;

        do {
            printMenuCustomer();
            System.out.print("Enter your choice: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
            number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1 -> {
                    System.out.print("Enter Customer ID to get: ");
                    String customerIdToGet = scanner.nextLine();
                    Customer foundCustomer = findCustomerById(customerIdToGet);
                    if (foundCustomer != null) {
                        System.out.println("Customer found: " + foundCustomer);
                    } else {
                        System.out.println("Customer with ID " + customerIdToGet + " not found.");
                    }
                }
                case 2 -> {
                    System.out.println("All Customers:");
                    getAllCustomers();
                }
                case 3 -> {
                    System.out.print("Enter Customer ID to update: ");
                    String customerIdToUpdate = scanner.nextLine();
                    if (updateCustomerById(customerIdToUpdate)) {
                        System.out.println("Customer updated successfully.");
                    } else {
                        System.out.println("Customer with ID " + customerIdToUpdate + " not found.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter Policy Holder ID to search: ");
                    String pHolderId = scanner.nextLine();
                    PolicyHolder foundPolicyHolder = null;
                    for (Customer customer : customers) {
                        if (customer instanceof PolicyHolder && customer.getId().equalsIgnoreCase(pHolderId)) {
                            foundPolicyHolder = (PolicyHolder) customer;
                            break;
                        }
                    }
                    if (foundPolicyHolder != null) {
                        System.out.println("Dependent list of Policy Holder with ID " + pHolderId + ":");
                        ArrayList<Dependent> dependents = foundPolicyHolder.getObjDependentList();
                        if (dependents.isEmpty()) {
                            System.out.println("No dependents found.");
                        } else {
                            for (Dependent dependent : dependents) {
                                System.out.println(dependent);
                            }
                        }
                    } else {
                        System.out.println("Policy Holder not found with ID " + pHolderId);
                    }
                }
                case 0 -> System.out.println("Exiting Customer Menu.");
                default -> System.out.println("Invalid choice. Please enter a valid option.");
            }
        } while (number != 0);
    }
}
