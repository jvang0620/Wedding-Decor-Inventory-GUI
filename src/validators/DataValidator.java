package src.validators;

import java.util.List;
import javax.swing.JOptionPane;
import src.model.InventoryItem;

public class DataValidator {

    // Method to validate the list of inventory items
    public static void validateInventoryItemsList(List<InventoryItem> inventoryItemsList) {

        // check if list is empty, if so, display message and exit method
        if (inventoryItemsList.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "There are no items in the inventory to display.",
                    "No Items", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    // Method to validate the list of deleted items
    public static void validateDeletedItemsList(List<InventoryItem> deletedItems) {

        // check if list is empthy, if so, display messages and exit method
        if (deletedItems.isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "No deleted items found.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
    }

    // Method to check if user selected 'cancel' or closed the dialog
    public static boolean isInputNull(String input) {
        if (input == null) {
            return true; // Exit Method
        } else {
            return false; // Continue Method
        }
    }

    public static boolean isInputEmpty(String inputString) {
        if (inputString.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Empty input! Please fill out required field.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return true; // Exit Method
        } else {
            return false; // Continue Method
        }
    }

    /**
     * Method to validate the quantity string
     * 
     * @param quantityString - the quantity string to validate (ex: 1, 5, 100, 5000)
     * @return - true if the quantity is valid, false if not
     */
    public static boolean isQuantityValid(String quantityString) {
        if (quantityString.matches("\\d+")) {
            return true; // Continue Method
        } else {
            JOptionPane.showMessageDialog(null,
                    "Quantity must contain only numbers! (ex: 1, 2, 5, 10, 50, 100)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Exit Method
        }
    }

    // Method to validate the user input
    public static boolean isValidItemNameForCreate(String itemNumberStr) {

        // Check if empty
        if (itemNumberStr.isEmpty()) {
            // Show an error message if the new item name is empty
            JOptionPane.showMessageDialog(null,
                    "Input cannot be empty!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        // Ensure input contains only valid characters (A-Z, a-z. (0-9))
        if (!itemNumberStr.matches("[a-zA-Z0-9\\s]+")) {
            // Show error message if the input contains invalid characters
            JOptionPane.showMessageDialog(null,
                    "Invalid characters in input! Only alphanumeric characters (A-Z, a-z) or digits (0-9).",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        return true; // Validation passed
    }

    // Method to validate the user input for the quantity
    public static boolean isValidQuantityInput(String quantityStr) {

        // Check if empty
        if (quantityStr.isEmpty()) {
            // Show error message if the quantity is empty
            JOptionPane.showMessageDialog(null, "Quantity cannot be empty!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        // Check if the quantity contains only digits
        if (!quantityStr.matches("\\d+")) {
            // Show an error message if the new quantity contains invalid characters
            JOptionPane.showMessageDialog(null,
                    "Quantity must contain only numbers! (ex: 1, 2, 5, 10, 50, 100)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        return true; // Validation passed
    }

    // Method to validate that quantity is a positive number
    public static boolean isPositiveQuantity(int quantity) {

        // Check if quantity is negative or zero
        if (quantity <= 0) {
            // Show error message if the quantity is not within a reasonable range
            JOptionPane.showMessageDialog(null,
                    "Quantity must be a positive number! (ex: 1, 2, 5, 10, 50, 100)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // // Validation failed
        }
        return true; // Validation passed
    }

    // Method to validate the user input for the item number
    public static boolean isItemNumberFound(InventoryItem selectedItem, int itemNumberToUpdate) {
        // If the item number does not exist
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(null, "Item number " + itemNumberToUpdate + " does not exist",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Exit fails
        } else {
            return true; // Validation passed
        }
    }
}
