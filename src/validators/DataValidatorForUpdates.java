package src.validators;

import java.util.List;
import javax.swing.JOptionPane;

import src.model.InventoryItem;

public class DataValidatorForUpdates {

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
    public static boolean isUpdatedItemNumberValid(String itemType, int itemNumber) {
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
    public static boolean isUpdatedItemNumberDuplicate(InventoryItem selectedItem, int newUpdatedItemNumber) {
        // Check if the new item number created is the same as the current one
        if (newUpdatedItemNumber == selectedItem.getItemNumber()) {
            JOptionPane.showMessageDialog(null,
                    "New item number matches the current item number.",
                    "No Change Detected",
                    JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit method
        } else {
            return false; // Continue method
        }
    }

    /**
     * Checks if the new updated item number already exists in the inventory items
     * 
     * @param inventoryItemsList   The list of inventory items
     * @param newUpdatedItemNumber The new updated item number
     */
    public static boolean doesUpdatedItemNumberExist(List<InventoryItem> inventoryItemsList, int newUpdatedItemNumber) {
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

    /**
     * Checks if the new updated item type is the same as the current item type
     * 
     * @param selectedItem The selected item to be updated
     * @param newType      The new updated item type
     */
    public static boolean isSameItemType(InventoryItem selectedItem, String newType) {
        if (newType.equals(selectedItem.getItemType())) {
            JOptionPane.showMessageDialog(null,
                    "New item type matches the current item type.",
                    "No Change Detected",
                    JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit method
        } else {
            return false; // Continue method
        }
    }

    /**
     * Checks if the new updated item name is empty
     * 
     * @param newUpdatedItemName The new updated item name
     * @return True if the new updated item name is empty, otherwise false.
     */
    public static boolean isUpdatedItemNameEmpty(String newUpdatedItemName) {
        if (newUpdatedItemName.isEmpty()) {
            // Show an error message if the new item name is empty
            JOptionPane.showMessageDialog(null,
                    "Item Name cannot be empty!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return true; // Exit method
        } else {
            return false; // Continue method
        }
    }

    /**
     * Checks if the new updated item name is the same as the current item name
     * 
     * @param selectedItem       The selected item to be updated
     * @param newUpdatedItemName The new updated item name
     * @return True if the new updated item name is the same as the current one,
     *         otherwise false.
     */
    public static boolean isSameItemName(InventoryItem selectedItem, String newUpdatedItemName) {
        if (newUpdatedItemName.equals(selectedItem.getItemName())) {
            JOptionPane.showMessageDialog(null,
                    "New item name matches the current item name.",
                    "No Change Detected",
                    JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit method
        } else {
            return false; // Continue method
        }
    }

    /**
     * Checks if the new updated quantity is the same as the current quantity
     * 
     * @param selectedItem The selected item to be updated
     * @param newQuantity  The new updated quantity
     * @return True if the new updated quantity is the same as the current one,
     *         otherwise false.
     */
    public static boolean isSameQuantity(InventoryItem selectedItem, int newQuantity) {
        if (newQuantity == selectedItem.getQuantity()) {
            JOptionPane.showMessageDialog(null,
                    "New quantity matches the current quantity.",
                    "No Change Detected",
                    JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit method
        } else {
            return false; // Continue method
        }
    }

}
