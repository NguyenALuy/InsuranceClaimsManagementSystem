package model;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class Document {
    private String documentId;
    private String claimId;
    private String cardNumber;
    private String documentName;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public Document(String documentId, String claimId, String cardNumber, String documentName) {
        this.documentId = documentId;
        this.claimId = claimId;
        this.cardNumber = cardNumber;
        this.documentName = documentName;
    }

    public Document() {
        this.documentId = "Default";
        this.claimId = "Default";
        this.cardNumber = "Default";
        this.documentName = "Default";
    }

    @Override
    public String toString() {
        return "Document{" +
                "documentId='" + documentId + '\'' +
                ", claimId='" + claimId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", documentName='" + documentName + '\'' +
                '}';
    }

}

