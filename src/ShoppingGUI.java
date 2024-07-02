import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ShoppingGUI extends JFrame implements ActionListener{

    //arraylist that holds the all the items in the inventory
    ArrayList<AbstractProduct> storeProducts = new ArrayList<>();
    ArrayList<AbstractProduct> selectedList = new ArrayList<>();


    private JLabel categoryLabel;
    private JComboBox<String> categoryComboBox;
    private JButton cartButton;
    private JTable table;
    private JLabel productIdLabel;
    private JLabel categoryLabel2;
    private JLabel nameLabel;
    private JLabel sizeLabel;
    private JLabel colorLabel;
    private JLabel itemsLabel;
    private JButton addButton;

    // Constructor to initialize the components and add them to the frame
    public ShoppingGUI() throws IOException {

        //creating a file object with the file name
        File file = new File("product_list.txt");
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
                    storeProducts.add(p);
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

        // Set the title and size of the frame
        setTitle("WestMinster Shopping Centre");
        setSize(850, 800);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a panel for the top components
        JPanel topPanel = new JPanel();
        // Use a flow layout with center alignment and gaps
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER,150,10));

        // Create and add the category label and combo box to the top panel
        categoryLabel = new JLabel("Select Product Category");
        topPanel.add(categoryLabel);

        String[] categories = {"All", "Electronics", "Clothing"};
        categoryComboBox = new JComboBox<>(categories);
        topPanel.add(categoryComboBox);

        // Create and add the cart button to the top panel
        cartButton = new JButton("Shopping Cart");
        cartButton.setPreferredSize(new Dimension(120,40));
        //remove the box covers the text inside the button
        cartButton.setFocusable(false);
        cartButton.addActionListener(this);
        topPanel.add(cartButton);

        // create a panel for the table
        JPanel tablePanel = new JPanel();
// use a border layout with some padding
        tablePanel.setLayout(new BorderLayout(10, 20));


// create a table
        String[] columnNames = {"Product ID", "Name", "Category", "Price(€)", "Info"};
        Object[][] data = new Object[50][5];

        for (int i = 0; i<storeProducts.size();i++){

            //get the object at the current index
            AbstractProduct p = storeProducts.get(i);
            //assign to the data array
            data[i][0] = p.getProduct_id();
            data[i][1] = p.getProduct_name();
            data[i][2] = p.getClass().getName();
            data[i][3] = p.getPrice();
            if (p.getClass().getName().equals("Electronics")){
                Electronics elect = (Electronics) p;
                data[i][4] = elect.getBrand() + " " + elect.getWarranty_period();
            }
            else{
                Clothing cloth = (Clothing) p;
                data[i][4] = cloth.getSize() + " " + cloth.getColour();
            }

        }
// create a custom table model that prevents editing
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
// create a table using the custom model
        table = new JTable(model);
// disable column reordering
        table.getTableHeader().setReorderingAllowed(false);
// add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
// add the scroll pane to the panel
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the bottom components
        JPanel bottomPanel = new JPanel();

        // Use a grid layout with 6 rows and 1 column
        bottomPanel.setLayout(new GridLayout(3,2));

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            //updating the selected product details
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()){
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow !=-1){
                        updateLabels(selectedRow);
                    }
                }

            }

        });

        // Create and add the labels and button to the bottom panel
        productIdLabel = new JLabel("Product Id: ");
        bottomPanel.add(productIdLabel);

        categoryLabel2 = new JLabel("Category: ");
        bottomPanel.add(categoryLabel2);

        nameLabel = new JLabel("Name: ");
        bottomPanel.add(nameLabel);

        sizeLabel = new JLabel("Size: ");
        bottomPanel.add(sizeLabel);

        colorLabel = new JLabel("Colour: ");
        bottomPanel.add(colorLabel);

        itemsLabel = new JLabel("Items Available: ");
        bottomPanel.add(itemsLabel);

        // Create and add the add button to the bottom panel
        addButton = new JButton("Add to Shopping Cart");
        addButton.setPreferredSize(new Dimension(120,40));

        //adding an action listener to the button
        addButton.addActionListener(this);

        bottomPanel.add(addButton);

        // Add some padding to the bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Add the panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set the frame to be visible and exit on close
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cartButton){
            dispose();
            try {
                new ShoppingCartGUI();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        else if (e.getSource() == addButton){

            int selectedRow = table.getSelectedRow();
            AbstractProduct selectProduct = storeProducts.get(selectedRow);
            if(selectedRow !=-1) {
                selectedList.add(selectProduct);
                writeToFile();
            }
            else{
                System.out.println("no row selected..!");
            }
        }
    }
    public void writeToFile(){
        try{
            FileOutputStream fos = new FileOutputStream("Saved_list.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            for(int i = 0; i<selectedList.size();i++){
                AbstractProduct product = selectedList.get(i);
                oos.writeObject(product);
            }
            oos.close();
            fos.close();
        }catch(Exception e){
        e.printStackTrace();
        }
    }
    private void updateLabels(int selectedRow){
        AbstractProduct selectProduct = storeProducts.get(selectedRow);
        productIdLabel.setText("Product Id: " + selectProduct.getProduct_id());
        categoryLabel2.setText("Category: " + selectProduct.getClass().getName());
        nameLabel.setText("Name: " + selectProduct.getProduct_name());

        //if the product is a cloth
        if(selectProduct.getClass().getName().equals("Clothing")){
            Clothing cloth = (Clothing) selectProduct;
            colorLabel.setText("Colour: " + cloth.getColour());
            sizeLabel.setText("Size: " + cloth.getSize());
        }
        //if the product is an electronic
        else{
            Electronics elect = (Electronics) selectProduct;
            colorLabel.setText("Warranty Period: " + elect.getWarranty_period());
            sizeLabel.setText("Brand: " + elect.getBrand());

        }
        itemsLabel.setText("No Items Available: " + selectProduct.getNumber_of_available_items());


    }
}
