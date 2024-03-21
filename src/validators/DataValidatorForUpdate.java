package src.validators;

import java.util.List;

import javax.swing.JOptionPane;

import src.model.InventoryItem;

public class DataValidatorForUpdate {

    // Method to validate the list of updated items
    public static boolean validateUpdatedItemsList(List<InventoryItem> updatedItems) {

        // Check if list is empty, if so, display message and exit method
        if (updatedItems.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "No updated items found.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return false; // Validation failed
        } else {
            return true; // Validation passed
        }
    }

    // Method to validate the user input for the item number
    public static boolean isValidItemNumberForUpdate(String itemNumberStr) {

        // Check if empty
        if (itemNumberStr.isEmpty()) {
            // Show an error message if the new item name is empty
            JOptionPane.showMessageDialog(null,
                    "Item Number cannot be empty!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Exit mthod
        }

        // Check if the quantity contains only digits
        if (!itemNumberStr.matches("\\d+")) {
            // Show an error message if the new quantity contains invalid characters
            JOptionPane.showMessageDialog(null,
                    "Quantity must contain only numbers! (ex: 1, 2, 5, 10, 50, 100",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Exit method
        }

        return true; // Validation passed
    }
}
