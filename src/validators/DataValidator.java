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
}
