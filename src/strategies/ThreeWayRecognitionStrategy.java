package strategies;

import models.Contract;
import models.RevenueRecognition;

/**
 * Created by pyoung on 2016-11-27.
 */
public class ThreeWayRecognitionStrategy extends RecognitionStrategy {
    private int firstRecognitionOffset;
    private int secondRecognitionOffset;

    public ThreeWayRecognitionStrategy(int firstRecognitionOffset, int secondRecognitionOffset) {
        this.firstRecognitionOffset = firstRecognitionOffset;
        this.secondRecognitionOffset = secondRecognitionOffset;
    }

    public void calculateRevenueRecognitions(Contract contract) {
        double allocation = contract.getRevenue();

        contract.addRevenueRecognition(new RevenueRecognition(allocation/3, contract.getWhenSigned(), contract.getID()));
        contract.addRevenueRecognition(new RevenueRecognition(allocation/3, contract.getWhenSigned().plusDays(firstRecognitionOffset), contract.getID()));
        contract.addRevenueRecognition(new RevenueRecognition(allocation/3, contract.getWhenSigned().plusDays(secondRecognitionOffset), contract.getID()));
    }
}
