package src.gui_inventory_management;

import src.CSVReaderWriter.CSVHandler;
import src.gui_components.GUIComponentInitializer;
import src.model.InventoryItem;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class InventoryManagementSystemFrame extends JFrame {
    // List to store inventory items
    private List<InventoryItem> inventoryItemsList;

    // Constructor to initialize the GUI
    public InventoryManagementSystemFrame() {
        // Sets the title of the JFrame window
        super("Wedding Decors Inventory Management System");

        // Initialize the list of inventory items
        inventoryItemsList = new ArrayList<>();

        // Load existing data from the CSV file during startup
        CSVHandler.loadExisitingDataFromInventoryCSVDuringStartUp(inventoryItemsList);

        // Set the default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set layout for the main frame
        setLayout(new BorderLayout());

        /*
         * Initialize GUI components using the current instance of the
         * WeddingInventorySystemGUI class
         * 'this' is being used to pass the current instance of the
         * WeddingInventorySystemGUI class to initialize the GUI components.
         */
        GUIComponentInitializer.initializeGUIComponents(this, inventoryItemsList);

        // Set a specific size for the frame
        setSize(1300, 800); // Adjust the width and height as needed

        // Make the frame visible
        setVisible(true);

        // Create an Image Icon
        ImageIcon GUI_Image_Icon = new ImageIcon("imgs/GUI-Img-Icon/inventory-system.png");

        // Change icon of frame
        setIconImage(GUI_Image_Icon.getImage());

    }

}
