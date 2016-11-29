package strategies;

import models.Contract;
import models.RevenueRecognition;

/**
 * Created by pyoung on 2016-11-27.
 */
public class CompleteRecognitionStrategy extends RecognitionStrategy{

    public void calculateRevenueRecognitions(Contract contract) {
        contract.addRevenueRecognition(new RevenueRecognition(contract.getRevenue(), contract.getWhenSigned(), contract.getID()));
    }
}
