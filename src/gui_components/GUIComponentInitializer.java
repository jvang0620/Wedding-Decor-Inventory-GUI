package src.gui_components;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import src.CSVReaderWriter.CSVHandler;
import src.model.InventoryItem;
import src.util.GenerateReports;
import src.util.PromptForUpdateConfirmation;
import src.util.RandomNumberGenerator;
import src.util.UpdateDeletedItemsTextArea;
import src.util.UpdateInventoryTextArea;
import src.validators.DataValidator;
import src.validators.DataValidatorForUpdates;
import src.validators.DataValidatorForViewDeleted;
import src.validators.DataValidatorForViewInventory;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIComponentInitializer {

    private static List<InventoryItem> inventoryItemsList;

    // Text area to display inventory
    private static JTextArea inventoryTextArea;

    // Text area to display deleted items
    private static JTextArea deletedInventoryTextArea;

    /**
     * Initializes the GUI components for a specific part of the application and
     * adds them to the given container.
     * 
     * @param container The container to which the GUI components will be added.
     * @param itemsList The list of InventoryItem objects used to initialize certain
     *                  GUI components.
     */
    public static void initializeGUIComponents(Container container, List<InventoryItem> itemsList) {
        // Store the inventoryItemsList reference for later use
        inventoryItemsList = itemsList;

        // Panel to hold inventory-related components
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title label
        JLabel titleLabel = new JLabel("Wedding Decors Inventory Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inventoryPanel.add(titleLabel, BorderLayout.NORTH);

        // Panels to hold buttons/button sizes
        JPanel leftButtonPanel = new JPanel(new GridLayout(12, 1, 7, 7));
        JPanel rightButtonPanel = new JPanel(new GridLayout(12, 1, 7, 7));

        /*
         * Button to view inventory
         */
        JButton viewInventoryButton = new JButton("View Inventory");
        // Set button color to dodger blue
        viewInventoryButton.setBackground(new Color(30, 144, 255));
        // ActionListener for the "View Inventory Item" button
        viewInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validate the list of inventory items to see if it is empty
                if (DataValidatorForViewInventory.isInventoryItemsListEmpty(inventoryItemsList, inventoryTextArea)) {
                    return; // If true, exit method
                }

                // Update inventory text area to display current inventory
                UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);
            }
        });

        /*
         * Button to view items in the trash bin
         */
        JButton viewDeletedItemsButton = new JButton("View Deleted Items");
        // ActionListener for the "Deleted Items" button
        viewDeletedItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read items from the deleted_items.csv file
                List<InventoryItem> trashItems = CSVHandler.readItemsFromDeletedCSVFile();

                // Validate the list of deleted items to see if empty, update the text area
                if (DataValidatorForViewDeleted.isDeletedItemsListEmpty(trashItems, deletedInventoryTextArea)) {
                    return; // If true, exit method
                }

                // Update the text area with deleted items
                UpdateDeletedItemsTextArea.populateTextArea(trashItems, deletedInventoryTextArea);
            }
        });

        /**
         * Button to add inventory item
         */
        JButton createButton = new JButton("Create Inventory");
        // Set button color to lime green
        createButton.setBackground(new Color(50, 205, 50));
        // ActionListener for the "Add Inventory Item" button
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Update inventory text area to display current inventory
                UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                // Prompt user to enter item name
                String itemName = JOptionPane.showInputDialog(null, "Enter Item Name:");

                try {
                    // Check if user clicked cancel or closed the dialog
                    if (DataValidator.isInputNull(itemName)) {
                        return; // If true, exit method
                    }

                    // Trim and normalize whitespace in the item name
                    itemName = itemName.replaceAll("\\s+", " ").trim();

                    // Validate the user input is not empty
                    if (DataValidator.isInputEmpty(itemName)) {
                        return; // If true, exit method
                    }

                    // Validate the user input contains valid characters
                    if (!DataValidator.isValidItemName(itemName)) {
                        return; // If false, exit method
                    }

                    // Promte user to enter Quanity
                    String quantityString = JOptionPane.showInputDialog(null, "Enter Quantity:");

                    // Validate if user clicked cancel or closed the dialog
                    if (DataValidator.isInputNull(quantityString)) {
                        return; // If true, exit method
                    }

                    // Trim and normalize whitespace in the quantityStr
                    quantityString = quantityString.replaceAll("\\s+", " ").trim();

                    // Validate the user input is not empty
                    if (DataValidator.isInputEmpty(quantityString)) {
                        return; // If true, exit method
                    }

                    // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                    if (!DataValidator.isQuantityInputValid(quantityString)) {
                        return; // If false, exit method
                    }

                    // Parse and validate that quantity is a positive number
                    int quantity = Integer.parseInt(quantityString);
                    if (!DataValidator.isPositiveQuantity(quantity)) {
                        return; // If false, exit method
                    }

                    // Prompt user to select item type from a predefined list of options.
                    String[] types = { "Vases", "Table Runners", "Greeneries" };
                    String itemType = (String) JOptionPane.showInputDialog(null,
                            "Select Item Type:",
                            "Item Type",
                            JOptionPane.QUESTION_MESSAGE, null, types, types[0]);

                    // Check if user clicked cancel or closed the dialog
                    if (DataValidator.isInputNull(itemType)) {
                        return; // If true, exit method
                    }

                    // Generate random item number based on item type
                    int itemNumber = RandomNumberGenerator.generateRandomNumber(itemType,
                            inventoryItemsList);

                    // Get current date
                    LocalDate creationDate = LocalDate.now();

                    // Display confirmation dialog
                    String message = String.format(
                            "Item Name: %s\nQuantity: %d\nItem Type: %s\n\n"
                                    + "Are you sure you want to create this inventory item?",
                            itemName, quantity, itemType);

                    int confirmation = JOptionPane.showConfirmDialog(null, message,
                            "Confirm Addition",
                            JOptionPane.YES_NO_OPTION);

                    // If confirmation is YES
                    if (confirmation == JOptionPane.YES_OPTION) {

                        // User confirmed, add the new inventory item
                        InventoryItem newItem = new InventoryItem(itemName, quantity,
                                itemNumber, itemType, creationDate);

                        // Add new item to inventory items list
                        // Update inventory display and write to inventory.CSV
                        inventoryItemsList.add(newItem);
                        UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);
                        CSVHandler.writeCreatedItemToInventoryCSV(inventoryItemsList);

                        // Display successful message
                        JOptionPane.showMessageDialog(null, "Item Created Successfully!",
                                "Created Successful", JOptionPane.INFORMATION_MESSAGE);

                    }

                } catch (NumberFormatException ex) {
                    // Show error message if the quantity input cannot be parsed as an integer
                    JOptionPane.showMessageDialog(null, "Invalid input! Only numeric input is allowed.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Button to update inventory
         */
        JButton updateButton = new JButton("Update Inventory");
        // Set button color to Orange
        updateButton.setBackground(Color.ORANGE);
        // ActionListener for the "Update" button
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Read from inventory csv file
                List<InventoryItem> list = CSVHandler.readItemsFromInventoryCSVFile();

                try {
                    // Validate if the list is empty
                    if (DataValidator.isCSVFileEmpty(list)) {
                        return; // If true, exit method
                    }

                    // Update inventory text area to display current inventory
                    UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                    // Prompt user to enter the item number for the item to be updated
                    String itemNumberString = JOptionPane.showInputDialog(null, "Enter Item Number to Update:");

                    // Validate if user clicked cancel or closed the dialog
                    if (DataValidator.isInputNull(itemNumberString)) {
                        return; // If true, exit method
                    }

                    // Trim and normalize whitespace in the item Number String
                    itemNumberString = itemNumberString.replaceAll("\\s+", " ").trim();

                    // Validate the user input is not empty
                    if (DataValidator.isInputEmpty(itemNumberString)) {
                        return; // If true, exit method
                    }

                    // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                    if (!DataValidator.isQuantityInputValid(itemNumberString)) {
                        return; // If false, exit method
                    }

                    // Parse and validate that quantity is a positive number
                    int itemNumberToUpdate = Integer.parseInt(itemNumberString);
                    if (!DataValidator.isPositiveQuantity(itemNumberToUpdate)) {
                        return; // If false, exit method
                    }

                    // Create variable
                    InventoryItem selectedItem = null;

                    // Iterate thorugh list and find the item to update, if not found, exit loops
                    for (InventoryItem item : inventoryItemsList) {
                        if (item.getItemNumber() == itemNumberToUpdate) {
                            selectedItem = item;
                            break; // selectedItem will remain 'null' if the item number is not found
                        }
                    }

                    // Validate if the item number exists
                    if (!DataValidator.isItemNumberFound(selectedItem, itemNumberToUpdate)) {
                        return; // If false, exit the method
                    }

                    // Display a dialog to select the field to update
                    String[] updateOptions = { "Item Number", "Item Type", "Item Name", "Quantity" };

                    // Prompt user to select a field to update
                    String selectedOption = (String) JOptionPane.showInputDialog(null,
                            "Select which field to update:",
                            "Update Item", JOptionPane.QUESTION_MESSAGE, null, updateOptions,
                            updateOptions[0]);

                    // Validate if a field to update is selected
                    if (selectedOption != null) {
                        switch (selectedOption) {
                            case "Item Number":
                                // Prompt user to enter a new value for the item number
                                String newItemNumberString = JOptionPane.showInputDialog(null,
                                        "Enter new value for Item Number:",
                                        selectedItem.getItemNumber());

                                try {
                                    // Validate if user clicked cancel or closed the dialog
                                    if (DataValidator.isInputNull(newItemNumberString)) {
                                        return; // If true, exit method
                                    }

                                    // Trim and normalize whitespace in the newItemNumberStr
                                    newItemNumberString = newItemNumberString.replaceAll("\\s+", " ").trim();

                                    // Validate the user input is not empty
                                    if (DataValidator.isInputEmpty(newItemNumberString)) {
                                        return; // If true, exit method
                                    }

                                    // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                                    if (!DataValidator.isQuantityInputValid(newItemNumberString)) {
                                        return; // If false, exit method
                                    }

                                    // Parse and validate that quantity is a positive number
                                    int newUpdatedItemNumber = Integer.parseInt(newItemNumberString);
                                    if (!DataValidator.isPositiveQuantity(newUpdatedItemNumber)) {
                                        return; // If false, exit method
                                    }

                                    // Validate that the new updated item number is valid
                                    if (!DataValidatorForUpdates.isUpdatedItemNumberValid(selectedItem.getItemType(),
                                            newUpdatedItemNumber)) {
                                        return; // Exit method if validation fails
                                    }

                                    // Validate if the new updated item number is a duplicate
                                    if (DataValidatorForUpdates.isUpdatedItemNumberDuplicate(selectedItem,
                                            newUpdatedItemNumber)) {
                                        return; // Exit method if validation passes
                                    }

                                    // Validate if the new updated item number is an existing item number
                                    if (DataValidatorForUpdates.doesUpdatedItemNumberExist(itemsList,
                                            newUpdatedItemNumber)) {
                                        return; // Exit method if validation passes
                                    }

                                    // Show confirmation dialog for the update
                                    PromptForUpdateConfirmation.showUpdateConfirmationDialog(selectedItem,
                                            selectedOption,
                                            newUpdatedItemNumber, inventoryItemsList, inventoryTextArea);

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid entry!", "Error", JOptionPane.ERROR_MESSAGE);
                                }

                                break;

                            case "Item Type":
                                // Display a dropdown list of current item types
                                String[] currentTypes = { "Greeneries", "Table Runners", "Vases" };

                                // Prompt user to select a new item type
                                String newType = (String) JOptionPane.showInputDialog(null,
                                        "Select new type for the item:",
                                        "Update Item Type",
                                        JOptionPane.QUESTION_MESSAGE, null, currentTypes,
                                        selectedItem.getItemType());
                                try {
                                    // Validate if user clicked cancel or closed the dialog
                                    if (DataValidator.isInputNull(newType)) {
                                        return; // If true, exit method
                                    }

                                    // Validate if the new updated item type is the same as the current item type
                                    if (DataValidatorForUpdates.isSameItemType(selectedItem, newType)) {
                                        return; // If true, exit method
                                    }

                                    // Show confirmation dialog for the update
                                    PromptForUpdateConfirmation.showUpdateConfirmationDialog(selectedItem,
                                            selectedOption, newType, inventoryItemsList, inventoryTextArea);

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid entry!", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                                break;

                            case "Item Name":
                                // Prompt user to enter a new value for the item name
                                String newUpdatedItemName = JOptionPane.showInputDialog(null,
                                        "Enter new value for Item Name:",
                                        selectedItem.getItemName());
                                try {

                                    // Validate if user clicked cancel or closed the dialog
                                    if (DataValidator.isInputNull(newUpdatedItemName)) {
                                        return; // If true, exit method
                                    }

                                    // Trim and normalize whitespace in the newUpdatedItemName
                                    newUpdatedItemName = newUpdatedItemName.replaceAll("\\s+", " ").trim();

                                    // Validate the user input is not empty
                                    if (DataValidator.isInputEmpty(newUpdatedItemName)) {
                                        return; // If true, exit method
                                    }

                                    // Validate if the new updated item name is the same as the current item name
                                    if (DataValidatorForUpdates.isSameItemName(selectedItem, newUpdatedItemName)) {
                                        return; // If true, exit method
                                    }

                                    // Validate if the new updated item name contains only valid characters
                                    if (!DataValidator.isValidItemName(newUpdatedItemName)) {
                                        return; // If false, exit method
                                    }

                                    // Show confirmation dialog for the update
                                    PromptForUpdateConfirmation.showUpdateConfirmationDialog(selectedItem,
                                            selectedOption, newUpdatedItemName, inventoryItemsList, inventoryTextArea);

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid entry!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                                break;

                            case "Quantity":
                                // Prompt user to enter a new value for the quantity
                                String newQuantityStr = JOptionPane.showInputDialog(null,
                                        "Enter new value for Quantity:",
                                        selectedItem.getQuantity());

                                try {
                                    // Check if user clicked cancel or closed the dialog
                                    if (DataValidator.isInputNull(newQuantityStr)) {
                                        return; // if true, exit method
                                    }

                                    // Trim and normalize whitespace in the newQuantityStr
                                    newQuantityStr = newQuantityStr.replaceAll("\\s+", " ").trim();

                                    // Validate the user input is not empty
                                    if (DataValidator.isInputEmpty(newQuantityStr)) {
                                        return; // if true, exit method
                                    }

                                    // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                                    if (!DataValidator.isQuantityInputValid(newQuantityStr)) {
                                        return; // if false, exit method
                                    }

                                    // Parse and validate that quantity is a positive number
                                    int newQuantity = Integer.parseInt(newQuantityStr);
                                    if (!DataValidator.isPositiveQuantity(newQuantity)) {
                                        return; // If false, exit method
                                    }

                                    // Validate if new quantity is the same as the current quantity;
                                    if (DataValidatorForUpdates.isSameQuantity(selectedItem, newQuantity)) {
                                        return; // if true, exit method
                                    }

                                    // Show confirmation dialog for the update
                                    PromptForUpdateConfirmation.showUpdateConfirmationDialog(selectedItem,
                                            selectedOption, newQuantity, inventoryItemsList, inventoryTextArea);

                                } catch (NumberFormatException ex) {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid entry!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                                break;
                        }
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid entry!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }

        });

        /**
         * Button to delete inventory
         */
        JButton deleteInventoryButton = new JButton("Delete Inventory");
        // Set button color to Light Red
        deleteInventoryButton.setBackground(new Color(255, 153, 153));
        // ActionListener for the "Delete Inventory" button
        deleteInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Read from inventory csv file
                List<InventoryItem> list = CSVHandler.readItemsFromInventoryCSVFile();

                try {
                    // Validate if the list is empty
                    if (DataValidator.isCSVFileEmpty(list)) {
                        return; // If true, exit method
                    }

                    // Update inventory text area to display current inventory
                    UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                    // Prompt the user to enter the item number to delete
                    String itemNumberString = JOptionPane.showInputDialog(null, "Enter Item Number to Delete:");

                    // Validate if user clicked cancel or closed the dialog
                    if (DataValidator.isInputNull(itemNumberString)) {
                        return; // If true, exit method
                    }

                    // Trim and normalize whitespace in the itemNumberStr
                    itemNumberString = itemNumberString.replaceAll("\\s+", " ").trim();

                    // Validate if input is empty
                    if (DataValidator.isInputEmpty(itemNumberString)) {
                        return; // If true, exit method
                    }

                    // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                    if (!DataValidator.isQuantityInputValid(itemNumberString)) {
                        return; // If false, exit method
                    }

                    // Parse and validate that quantity is a positive number
                    int itemNumberToDelete = Integer.parseInt(itemNumberString);
                    if (!DataValidator.isPositiveQuantity(itemNumberToDelete)) {
                        return; // If false, exit method
                    }

                    // Create variable
                    InventoryItem itemToDelete = null;

                    // Iterate thorugh list and find the item to delete, if not found, exit loops
                    for (InventoryItem item : inventoryItemsList) {
                        if (item.getItemNumber() == itemNumberToDelete) {
                            itemToDelete = item;
                            break; // selectedItem will remain 'null' if the item number is not found
                        }
                    }

                    // Validate if the item number exists
                    if (!DataValidator.isItemNumberFound(itemToDelete, itemNumberToDelete)) {
                        return; // If false, exit the method
                    }

                    // Display a confirmation dialog with item details before deletion
                    int confirmDelete = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete this item?\n\n" +
                                    "Item Number: " + itemToDelete.getItemNumber() + "\n" +
                                    "Item Name: " + itemToDelete.getItemName() + "\n" +
                                    "Quantity: " + itemToDelete.getQuantity() + "\n" +
                                    "Item Type: " + itemToDelete.getItemType() + "\n" +
                                    "Create Date: "
                                    + itemToDelete.getCreationDate()
                                            .format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                                    + "\n\n",
                            "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                    // If the user confirms deletion
                    if (confirmDelete == JOptionPane.YES_OPTION) {
                        // Write the item details to the CSV file for deleted items
                        CSVHandler.writeDeletedItemToCSV(itemToDelete);
                        // Remove the item from the main inventory list
                        inventoryItemsList.remove(itemToDelete);
                        // Update the inventory text area
                        UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);
                        // Update the text area with deleted items
                        UpdateDeletedItemsTextArea.populateTextArea(
                                CSVHandler.readItemsFromDeletedCSVFile(),
                                deletedInventoryTextArea);
                        // Write created item to Inventory.csv
                        CSVHandler.writeCreatedItemToInventoryCSV(inventoryItemsList);

                        // Display success deletion message
                        JOptionPane.showMessageDialog(null, "Item Deleted Successfully!",
                                "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (NumberFormatException ex) {
                    // Notify the user if an invalid item number is entered
                    JOptionPane.showMessageDialog(null, "Invalid entry!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            }
        });

        /**
         * Button to permanently delete items
         */
        JButton permanentDeleteButton = new JButton("Permanent Delete");
        // ActionListener for the "Permanent Delete" button
        permanentDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read from deleted item csv file and set to list
                List<InventoryItem> deletedItemsList = CSVHandler.readItemsFromDeletedCSVFile();

                // Validate if CSV file is empty
                if (DataValidator.isCSVFileEmpty(deletedItemsList)) {
                    return; // If true, exit method
                }

                // Update the text area with deleted items
                UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList, deletedInventoryTextArea);

                // Prompt the user to choose between deleting one item, all items, or cancel
                Object[] options = { "One Item", "All Items", "Cancel" };
                int choice = JOptionPane.showOptionDialog(null,
                        "Do you want to delete ONE ITEM, ALL ITEMS, or Cancel?",
                        "Delete Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                // Perform actions based on user choice
                switch (choice) {
                    case JOptionPane.YES_OPTION: // Permanently delete one item
                        String itemNumberString = JOptionPane.showInputDialog(null,
                                "Enter the item number to PERMANENTLY DELETE:");

                        try {
                            // Validate if user clicked cancel or closed the dialog
                            if (DataValidator.isInputNull(itemNumberString)) {
                                return; // If true, exit method
                            }

                            // Trim and normalize whitespace in the itemNumberStr
                            itemNumberString = itemNumberString.replaceAll("\\s+", " ").trim();

                            // Validate if input is empty
                            if (DataValidator.isInputEmpty(itemNumberString)) {
                                return; // If true, exit method
                            }

                            // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                            if (!DataValidator.isQuantityInputValid(itemNumberString)) {
                                return; // If false, exit method
                            }

                            // Parse and validate that quantity is a positive number
                            int itemNumberToDeletePermanently = Integer.parseInt(itemNumberString);
                            if (!DataValidator.isPositiveQuantity(itemNumberToDeletePermanently)) {
                                return; // If false, exit method
                            }

                            // Read from deleted item csv file and set to list
                            deletedItemsList = CSVHandler.readItemsFromDeletedCSVFile();

                            // Create variables
                            InventoryItem itemToDelete = null;

                            // Iterate thorugh list and find the item to delete, if not found, exit loops
                            for (InventoryItem item : deletedItemsList) {
                                if (item.getItemNumber() == itemNumberToDeletePermanently) {
                                    itemToDelete = item;
                                    break; // selectedItem will remain 'null' if the item number is not found
                                }
                            }

                            // Validate if the item number exists
                            if (!DataValidator.isItemNumberFound(itemToDelete, itemNumberToDeletePermanently)) {
                                return; // If false, exit the method
                            }

                            int confirmDelete = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to delete this item PERMANENTLY?\n\n" +
                                            "Item Number: " + itemToDelete.getItemNumber() + "\n" +
                                            "Item Name: " + itemToDelete.getItemName() + "\n" +
                                            "Quantity: " + itemToDelete.getQuantity() + "\n" +
                                            "Item Type: " + itemToDelete.getItemType() + "\n" +
                                            "Create Date: "
                                            + itemToDelete.getCreationDate()
                                                    .format(DateTimeFormatter.ofPattern("MMMM d, yyyy"))
                                            + "\n\n",
                                    "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                            // if confrim yes
                            if (confirmDelete == JOptionPane.YES_OPTION) {

                                // Remove item from deleted item list
                                deletedItemsList.remove(itemToDelete);

                                // Upddate deleted items text area with list
                                UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList,
                                        deletedInventoryTextArea);

                                // Write items from deleted items list to deleted items csv file
                                CSVHandler.writeItemsToDeletedItemsCSV(deletedItemsList);

                                // Display success deletion message
                                JOptionPane.showMessageDialog(null, "Item Permanently Deleted Successfully!",
                                        "Permanent Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid entry!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        break;

                    case JOptionPane.NO_OPTION: // Permanently delete all items
                        int confirmDeleteAll = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delete ALL items PERMANENTLY?",
                                "Confirm Deletion", JOptionPane.YES_NO_OPTION);

                        if (confirmDeleteAll == JOptionPane.YES_OPTION) {
                            // Clear the list
                            deletedItemsList.clear();
                            // Update cleared list to text area
                            UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList,
                                    deletedInventoryTextArea);
                            // Write cleared list to delete item csv file
                            CSVHandler.writeItemsToDeletedItemsCSV(deletedItemsList);

                            // Display success deletion message
                            JOptionPane.showMessageDialog(null, "All Items Deleted Successfully!",
                                    "Permanent Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
                        }

                        break;

                    default: // Cancel
                        break;
                }
            }
        });

        /**
         * Button to restore item
         */
        JButton restoreButton = new JButton("Restore");
        // ActionListener for the "Restore Item" button
        restoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Read from deleted item csv file and set to list
                List<InventoryItem> deletedItemsList = CSVHandler.readItemsFromDeletedCSVFile();

                // Validate if CSV file is empty
                if (DataValidator.isCSVFileEmpty(deletedItemsList)) {
                    return; // If true, exit method
                }

                // Update the text area with deleted items
                UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList, deletedInventoryTextArea);

                // Prompt the user to choose between restoring one item, all items, or cancel
                Object[] options = { "Restore One Item", "Restore All Items", "Cancel" };
                int choice = JOptionPane.showOptionDialog(null,
                        "Do you want to restore ONE ITEM, ALL ITEMS, or Cancel?",
                        "Restore Options", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);

                // Perform actions based on user choice
                switch (choice) {
                    case JOptionPane.YES_OPTION: // Restore one item
                        String itemNumberString = JOptionPane.showInputDialog(null,
                                "Enter the item number to restore:");
                        try {

                            // Validate if user selected 'cancel' or closed the dialog
                            if (DataValidator.isInputNull(itemNumberString)) {
                                return; // If true, exit method
                            }

                            // Trim and normalize whitespace in the input
                            itemNumberString = itemNumberString.replaceAll("\\s+", " ").trim();

                            if (DataValidator.isInputEmpty(itemNumberString)) {
                                return; // If true, exit method
                            }

                            // Validate the user input contains valid characters (ex: 1, 5, 100, 5000)
                            if (!DataValidator.isQuantityInputValid(itemNumberString)) {
                                return; // If false, exit method
                            }

                            // Parse and validate that quantity is a positive number
                            int itemNumber = Integer.parseInt(itemNumberString);
                            if (!DataValidator.isPositiveQuantity(itemNumber)) {
                                return; // If false, exit method
                            }

                            // Create variablea
                            InventoryItem itemToRestore = null;

                            // Iterate thorugh list and find the item to restore, if not found, exit loops
                            for (InventoryItem item : deletedItemsList) {
                                if (item.getItemNumber() == itemNumber) {
                                    itemToRestore = item;
                                    break; // selectedItem will remain 'null' if the item number is not found
                                }
                            }

                            // Validate if the item number exists
                            if (!DataValidator.isItemNumberFound(itemToRestore, itemNumber)) {
                                return; // If false, exit the method
                            }

                            // Remove the item from deleted items list
                            deletedItemsList.remove(itemToRestore);

                            // Write the updated list back to deleted_items.csv
                            CSVHandler.writeItemsToDeletedItemsCSV(deletedItemsList);

                            // Add the item back to inventory.csv
                            CSVHandler.writeRestoredItemToCSV(itemToRestore);

                            // Add the item back to inventoryItem
                            inventoryItemsList.add(itemToRestore);

                            // Update the GUI
                            UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList,
                                    deletedInventoryTextArea);
                            // Update inventory text area
                            UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                            // Message stating restoration is successful
                            JOptionPane.showMessageDialog(null, "Item restored successfully!",
                                    "Restore Successful", JOptionPane.INFORMATION_MESSAGE);

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid item number!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        break;

                    case JOptionPane.NO_OPTION: // Restore all items
                        int confirmRestoreAll = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to restore ALL items?",
                                "Confirm Restoration", JOptionPane.YES_NO_OPTION);

                        if (confirmRestoreAll == JOptionPane.YES_OPTION) {

                            // Restore all items
                            for (InventoryItem itemToRestore : deletedItemsList) {
                                // Write to Inventory.csv
                                CSVHandler.writeRestoredItemToCSV(itemToRestore);
                                // Add the item back to inventoryItem
                                inventoryItemsList.add(itemToRestore);
                            }

                            // Clear the deletedItems list and write the updated list back to
                            // deleted_items.csv
                            deletedItemsList.clear();
                            CSVHandler.writeItemsToDeletedItemsCSV(deletedItemsList);

                            // Update the GUI and show a message dialog
                            UpdateDeletedItemsTextArea.populateTextArea(deletedItemsList,
                                    deletedInventoryTextArea);
                            // Update Invenotry Text Area
                            UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                            // Display success message
                            JOptionPane.showMessageDialog(null, "All items restored successfully!",
                                    "Restore Successful", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;

                    default: // Cancel button selected
                        break;
                }
            }
        });

        /**
         * Button to generate inventory report
         */
        JButton generateReportButton = new JButton("Generate Report");
        // Set button color to light yellow
        generateReportButton.setBackground(new Color(255, 255, 153));
        // ActionListener for the "Generate Report" button
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Validate if the list is empty
                if (DataValidator.isCSVFileEmpty(itemsList)) {
                    return; // If true, exit method
                }

                // Update inventory text area to display current inventory
                UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

                // Ask the user for confirmation
                int confirmChoice = JOptionPane.showConfirmDialog(null,
                        "Are you sure you want to generate the report? \nIf you select 'Yes', the report will be generate to your download folder.",
                        "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmChoice == JOptionPane.YES_OPTION) {
                    // Prompt user for sorting preference
                    String[] sortOptions = { "Alphabetically", "By Quantity", "By Item Number" };
                    Object selectedOption = JOptionPane.showInputDialog(null,
                            "Select sorting preference:", "Sort Preference",
                            JOptionPane.QUESTION_MESSAGE, null, sortOptions, sortOptions[0]);

                    // Check if the user selected an option
                    if (selectedOption != null) {
                        String sortChoice = (String) selectedOption;

                        // Generate report based on user's sorting preference
                        String reportContent;
                        switch (sortChoice) {
                            case "By Quantity":
                                reportContent = GenerateReports
                                        .generateReportContentSortedByQuantity(inventoryItemsList);
                                break;
                            case "By Item Number":
                                reportContent = GenerateReports
                                        .generateReportContentSortedByItemNumber(inventoryItemsList);
                                break;
                            default:
                                reportContent = GenerateReports.generateReportSortedAlphabetically(inventoryItemsList);
                                break;
                        }

                        String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";
                        String fileName = "inventory_report_"
                                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";
                        String filePath = downloadFolderPath + fileName;
                        try {
                            PrintWriter writer = new PrintWriter(new FileWriter(filePath));
                            writer.println(reportContent);
                            writer.close();
                            JOptionPane.showMessageDialog(null, "Report generated and saved to:\n" + filePath);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Error saving report!", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });

        /**
         * Button to exit the program
         */
        JButton exitButton = new JButton("Exit Program");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Prompt the user for confirmation before exiting the program
                int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit the program?",
                        "Exit Program", JOptionPane.YES_NO_OPTION);
                // If the user confirms, exit the program
                if (confirmation == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        // Add buttons to the button panel
        leftButtonPanel.add(viewInventoryButton);
        leftButtonPanel.add(createButton);
        leftButtonPanel.add(updateButton);
        leftButtonPanel.add(deleteInventoryButton);
        leftButtonPanel.add(generateReportButton);
        leftButtonPanel.add(exitButton);

        // Add buttons to the right button panel
        rightButtonPanel.add(viewDeletedItemsButton);
        rightButtonPanel.add(restoreButton);
        rightButtonPanel.add(permanentDeleteButton);

        // Text area to display inventory data
        inventoryTextArea = new JTextArea();
        JScrollPane inventoryScrollPane = new JScrollPane(inventoryTextArea);

        // Text area to display deleted items
        deletedInventoryTextArea = new JTextArea();
        JScrollPane deletedInventoryScrollPane = new JScrollPane(deletedInventoryTextArea);

        // Create a panel to hold the inventory and deleted items text areas
        JPanel textAreasPanel = new JPanel(new GridLayout(1, 2)); // 1 row, 2 columns
        textAreasPanel.add(inventoryScrollPane);
        textAreasPanel.add(deletedInventoryScrollPane);

        // Add the panel with text areas to the center of the inventory panel
        inventoryPanel.add(textAreasPanel, BorderLayout.CENTER);

        // Add button panels to the inventory panel
        inventoryPanel.add(leftButtonPanel, BorderLayout.WEST);
        inventoryPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Add inventory panel to the main frame
        container.add(inventoryPanel, BorderLayout.CENTER);
    }
}
