package src.validators;

import java.util.List;

import javax.swing.JOptionPane;

import src.model.InventoryItem;
import src.util.ValidatingItemNumber;

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

    /**
     * Validates the format of the item number for update.
     * 
     * @param itemNumberStr The string representation of the item number.
     * @return True if the item number format is valid, otherwise false.
     */
    public static boolean isValidItemNumberForUpdate(String itemNumberStr) {

        // Check if the input string is empty
        if (itemNumberStr.isEmpty()) {
            // Display an error message if the item number is empty
            JOptionPane.showMessageDialog(null,
                    "Item Number cannot be empty!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        // Check if the input string contains only digits
        if (!itemNumberStr.matches("\\d+")) {
            // Display an error message if the input contains non-numeric characters
            JOptionPane.showMessageDialog(null,
                    "Quantity must contain only numbers! (ex: 1, 2, 5, 10, 50, 100",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Validation failed
        }

        return true; // Validation passed
    }

    /**
     * Validate item number range for the given item type.
     *
     * @param itemType The type of the inventory item (Vases, Table Runners,
     *                 Greeneries).
     * @return The valid item number range as a string.
     */
    public static String validateItemNumberRange(String itemType) {
        switch (itemType) {
            case "Vases":
                return "100 - 199";
            case "Table Runners":
                return "200 - 299";
            case "Greeneries":
                return "300 - 399";
            default:
                return "Unknown"; // Return "Unknown" for unrecognized item types
        }
    }

    /**
     * Checks if the provided item number is valid for the given item type.
     * Valid item numbers are within specific ranges based on the item type:
     * - Vases: 100 - 199
     * - Table Runners: 200 - 299
     * - Greeneries: 300 - 399
     *
     * @param itemType   The type of the inventory item (Vases, Table Runners,
     *                   Greeneries).
     * @param itemNumber The item number to be validated.
     * @return True if the item number is valid for the given item type, false
     *         otherwise.
     */
    public static boolean isValidItemNumber(String itemType, int itemNumber) {
        switch (itemType) {
            case "Vases":
                if (itemNumber >= 100 && itemNumber < 200) {
                    return true;
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null,
                            "Invalid item number for the selected item type! " +
                                    "The item number for '"
                                    + itemType
                                    + "' must be between " +
                                    "100 and 199.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            case "Table Runners":
                if (itemNumber >= 200 && itemNumber < 300) {
                    return true;
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null,
                            "Invalid item number for the selected item type! " +
                                    "The item number for '"
                                    + itemType
                                    + "' must be between " +
                                    "200 and 299.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            case "Greeneries":
                if (itemNumber >= 300 && itemNumber < 400) {
                    return true;
                } else {
                    // Show error message
                    JOptionPane.showMessageDialog(null,
                            "Invalid item number for the selected item type! " +
                                    "The item number for '"
                                    + itemType
                                    + "' must be between " +
                                    "300 and 399.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            default:
                // Handle unknown item types gracefully
                return false;
        }
    }

    /**
     * Checks if the new updated item number is a duplicate of the current item
     * 
     * @param selectedItem         The selected item to be updated
     * @param newUpdatedItemNumber The new updated item number
     * @return True if the new updated item number is a duplicate of the current
     *         item, otherwise false.
     */
    public static boolean isDuplicateItemNumber(InventoryItem selectedItem, int newUpdatedItemNumber) {
        // Check if the new item number created is the same as the current one
        if (newUpdatedItemNumber == selectedItem.getItemNumber()) {
            JOptionPane.showMessageDialog(null,
                    "New item number matches the current item number.",
                    "No Change Detected",
                    JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit the update
        } else {
            return false; // Continue with the update
        }
    }

    /**
     * Checks if the new updated item number already exists in the inventory items
     * 
     * @param inventoryItemsList   The list of inventory items
     * @param newUpdatedItemNumber The new updated item number
     */
    public static boolean isExistingItemNumber(List<InventoryItem> inventoryItemsList, int newUpdatedItemNumber) {
        for (InventoryItem item : inventoryItemsList) {
            if (item.getItemNumber() == newUpdatedItemNumber) {
                JOptionPane.showMessageDialog(null,
                        "The entered item number already exists!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return true; // Exit method
            }
        }
        return false; // Continue method
    }
}
