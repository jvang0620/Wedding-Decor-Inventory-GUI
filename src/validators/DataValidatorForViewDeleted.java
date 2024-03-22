package src.validators;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import src.model.InventoryItem;

public class DataValidatorForViewDeleted {

    /**
     * Method to validate if the list of deleted items is empty
     * 
     * @param deletedItems      - the list of deleted items
     * @param inventoryTextArea - the text area to display the message
     * @return - true if the list of deleted items is empty, false if not
     */
    public static boolean isDeletedItemsListEmpty(List<InventoryItem> deletedItems, JTextArea inventoryTextArea) {

        // check if list is empthy, if so, display messages and exit method
        if (deletedItems.isEmpty()) {

            // Set text area to display message
            inventoryTextArea
                    .setText(" No items founds in deleted section. :() \n\n Please delete items to see them here.");

            // Display dialog message
            JOptionPane.showMessageDialog(
                    null,
                    "No deleted items found.",
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            return true; // Exit Method
        } else {
            return false; // Continue method
        }
    }
}
