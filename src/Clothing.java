public class Clothing extends AbstractProduct {

    // initializing variables
    private String size;
    private String colour;

    // constructor
    public Clothing(String product_id, String product_name, int number_of_available_items, double price, String size, String colour) {
        super(product_id, product_name, number_of_available_items, price);
        this. size = size;
        this.colour = colour;
    }
    // getters and setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
