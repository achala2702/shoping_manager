import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class ShoppingCartGUI extends JFrame {
    //ShoppingCart shopping;
    ArrayList<AbstractProduct> product_list = new ArrayList<>();

    // Add variables for total, discount, and final total
    double total = 0;
    double discount = 0.20; // 20% discount

    private JTable table;
    private JLabel totalLabel;

    private JLabel discountLabel;

    private JLabel finalLabel;

    public ShoppingCartGUI() throws IOException {
        //shopping = shoppingCart;
        //creating a file object with the file name
        File file = new File("product_list.txt");
        if (file.exists()){
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {
                //Create a file input stream
                fis = new FileInputStream("Saved_list.txt");
                //Create a object input stream
                ois = new ObjectInputStream(fis);

                //loop until the end of the text file
                while (true) {
                    AbstractProduct p = (AbstractProduct) ois.readObject();
                    // add to the array list named as readFromFile
                    product_list.add(p);
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
            if(ois != null){
                ois.close();
            }


        }else{
            System.out.println("ERROR: File Not Found");
        }

        // Create a frame
        setTitle("Shopping Cart");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        // Create a table model without data
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Product","Quantity", "Price"});
        table = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // No need to update labels in this example
                    }
                }
            }
        });

        // Create a panel
        JPanel panel = new JPanel();
        // Set the layout to grid layout with 2 rows and 2 columns
        panel.setLayout(new GridLayout(2, 2));
        // Create and add labels to the panel
        totalLabel = new JLabel("Total: ");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(totalLabel);
        discountLabel = new JLabel("Three Items in Same Category(20%): ");
        discountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(discountLabel);
        finalLabel = new JLabel("Final Total: ");
        finalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(finalLabel);

        // Add the scroll pane and the panel to the frame
        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);

        // Update the table with shopping cart data
        updateTable();
        // Update the labels with calculated values
        updateLabels();
    }

    private void updateTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Clear existing rows

            for (int i = 0; i<product_list.size();i++) {
                AbstractProduct pro = product_list.get(i);
                System.out.println(pro);
                if (pro.getClass().getName().equals("Electronics")){
                    Electronics elect = (Electronics) pro;
                    Object[] rowData = {elect.getProduct_id() + " \n " + elect.getBrand() + " \n "+ elect.getWarranty_period(),pro.getNumber_of_available_items(),pro.getPrice()};
                    model.addRow(rowData);
                }else if (pro.getClass().getName().equals("Clothing")){
                    Clothing cloth = (Clothing) pro;
                    Object[] rowData = {cloth.getProduct_id() + " \n " + cloth.getSize() + " \n "+ cloth.getColour(),pro.getNumber_of_available_items(),pro.getPrice()};
                    model.addRow(rowData);
                }
            }
    }

    private void updateLabels() {
        int eCount = 1;
        int cCount = 1;
        double threeItemDis = 0;

        for(int i = 0;i<product_list.size();i++){
            total += product_list.get(i).getPrice();
            if(product_list.get(i).getClass().getName().equals("Electronic")){
                eCount ++;
            }
            else if(product_list.get(i).getClass().getName().equals("Clothing")){
                cCount++;
            }
        }
        double discountedTotal = total - (total * discount);
        totalLabel.setText("Total: " + String.format("%.2f", total));
        if(cCount >= 3 || eCount>=3){
            threeItemDis = total*discount;

        }
        discountLabel.setText("Three Items in Same Category(20%): " + String.format("%.2f", threeItemDis));
        finalLabel.setText("Final Total: " + String.format("%.2f", discountedTotal));
    }
}
