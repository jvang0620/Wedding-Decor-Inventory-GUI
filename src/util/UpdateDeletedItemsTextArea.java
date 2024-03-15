package src.util;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JTextArea;

import src.model.InventoryItem;

public class UpdateDeletedItemsTextArea {

    /**
     * Updates the text area with deleted items.
     * 
     * Displays deleted items grouped by type and sorted alphabetically.
     * Only displays the first 35 characters of the item name, followed by "..." if
     * the name is longer.
     * Formats the creation date to display in the format "MM/dd/yy".
     * Sets the text of the deleted items text area to the generated report.
     * 
     * @param deletedItems The list of InventoryItem objects representing deleted
     *                     items.
     */
    public static void populateTextArea(List<InventoryItem> deletedItems,
            JTextArea deletedInventoryTextArea) {
        // Initialize StringBuilder to store the deleted items text
        StringBuilder sb = new StringBuilder();

        // Append title and separator
        sb.append("  Deleted Items Report\n");
        sb.append(" --------------------------------\n\n");

        // Group deleted items by type and sort alphabetically
        Map<String, List<InventoryItem>> deletedItemsByType = new TreeMap<>();
        for (InventoryItem item : deletedItems) {
            String itemType = item.getItemType();
            if (!deletedItemsByType.containsKey(itemType)) {
                deletedItemsByType.put(itemType, new ArrayList<>());
            }
            deletedItemsByType.get(itemType).add(item);
        }

        // Display deleted items by type
        for (Map.Entry<String, List<InventoryItem>> entry : deletedItemsByType.entrySet()) {
            String type = entry.getKey();
            List<InventoryItem> itemsOfType = entry.getValue();

            sb.append(" Type: ").append(type).append("\n");
            sb.append(String.format("%-7s %-15s %-55s %-20s %-25s%n", " ", "Item #", "Item Name", "Quantity",
                    "Date"));

            // Iterate over the deleted items of a specific type and display relevant
            // information for each item
            for (int i = 0; i < itemsOfType.size(); i++) {
                InventoryItem item = itemsOfType.get(i);

                // Format the creation date to display in the format "MM/dd/yy"
                String formattedDate = item.getCreationDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"));

                // Display only the first 25 characters of the item name, followed by "..." if
                // the name is longer
                String itemName = item.getItemName().length() > 35 ? item.getItemName().substring(0, 32) + "..."
                        : item.getItemName();

                // Append the formatted item information to the StringBuilder
                sb.append(String.format(" %-5d %-16d %-45s %-25d %-25s%n", i + 1, item.getItemNumber(),
                        itemName, item.getQuantity(), formattedDate));
            }
            sb.append("\n"); // Add a newline after displaying all deleted items of the type
        }

        // Set the text of the deleted items text area
        deletedInventoryTextArea.setText(sb.toString());
    }
}
