package src.validators;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import src.model.InventoryItem;

public class DataValidatorForViewInventory {

    /**
     * Method to validate if the list of inventory items is empty
     * 
     * @param inventoryItemsList - the list of inventory items
     * @param inventoryTextArea  - the text area to display the message
     * @return - true if the list of inventory items is empty, false if not
     */
    public static boolean isInventoryItemsListEmpty(List<InventoryItem> inventoryItemsList,
            JTextArea inventoryTextArea) {

        // check if list is empty, if so, display message and exit method
        if (inventoryItemsList.isEmpty()) {

            // Set text area to display message
            inventoryTextArea
                    .setText(" No items founds in the inventory. :() \n\n Please add items to the inventory.");

            // Display dialog message
            JOptionPane.showMessageDialog(
                    null,
                    "There are no items in the inventory to display.",
                    "No Items", JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit Method
        } else {
            return false; // Continue method
        }
    }
}
