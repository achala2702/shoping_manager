public class Electronics extends AbstractProduct {

    // initializing variables
    private String brand;
    private String warranty_period;

    // constructor
    public Electronics(String product_id, String product_name, int number_of_available_items, double price, String brand, String warranty_period){
        super(product_id, product_name, number_of_available_items, price);
        this.brand = brand;
        this.warranty_period = warranty_period;
    }

    // getters and setters
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getWarranty_period() {
        return warranty_period;
    }

    public void setWarranty_period(String warranty_period) {
        this.warranty_period = warranty_period;
    }
}
