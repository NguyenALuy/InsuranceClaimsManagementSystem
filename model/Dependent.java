/**
 * @author <Nguyen A Luy - S3891919>
 */
package model;

public class Dependent extends Customer {

    public Dependent(String id, String fullName) {
        super(id, fullName);
        setInsuranceCard(null);
    }

    public Dependent() {
        setInsuranceCard(null);
    }

    @Override
    public String toString() {
        return "Dependent{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", insuranceCard=" + getInsuranceCard() +
                "}";
    }
}
