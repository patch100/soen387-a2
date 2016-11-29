package models;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Created by pyoung on 2016-11-27.
 */
public class Contract extends DomainObject {
    private ArrayList revenueRecognitions = new ArrayList();
    private Product product;
    private double revenue;
    private LocalDate whenSigned;

    public Contract(Product product, double revenue, LocalDate whenSigned) {
        this.product = product;
        this.revenue = revenue;
        this.whenSigned = whenSigned;
    }

    public double recognizedRevenue(LocalDate asOf) {
        double result = 0;

        for (Object revenueRecognition : revenueRecognitions) {
            RevenueRecognition r = (RevenueRecognition) revenueRecognition;
            if (r.isRecognizableBy(asOf))
                result += r.getAmount();
        }
        return result;
    }

    public LocalDate getWhenSigned() {

        return whenSigned;
    }

    public double getRevenue() {

        return revenue;
    }

    public void addRevenueRecognition(RevenueRecognition revenueRecognition) {
        boolean alreadyInList = false;
        for (Object rr : revenueRecognitions) {
            RevenueRecognition r = (RevenueRecognition) rr;
            if (r.getContractId() == revenueRecognition.getContractId() && r.getDate().isEqual(((RevenueRecognition) rr).getDate()))
                alreadyInList = true;
        }
        if(!alreadyInList)
            revenueRecognitions.add(revenueRecognition);
    }

    public void calculateRecognitions() {
        product.calculateRevenueRecognitions(this);
    }

    public Long getProductId() {
        return this.product.getID();
    }

    public String getProductType() {
        return this.product.getType();
    }

    public String getProductName() {
        return this.product.getName();
    }

    public ArrayList<RevenueRecognition> getRevenueRecognitions() {
        return this.revenueRecognitions;
    }

    public void setRevenueRecognitions(ArrayList revenueRecognitions) {
        this.revenueRecognitions = revenueRecognitions;
    }
}
