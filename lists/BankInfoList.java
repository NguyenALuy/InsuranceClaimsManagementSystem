package lists;

import interfaces.ReadAndWriteFile;
import model.BankInfo;


import java.io.*;
import java.util.*;

public class BankInfoList implements ReadAndWriteFile {
    private ArrayList<BankInfo> bankInfos;

    public ArrayList<BankInfo> getBankInfos() {
        return bankInfos;
    }

    public void setBankInfos(ArrayList<BankInfo> bankInfos) {
        this.bankInfos = bankInfos;
    }

    public BankInfoList() {
        this.bankInfos = new ArrayList<BankInfo>();
    }

    public BankInfo addNewBankInfo() {
        Scanner scanner = new Scanner(System.in);
        String bankNumber;
        boolean exists;
        do {
            System.out.println("Enter bank number:");
            bankNumber = scanner.nextLine();
            exists = false;
            for (BankInfo existingBank : bankInfos) {
                if (existingBank.getBankNumber().equalsIgnoreCase(bankNumber)) {
                    System.out.println("Bank number already exists. Please enter a different number.");
                    exists = true;
                    break;
                }
            }
        } while (exists);

        System.out.println("Enter bank name:");
        String bankName = scanner.nextLine();
        System.out.println("Enter customer name:");
        String customerName = scanner.nextLine();

        BankInfo bankInfo = new BankInfo(bankName, customerName, bankNumber);
        System.out.println("BankInfo added successfully.");
        return bankInfo;
    }


    public boolean deleteBankByNumber(String bankNumber) {
        Iterator<BankInfo> iterator = this.bankInfos.iterator();
        while (iterator.hasNext()) {
            BankInfo bankInfo = iterator.next();
            if (bankInfo.getBankNumber().equalsIgnoreCase(bankNumber)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }


    public BankInfo findBankByBankNumber(String bankNumber) {
        for (BankInfo bankInfo : this.bankInfos) {
            if (bankInfo.getBankNumber().equalsIgnoreCase(bankNumber)) {
                return bankInfo;
            }
        }
        return null;
    }

    public void getAllBanks() {
        sortBankInfoByBankNumber();
        for (BankInfo bankInfo : this.bankInfos) {
            System.out.println(bankInfo.toString());
        }
    }

    public void sortBankInfoByBankNumber() {
        Collections.sort(bankInfos, new Comparator<BankInfo>() {
            @Override
            public int compare(BankInfo b1, BankInfo b2) {
                String bankNumber1 = b1.getBankNumber().replaceAll("-", "");
                String bankNumber2 = b2.getBankNumber().replaceAll("-", "");
                long num1 = Long.parseLong(bankNumber1);
                long num2 = Long.parseLong(bankNumber2);
                return Long.compare(num1, num2);
            }
        });
    }

    public void addBankToList(BankInfo bankInfo) {
        this.bankInfos.add(bankInfo);
    }

    @Override
    public void readFromFile() {
        try {
            FileReader reader = new FileReader("files/BankInformationData.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] listInfo = line.split(" # ");
                BankInfo bankInfo = new BankInfo(listInfo[0], listInfo[1], listInfo[2]);
                this.bankInfos.add(bankInfo);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("files/BankInformationData.txt")) {
            for (BankInfo bankInfo : bankInfos) {
                writer.write(bankInfo.getBankName() + " # " + bankInfo.getCustomerName() + " # " + bankInfo.getBankNumber() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenuBank() {
        System.out.println("====================BANK MENU====================");
        System.out.println("1. ADD NEW BANK INFORMATION");
        System.out.println("2. DELETE BANK INFORMATION BY BANK NUMBER");
        System.out.println("3. GET BANK INFORMATION BY BANK NUMBER ");
        System.out.println("4. GET ALL BANK INFORMATION");
        System.out.println("0. EXIT BANK MENU");
    }

    public void doMenuBank() {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            printMenuBank();
            System.out.print("Enter the number: ");
            number = scanner.nextInt();
            scanner.nextLine();
            switch (number) {
                case 1 -> {
                    BankInfo newBankInfo = addNewBankInfo();
                    addBankToList(newBankInfo);
                }
                case 2 -> {
                    System.out.print("Enter bank number to delete: ");
                    String bankNumberToDelete = scanner.nextLine();
                    boolean deleted = deleteBankByNumber(bankNumberToDelete);
                    if (deleted) {
                        System.out.println("Bank information deleted successfully.");
                    } else {
                        System.out.println("Bank information with bank number " + bankNumberToDelete + " not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter bank number to search: ");
                    String bankNumberToSearch = scanner.nextLine();
                    BankInfo foundBank = findBankByBankNumber(bankNumberToSearch);
                    if (foundBank != null) {
                        System.out.println("Bank information found: " + foundBank);
                    } else {
                        System.out.println("Bank information with bank number " + bankNumberToSearch + " not found.");
                    }
                }
                case 4 -> {
                    System.out.println("All bank information:");
                    getAllBanks();
                }
                case 0 -> {
                    writeToFile();
                    System.out.println("Exiting bank menu.");
                }
                default -> System.out.println("Invalid choice. Please enter a number between 0 and 4.");
            }
        } while (number != 0);
    }

}
