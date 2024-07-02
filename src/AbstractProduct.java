import java.io.Serializable;

public abstract class AbstractProduct implements Serializable {
    // initializing variables
    private String product_id;
    private String product_name;
    private int number_of_available_items;
    private double price;

    // constructor
    public AbstractProduct(String product_id, String product_name, int number_of_available_items, double price){
        this.product_id = product_id;
        this.product_name = product_name;
        this.number_of_available_items = number_of_available_items;
        this.price = price;
    }

    // getters and setters
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getNumber_of_available_items() {
        return number_of_available_items;
    }

    public void setNumber_of_available_items(int number_of_available_items) {
        this.number_of_available_items = number_of_available_items;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }




}
