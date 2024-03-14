package src.util;

import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import src.CSVReaderWriter.CSVHandler;
import src.model.InventoryItem;

public class PromptForUpdateConfirmation {

    /**
     * Displays a confirmation dialog for updating an inventory item.
     * If the user confirms, the selected option of the item will be updated with
     * the new value.
     * After the update, it refreshes the inventory display and saves the updated
     * data to the CSV file.
     *
     * @param selectedItem       The InventoryItem object to be updated.
     * @param selectedOption     The option selected for updating (Item Number, Item
     *                           Type, Item Name, Quantity).
     * @param newValue           The new value for the selected option.
     * @param inventoryItemsList The inventory item list
     * @param inventoryTextArea  Inventory Text Area
     */

    public static void showUpdateConfirmationDialog(InventoryItem selectedItem, String selectedOption, Object newValue,
            List<InventoryItem> inventoryItemsList, JTextArea inventoryTextArea) {
        // Construct message for confirmation dialog
        String message = String.format(
                "Current Item Number: %d\nCurrent Item Name: %s\nCurrent Quantity: %d\nCurrent Item Type: %s\n\n"
                        + "New %s: %s\n\nAre you sure you want to update this inventory item?",
                selectedItem.getItemNumber(), selectedItem.getItemName(), selectedItem.getQuantity(),
                selectedItem.getItemType(),
                selectedOption,
                newValue);

        // Display confirmation dialog
        int confirmation = JOptionPane.showConfirmDialog(null, message, "Confirm Update",
                JOptionPane.YES_NO_OPTION);

        // If user confirms update, proceed with the update
        if (confirmation == JOptionPane.YES_OPTION) {
            // Update the selected option of the inventory item based on the provided new
            // value
            switch (selectedOption) {
                case "Item Number":
                    selectedItem.setItemNumber(Integer.parseInt(newValue.toString()));
                    break;
                case "Item Type":
                    selectedItem.setItemType(newValue.toString());
                    break;
                case "Item Name":
                    selectedItem.setItemName(newValue.toString());
                    break;
                case "Quantity":
                    selectedItem.setQuantity(Integer.parseInt(newValue.toString()));
                    break;
            }
            // Update inventory display and write to inventory.CSV
            UpdateInventoryTextArea.reloadTextArea(inventoryItemsList, inventoryTextArea);

            // Write new item created to Inventory csv
            CSVHandler.writeCreatedItemToInventoryCSV(inventoryItemsList);

            // Display successful updated message
            JOptionPane.showMessageDialog(null, "Item Updated Successfully!",
                    "Updated Successful", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
