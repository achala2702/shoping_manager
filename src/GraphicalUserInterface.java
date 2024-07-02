import java.io.FileWriter;
import java.io.IOException;

public class GraphicalUserInterface {
    public static void main(String[] args) throws IOException {

        //clearing the text in Saved_list.txt
        try{
            //open the file in write mode with file writer
            FileWriter fileWriter;
            fileWriter = new FileWriter("Saved_list.txt");

            // Close the FileWriter immediately to clear the file content
            fileWriter.close();

            System.out.println("File content cleared successfully.");

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Error clearing file content: " + e.getMessage());
        }

        // creating an instance of ShoppingGUI and run the gui part
        ShoppingGUI shoppingCentre = new ShoppingGUI();
    }
}
