/**
 * @author <Nguyen A Luy - S3891919>
 */
package lists;

import interfaces.ReadAndWriteFile;
import model.Document;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DocumentList implements ReadAndWriteFile {
    private ArrayList<Document> documents;
    public DocumentList() {
        this.documents = new ArrayList<>();
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public Document addNewDocument() {
        Scanner scanner = new Scanner(System.in);
        String documentId;
        boolean exists;

        do {
            System.out.println("Enter Document ID:");
            documentId = scanner.nextLine();
            exists = false;
            for (Document existingDocument : documents) {
                if (existingDocument.getDocumentId().equalsIgnoreCase(documentId)) {
                    System.out.println("Document ID already exists. Please enter a different ID.");
                    exists = true;
                    break;
                }
            }
        } while (exists);

        System.out.println("Enter Claim ID: ");
        String claimId = scanner.nextLine();

        System.out.println("Enter Insurance Card Number: ");
        String cardNumber = scanner.nextLine();

        System.out.println("Enter Document name:");
        String documentName = scanner.nextLine();

        Document document = new Document(documentId, claimId, cardNumber, documentName);
        System.out.println("Document added successfully.");
        return document;
    }

    public void addDocumentToList(Document document){
        this.documents.add(document);
    }

    public boolean updateDocumentById(String documentId) {
        Scanner scanner = new Scanner(System.in);
        for (Document document : this.documents) {
            if (document.getDocumentId().equalsIgnoreCase(documentId)) {
                System.out.println("Enter new Claim ID:");
                String newClaimId = scanner.nextLine();
                System.out.println("Enter new Card Number:");
                String newCardNumber = scanner.nextLine();
                System.out.println("Enter new Document Name:");
                String newDocumentName = scanner.nextLine();

                document.setDocumentId(documentId);
                document.setClaimId(newClaimId);
                document.setCardNumber(newCardNumber);
                document.setDocumentName(newDocumentName);

                return true;
            }
        }
        return false;
    }

    public boolean deleteDocumentById(String documentId) {
        Iterator<Document> iterator = this.documents.iterator();
        while (iterator.hasNext()) {
            Document document = iterator.next();
            if (document.getDocumentId().equals(documentId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public Document getDocumentById(String documentId) {
        for (Document document : this.documents) {
            if (document.getDocumentId().equals(documentId)) {
                return document;
            }
        }
        return null;
    }

    public void getAllDocuments() {
        sortDocumentById();
        for (Document document : this.documents) {
            System.out.println(document.toString());
        }
    }

    public void sortDocumentById() {
        Collections.sort(documents, new Comparator<Document>() {
            @Override
            public int compare(Document d1, Document d2) {
                int num1 = Integer.parseInt(d1.getDocumentId().substring(1));
                int num2 = Integer.parseInt(d2.getDocumentId().substring(1));
                return Integer.compare(num1, num2);
            }
        });
    }

    @Override
    public void readFromFile() {
        try {
            FileReader reader = new FileReader("files/DocumentData.txt");
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] listInfo = line.split(" # ");
                Document document = new Document(listInfo[0], listInfo[1], listInfo[2], listInfo[3]);
                this.documents.add(document);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeToFile() {
        try (FileWriter writer = new FileWriter("files/DocumentData.txt")) {
            for (Document document : documents) {
                writer.write(document.getDocumentId() + " # " + document.getClaimId() + " # " +
                        document.getCardNumber() + " # " + document.getDocumentName() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printMenuDocument() {
        System.out.println("====================DOCUMENT MENU====================");
        System.out.println("1. ADD NEW DOCUMENT");
        System.out.println("2. DELETE DOCUMENT BY ID");
        System.out.println("3. GET DOCUMENT BY ID");
        System.out.println("4. GET ALL DOCUMENTS");
        System.out.println("5. UPDATE DOCUMENT BY ID");
        System.out.println("0. EXIT DOCUMENT MENU");
    }

    public void doMenuDocument(){
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            printMenuDocument();
            System.out.print("Enter your choice: ");
            number = scanner.nextInt();
            scanner.nextLine();

            switch (number) {
                case 1 -> {
                    Document newDocument = addNewDocument();
                    addDocumentToList(newDocument);
                }
                case 2 -> {
                    System.out.print("Enter Document ID to delete: ");
                    String documentIdToDelete = scanner.nextLine();
                    boolean deleted = deleteDocumentById(documentIdToDelete);
                    if (deleted) {
                        System.out.println("Document with ID " + documentIdToDelete + " deleted successfully.");
                    } else {
                        System.out.println("Document with ID " + documentIdToDelete + " not found.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter Document ID to search: ");
                    String documentIdToSearch = scanner.nextLine();
                    Document document = getDocumentById(documentIdToSearch);
                    if (document != null) {
                        System.out.println("Document found: " + document);
                    } else {
                        System.out.println("Document with ID " + documentIdToSearch + " not found.");
                    }
                }
                case 4 -> {
                    System.out.println("All documents:");
                    getAllDocuments();
                }
                case 5 -> {
                    System.out.println("Enter Document ID to search: ");
                    String documentIdToSearch = scanner.nextLine();
                    boolean isUpdateDocument = updateDocumentById(documentIdToSearch);
                    if (isUpdateDocument) {
                        System.out.println("Document updated successfully");
                    } else {
                        System.out.println("Document with ID " + documentIdToSearch + " not found.");
                    }
                }
                case 0 -> System.out.println("Exiting document menu.");
                default -> System.out.println("Invalid choice. Please enter a number between 0 and 4.");
            }
        } while (number != 0);
    }
}
