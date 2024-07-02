import java.util.ArrayList;
public class ShoppingCart {
    // Creating a product list to store products
    ArrayList<AbstractProduct> products_list = new ArrayList<>();

    // adding products to the shopping cart
    public void add_product(AbstractProduct product){
        products_list.add(product);
    }

   public ArrayList arrayList(){
        return products_list;
    }

    // remove product from the shopping cart
    public void remove_product(AbstractProduct product){
        products_list.remove(product);
        System.out.println("Product Removed Successfully..!");
    }
    // calculating the total cost of the products in the shopping cart
    public double total_cost(){
        double total = 0;
        for (AbstractProduct product : products_list){
            total += product.getPrice();
        }
        return total;
    }
}
