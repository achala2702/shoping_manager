import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterShoppingManager implements ShoppingManager {
    //initializing the list that containing all the products in the system(maximum no of product = 50)
    ArrayList<AbstractProduct> allProductList = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    // menu of the shopping manager
    public static void main(String[] args) throws IOException {

        // creating an array list to store objects which are rea from file
        ArrayList<AbstractProduct> readFromFile = new ArrayList<>();
        //creating an object of the WestminsterShoppingManager inside the main method to call the methods of that class inside the main method
        WestminsterShoppingManager obj = new WestminsterShoppingManager();

        //creating a file object with the file name
        File file = new File("product_list.txt");
        //checking th availability of the file, read it and add the content in file to an array list

        if (file.exists()){
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                //Create a file input stream
                fis = new FileInputStream("product_list.txt");
                //Create a object input stream
                ois = new ObjectInputStream(fis);

                //loop until the end of the text file
                while (true) {
                    AbstractProduct p = (AbstractProduct) ois.readObject();
                    // add to the array list named as readFromFile
                    readFromFile.add(p);
                }
                //catching the end of the file
            } catch (EOFException e) {
                System.out.println("");

                //handle other exceptions
            } catch (Exception e) {
                e.printStackTrace();
            }
            //closing the file input stream and object input stream
            fis.close();
            ois.close();

        }else{
            System.out.println("ERROR: File Not Found");
        }

        //calling the menu
        obj.menu(readFromFile);
    }
    public void menu(ArrayList readedList){

    //printing the previously added products
        allProductList.addAll(readedList);

        //initializing variables
        String choice;
        boolean menuRepeat = true;
        while(menuRepeat) {
            String[] menu_items = {"Add a New Product", "Delete a Product", "Print the list of the Products", "Exit"};

            System.out.println("****Menu****");

            for (int i = 1; i <= menu_items.length; i++) {
                System.out.println("Press " + i + " to " + menu_items[i - 1]);
            }
            while (true) {
                //getting the user's choice
                System.out.println("Enter your choice : ");
                choice = scanner.next();

                switch (choice) {
                    case "1":
                        add_product();
                        break;
                    case "2":
                        delete_product();
                        break;
                    case "3":
                        print_product_list();
                        break;
                    case "4":
                        System.out.println("Exiting...!");
                        scanner.close();
                        menuRepeat = false;
                        break;
                    default:
                        System.out.println("Invalid Input Please Enter a Valid Input");
                        continue;
                }
                break;
            }
        }

    }

    //adding products to the system if the total products count <50
    public void add_product(){
        //initializing the variables
        String choice, brand, colour, size, warranty;
        int no_items, numWarranty;
        double price;
        ArrayList<String> products = new ArrayList<>();

        //asking user to choose the product type
        System.out.println("Please choose the product type : \nPress 1 for Electronics\nPress 2 for Clothing");

        while (true) if (allProductList.size()<50) {

                System.out.println("Enter your choice : ");
                choice = scanner.next();

                switch (choice) {
                    case "1":

                        //call the product details method and assign its returned arraylist to products
                        products = product_details();

                        if (products == null) {
                            break;
                        }

                        //get and checking the brand name
                        brand = checkStrVal("Brand Name");

                        //get and removing the decimal part from warranty
                        numWarranty = (int) Double.parseDouble(checkNumeric("Warranty Period(In Weeks)"));
                        warranty = numWarranty + " Weeks Warranty";

                        //getting the checked number of items, price and Assigning them after converting to the relevant data types
                        no_items = (int) Double.parseDouble(products.get(2));
                        price = Double.parseDouble(products.get(3));

                        //creating an electronic object
                        Electronics elect = new Electronics(products.get(0), products.get(1), no_items, price, brand, warranty);


                        //adding the electronic object to all product list
                        allProductList.add(elect);
                        break;
                    case "2":
                        //calling the product_details method and assign its return arraylist to products
                        products = product_details();

                        // breaks the loop and return to the main menu if product_detail() return null
                        if (products == null) {
                            break;
                        }

                        //getting the checked number off items, price and Assigning them after converting to the relevant data types
                        no_items = (int) Double.parseDouble(products.get(2));
                        price = Double.parseDouble(products.get(3));

                        //getting and checking the colour and size
                        colour = checkStrVal("Colour");
                        size = checkStrVal("Size");

                        //creating a cloth object
                        Clothing cloth = new Clothing(products.get(0), products.get(1), no_items, price, colour, size);

                        //adding the cloth to the all product list
                        allProductList.add(cloth);
                        break;
                    default:
                        System.out.println("Invalid Input please enter a valid input");
                        continue;
                }
                break;
        }
        else{
            System.out.println("Maximum Products Limit Reached..!");
            break;
        }

        //saving the products to the file
        save_file();

    }

    //delete product from the system
    public void delete_product(){

        //initializing the variable product id
        String product_id, class_name;
        int remaining_product_count;
        boolean result = false;

        //getting the product id
        product_id = checkStrVal("Product ID");

        //check the product id with all the products and remove the product and print its type if it is found and printing remaining product count
        for (int i = 0; i<allProductList.size(); i++){

            if (allProductList.get(i).getProduct_id().equals(product_id)){
                class_name = allProductList.get(i).getClass().getName();
                allProductList.remove(i);
                remaining_product_count = allProductList.size();
                result = true;
                System.out.println(class_name + " Product removed successfully..!\n" + remaining_product_count + " Products remaining");
            }
        }
        //save to the file
        save_file();

        if (!result){
            System.out.println("Invalid Product ID");
        }
    }

    //printing the product list
    public void print_product_list(){

        for (int i = 1; i<allProductList.size(); i++){
            String currentVal = allProductList.get(i).getProduct_id();
            AbstractProduct currentProduct = allProductList.get(i);
            int j = i - 1;
            //move the elements that are greater than current product to the right
            while (j >= 0 && allProductList.get(j).getProduct_id().compareTo(currentVal)>0){
                allProductList.set(j+1, allProductList.get(j));
                j--;
            }
            //insert current product at the current position
            allProductList.set(j+1, currentProduct);
        }
        //printing the sorted product list
        for (int i = 0; i<allProductList.size();i++){
            //getting the class name of the object and convert it to a string
            String className = String.valueOf(allProductList.get(i).getClass());

            AbstractProduct p = allProductList.get(i);
            //print the electronic products
            if (className.equals("class Electronics")){

                System.out.println("Product type: Electronic" + " Product ID "+ p.getProduct_id() + " Product name: " + p.getProduct_name() + " Number of Available items: " + p.getNumber_of_available_items() + " Price of the Product: " + p.getPrice() + " Brand of the product: " + ((Electronics)p).getBrand() + " Warranty period of the product: " + ((Electronics)p).getWarranty_period() + "\n");
            }
            else{
                System.out.println("Product type: Clothing" + " Product ID "+ p.getProduct_id() + " Product name: " + p.getProduct_name() + " Number of Available items: " + p.getNumber_of_available_items() + " Price of the Cloth: " + p.getPrice() + " Colour of the Cloth: " + ((Clothing)p).getColour() + " Size of the Cloth: " + ((Clothing)p).getSize() + "\n");
            }
        }
    }

    //save all the added products to a file
    public void save_file(){

        try{
            FileOutputStream fos = new FileOutputStream("product_list.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(int i = 0; i<allProductList.size();i++){
                AbstractProduct product = allProductList.get(i);
                oos.writeObject(product);
            }
            oos.close();
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //getting the product details belongs to all categories
    private ArrayList<String> product_details(){

        //Initializing variables
        String productId, productName, pricePerItem, noItems;
        boolean product_found = false;
        //creating an arraylist to hold the input details
        ArrayList<String> productDetails = new ArrayList<>();

        //getting and checking the product id and check whether the product is already in the system and add it to the array list
        productId = checkStrVal("Product ID");
        for (int i = 0; i< allProductList.size();i++){
            if (productId.equals(allProductList.get(i).getProduct_id())){
                product_found = true;
                break;
            }
        }
        if (!product_found) {
            productDetails.add(productId);

            //getting and checking the product name and add it to the array list
            productName = checkStrVal("Product Name");
            productDetails.add(productName);

            //getting and checking the number of items and add it to the array list
            noItems = checkNumeric("'How many Products are available'");
            productDetails.add(noItems);

            //getting and checking the price and add it to the array list
            pricePerItem = checkNumeric("Price of the Product");
            productDetails.add(pricePerItem);

            //if the product id already in the system
        }else{
            System.out.println("Product ID already existed..!");
            return null;
        }

        return productDetails;
    }

    //checking the string values that user inputs(productId, productName, brandName)
    private String checkStrVal(String value){
        String val;
        while (true) {
            System.out.println("Enter "+ value +" : ");
            val = scanner.next();
            if (val.matches("^[A-Za-z0-9]+$")){
                break;
            }else {
                System.out.println("Invalid "+ value +"..!");
            }
        }
        return val;
    }

    //checking Integer and double values(number of items available, warranty period, price)
    private String checkNumeric(String value){
        double val;
        while(true) {
            try {
                System.out.println("Enter the "+value+" : ");
                val = scanner.nextDouble();
                break;
            } catch (Exception e) {
                System.out.println("Invalid "+value+"..!");
                scanner.nextLine();
            }
        }
        return Double.toString(val);
    }
}
