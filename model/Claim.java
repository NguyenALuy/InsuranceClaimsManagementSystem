package model;

import lists.ClaimList;
import lists.DocumentList;

import java.text.SimpleDateFormat;
import java.util.*;

public class Claim {
    private String claimId;
    private Date claimDate;
    private Customer insuredPerson;
    private String cardNumber;//this is the insurance card number
    private Date examDate;
    private DocumentList objDocumentList;
    private int claimAmount;
    private String status;
    private BankInfo receiverBankingInfo;

    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public Date getClaimDate() {
        return claimDate;
    }

    public void setClaimDate(Date claimDate) {
        this.claimDate = claimDate;
    }

    public Customer getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(Customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public DocumentList getObjDocumentList() {
        return objDocumentList;
    }

    public void setObjDocumentList(DocumentList objDocumentList) {
        this.objDocumentList = objDocumentList;
    }

    public int getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(int claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BankInfo getReceiverBankingInfo() {
        return receiverBankingInfo;
    }

    public void setReceiverBankingInfo(BankInfo receiverBankingInfo) {
        this.receiverBankingInfo = receiverBankingInfo;
    }

    public String getFormattedClaimDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(claimDate);
    }

    public String getFormattedExamDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(examDate);
    }

    public void setFormattedClaimDate(String formattedClaimDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.claimDate = sdf.parse(formattedClaimDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setFormattedExamDate(String formattedClaimDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.examDate = sdf.parse(formattedClaimDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Claim(String claimId, String formattedClaimDate, String cardNumber, String formattedExamDate, String status, int amount) {
        this.claimId = claimId;
        setFormattedClaimDate(formattedClaimDate);
        this.insuredPerson = null;
        this.cardNumber = cardNumber;
        setFormattedExamDate(formattedExamDate);
        this.claimAmount = amount;
        this.status = status;
        this.receiverBankingInfo = null;
        this.objDocumentList = new DocumentList();
    }

    @Override
    public String toString() {
        return "Claim{" +
                "claimId='" + claimId + '\'' +
                ", claimDate=" + getFormattedClaimDate() +
                ", insuredPerson=" + insuredPerson +
                ", cardNumber='" + cardNumber + '\'' +
                ", examDate=" + getFormattedExamDate() +
                ", claimAmount=" + claimAmount +
                ", status='" + status + '\'' +
                ", receiverBankingInfo=" + receiverBankingInfo +
                '}';
    }
}
