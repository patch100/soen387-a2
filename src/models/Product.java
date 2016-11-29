package models;

import strategies.CompleteRecognitionStrategy;
import strategies.RecognitionStrategy;
import strategies.ThreeWayRecognitionStrategy;

/**
 * Created by pyoung on 2016-11-27.
 */
public class Product extends DomainObject {
    private String name;
    private String type;
    private RecognitionStrategy recognitionStrategy;

    public Product(String name, String type, RecognitionStrategy recognitionStrategy) {
        this.name = name;
        this.type = type;
        this.recognitionStrategy = recognitionStrategy;
    }

    public static Product newWordProcessor(String name, String type) {

        return new Product(name,type, new CompleteRecognitionStrategy());
    }

    public static Product newSpreadsheet(String name, String type) {
        return new Product(name, type, new ThreeWayRecognitionStrategy(60, 90));
    }

    public static Product newDatabase(String name, String type) {
        return new Product(name, type, new ThreeWayRecognitionStrategy(30, 60));
    }

    void calculateRevenueRecognitions(Contract contract) {
        recognitionStrategy.calculateRevenueRecognitions(contract);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
