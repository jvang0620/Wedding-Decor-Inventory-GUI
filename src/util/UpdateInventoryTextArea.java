package src.util;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import src.model.InventoryItem;

public class UpdateInventoryTextArea {

    /**
     * Updates the inventory text area with the current inventory items.
     * Displays inventory items grouped by type and sorted alphabetically.
     * Only displays the first 25 characters of the item name, followed by "..." if
     * the name is longer.
     */
    public static void reloadTextArea(List<InventoryItem> inventoryItemsList, JTextArea inventoryTextArea) {
        // Initialize StringBuilder to store the inventory text
        StringBuilder sb = new StringBuilder();

        // Check if the list is empty, display a message to the user and exit method
        if (inventoryItemsList.isEmpty()) {

            JOptionPane.showMessageDialog(null, "There are no items in the inventory to display.", "No Items",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Append title and separator
        sb.append(" Inventory Report\n");
        sb.append(" ---------------------\n\n");

        // Group inventory items by type and sort alphabetically
        Map<String, List<InventoryItem>> inventoryByType = new TreeMap<>();
        for (InventoryItem item : inventoryItemsList) {
            String itemType = item.getItemType();
            if (!inventoryByType.containsKey(itemType)) {
                inventoryByType.put(itemType, new ArrayList<>());
            }
            inventoryByType.get(itemType).add(item);
        }

        // Display inventory items by type
        for (Map.Entry<String, List<InventoryItem>> entry : inventoryByType.entrySet()) {
            String type = entry.getKey();
            sb.append(" Type: ").append(type).append("\n");
            sb.append(String.format("%-7s %-14s %-55s %-15s %-25s%n", " ", "Item #", "Item Name", "Qty", "Date"));

            // Sort the items of a specific type alphabetically based on their names
            List<InventoryItem> itemsOfType = entry.getValue().stream()
                    // Sort by item name
                    .sorted(Comparator.comparing(InventoryItem::getItemName))
                    // Collect the sorted items into a list
                    .collect(Collectors.toList());

            // Iterate over the items of a specific type and display relevant information
            // for each item
            for (int i = 0; i < itemsOfType.size(); i++) {
                InventoryItem item = itemsOfType.get(i);
                // Format the creation date to display in the format "M/d/yy"
                String formattedDate = item.getCreationDate().format(DateTimeFormatter.ofPattern("MM/dd/yy"));
                // Display only the first 25 characters of the item name, followed by "..." if
                // the name is longer
                String itemName = item.getItemName().length() > 35 ? item.getItemName().substring(0, 32) + "..."
                        : item.getItemName();
                // Append the formatted item information to the StringBuilder
                sb.append(String.format(" %-5d %-15d %-45s %-15d %-25s%n", i + 1, item.getItemNumber(), itemName,
                        item.getQuantity(), formattedDate));
            }
            sb.append("\n"); // Add a newline after displaying all items of the type
        }

        // Set the text of the inventory text area
        inventoryTextArea.setText(sb.toString());
    }

}
