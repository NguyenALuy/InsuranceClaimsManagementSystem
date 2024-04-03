package model;

import java.text.SimpleDateFormat;
import java.util.Date;

/*
the customer might have insuranceCard null, because he/she newly created and he/she does not register for an insurance card
therefore, the customer will have the claimList null, because he/she do not have the insuranceCard to make claim(s)
However, when the object insuranceCard created, the cardNumber is now valid,
it must have customer owned the card (I added it as the additional parameter in the InsuranceCard)
it must have the card holder - policy holder
it must have the policy owner
*/

public class InsuranceCard {
    private Customer customer;
    private String cardNumber;
    private PolicyHolder cardHolder;
    private PolicyOwner policyOwner;
    private Date expirationDate;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public PolicyHolder getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(PolicyHolder cardHolder) {
        this.cardHolder = cardHolder;
    }

    public PolicyOwner getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(PolicyOwner policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getFormattedExpirationDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(expirationDate);
    }

    public void setFormattedExpirationDate(String formattedExpirationDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            this.expirationDate = sdf.parse(formattedExpirationDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InsuranceCard(String cardNumber, String formattedExpirationDate) {
        this.cardNumber = cardNumber;
        this.customer =null;
        this.cardHolder = null;
        this.policyOwner = null;
        setFormattedExpirationDate(formattedExpirationDate);
    }

    public InsuranceCard() {
    }

    @Override
    public String toString() {
        return "InsuranceCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", cardHolder=" + "{ " + cardHolder.getId() + ", " + cardHolder.getFullName() + "}" +
                ", policyOwner=" + policyOwner +
                ", expirationDate=" + getFormattedExpirationDate() +
                '}';
    }


}
