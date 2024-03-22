package src.validators;

import java.util.List;
import javax.swing.JOptionPane;

import src.model.InventoryItem;

public class DataValidator {

    /**
     * Method to validate if CSV file is empty
     * 
     * @param list - the list from the CSV file
     * @return - true if the list frpm the CSV file is empty, false if not
     */
    public static boolean isCSVFileEmpty(List<InventoryItem> list) {
        // Check if list is empty, if so, display message and exit method
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No items found.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit Method
        } else {
            return false; // Continue method
        }
    }

    /**
     * Method to validate if user selected 'cancel' or closed the dialog
     * 
     * @param input - the input to validate
     * @return - true if the user selected 'cancel' or closed the dialog,
     *         false if not
     */
    public static boolean isInputNull(String input) {
        if (input == null) {
            return true; // Exit Method
        } else {
            return false; // Continue Method
        }
    }

    /**
     * Method to validate if input is empty
     * 
     * @param inputString - the input to validate
     * @return - true if the input is empty, false if not
     */
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
     * Method to validate the quantity input
     * 
     * @param quantityString - the quantity input to validate (ex: 1, 5, 100, 5000)
     * @return - true if the quantity input is valid, false if not
     */
    public static boolean isQuantityInputValid(String quantityString) {
        if (quantityString.matches("\\d+")) {
            return true; // Continue Method
        } else {
            JOptionPane.showMessageDialog(null,
                    "Quantity input must contain only numbers! (ex: 1, 2, 5, 10, 50, 100)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Exit Method
        }
    }

    /**
     * Method to validate that quantity is a positive number
     * 
     * @param quantity - the quantity to validate
     * @return - true if the quantity is a positive number, false if not
     */
    public static boolean isPositiveQuantity(int quantity) {
        // Check if quantity is 1 or greater
        if (quantity >= 1) {
            return true; // Validation passed
        } else {
            // Show error message if the quantity is not within a reasonable range
            JOptionPane.showMessageDialog(null,
                    "Quantity must be a positive number! (ex: 1, 2, 5, 10, 50, 100)",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // // Validation failed
        }
    }

    /**
     * Method to validate if the new item name contains only valid characters (A-Z,
     * a-z, or digits 0-9)
     * 
     * @param itemName The new item name
     * @return True if the new item name contains only valid characters,
     *         otherwise false
     */
    public static boolean isValidItemName(String itemName) {
        // Ensure input contains only valid characters (A-Z, a-z. (0-9))
        if (itemName.matches("[a-zA-Z0-9\\s]+")) {
            return true; // Continue method
        } else {
            // Show error message if the item name contains invalid characters
            JOptionPane.showMessageDialog(null,
                    "Invalid characters in item name! Only alphanumeric characters (A-Z, a-z) or digits (0-9).",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Exit method
        }
    }

    /**
     * Method to validate the user input for the item number
     * 
     * @param selectedItem - the selected item
     * @param itemNumber   - the item number to validate
     * @return - true if the item number is found, false if not
     */
    public static boolean isItemNumberFound(InventoryItem selectedItem, int itemNumber) {
        // If the item number does not exist
        if (selectedItem == null) {
            JOptionPane.showMessageDialog(null, "Item not found! Item number " + itemNumber + " does not exist.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false; // Exit fails
        } else {
            return true; // Validation passed
        }
    }
}
