package src.util;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import src.model.InventoryItem;

public class GenerateReports {
    /**
     * Method to generate the content for the inventory report
     * 
     * This method constructs a textual representation of the inventory data
     * suitable for a report. It groups inventory items by type, sorts them
     * alphabetically within each type, and formats them into a string. The
     * resulting report content includes columns for item number, item name,
     * quantity, and creation date, and it is returned as a string.
     * 
     * @return Return the generated report content as a string
     */
    public static String generateReportSortedAlphabetically(List<InventoryItem> inventoryItemsList) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Inventory Report\n");
        reportContent.append("----------------\n\n");

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
            reportContent.append("Type: ").append(type).append("\n");
            reportContent.append(String.format("%-5s %-20s %-65s %-15s %-25s%n", "#", "Item Number", "Item Name",
                    "Quantity", "Creation Date"));

            // Retrieve the list of inventory items associated with a specific type,
            // convert it into a stream, and then sort the items based on their quantity
            // in descending order. Finally, collect the sorted items into a new list.
            List<InventoryItem> itemsOfType = entry.getValue().stream()
                    .sorted(Comparator.comparing(InventoryItem::getItemName))
                    .collect(Collectors.toList());

            for (int i = 0; i < itemsOfType.size(); i++) {
                InventoryItem item = itemsOfType.get(i);
                String formattedDate = item.getCreationDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
                reportContent.append(String.format("%-5d %-20d %-65s %-15d %-25s%n", i + 1, item.getItemNumber(),
                        item.getItemName(), item.getQuantity(), formattedDate));
            }
            reportContent.append("\n");
        }

        return reportContent.toString();
    }

    /**
     * Method to generate the content for the inventory report sorted by
     * item number.
     * 
     * This method constructs a textual representation of the inventory
     * data sorted by item number, suitable for a report. It groups inventory items
     * by type, sorts them within each type by item number, and formats them into a
     * string. The resulting report content includes columns for item number, item
     * name, quantity, and creation date, and it is returned as a string.
     * 
     * @return Return the generated report content as a string
     */
    public static String generateReportContentSortedByItemNumber(List<InventoryItem> inventoryItemsList) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Inventory Report (Sorted by Item Number)\n");
        reportContent.append("---------------------------------------\n\n");

        // Group inventory items by type
        Map<String, List<InventoryItem>> inventoryByType = new TreeMap<>();
        for (InventoryItem item : inventoryItemsList) {
            String itemType = item.getItemType();
            if (!inventoryByType.containsKey(itemType)) {
                inventoryByType.put(itemType, new ArrayList<>());
            }
            inventoryByType.get(itemType).add(item);
        }

        // Sort items within each type by item number
        for (Map.Entry<String, List<InventoryItem>> entry : inventoryByType.entrySet()) {
            String type = entry.getKey();
            List<InventoryItem> itemsOfType = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(InventoryItem::getItemNumber))
                    .collect(Collectors.toList());

            reportContent.append("Type: ").append(type).append("\n");
            reportContent.append(String.format("%-5s %-20s %-65s %-15s %-25s%n", "#", "Item Number", "Item Name",
                    "Quantity", "Creation Date"));

            int count = 1;
            for (InventoryItem item : itemsOfType) {
                String formattedDate = item.getCreationDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
                reportContent.append(String.format("%-5d %-20d %-65s %-15d %-25s%n", count++, item.getItemNumber(),
                        item.getItemName(), item.getQuantity(), formattedDate));
            }
            reportContent.append("\n");
        }

        return reportContent.toString();
    }

    /**
     * Method to generate the content for the inventory report sorted by
     * quantity.
     * 
     * This method constructs a textual representation of the inventory
     * data sorted by quantity in descending order, suitable for a report. It groups
     * inventory items by type, sorts them within each type by quantity in
     * descending order, and formats them into a string.The resulting report content
     * includes columns for item number, item name, quantity, andcreation date, and
     * it is returned as a string.
     * 
     * @return Return the generated report content as a string
     */
    public static String generateReportContentSortedByQuantity(List<InventoryItem> inventoryItemsList) {
        StringBuilder reportContent = new StringBuilder();
        reportContent.append("Inventory Report (Sorted by Quantity)\n");
        reportContent.append("---------------------------------------\n\n");

        // Group inventory items by type
        Map<String, List<InventoryItem>> inventoryByType = new TreeMap<>();
        for (InventoryItem item : inventoryItemsList) {
            String itemType = item.getItemType();
            if (!inventoryByType.containsKey(itemType)) {
                inventoryByType.put(itemType, new ArrayList<>());
            }
            inventoryByType.get(itemType).add(item);
        }

        // Sort items within each type by quantity in descending order
        for (Map.Entry<String, List<InventoryItem>> entry : inventoryByType.entrySet()) {
            String type = entry.getKey();

            // Retrieve the list of inventory items associated with a specific type,
            // convert it into a stream, and then sort the items based on their quantity
            // in descending order. Finally, collect the sorted items into a new list.
            List<InventoryItem> itemsOfType = entry.getValue().stream()
                    .sorted(Comparator.comparingInt(InventoryItem::getQuantity).reversed())
                    .collect(Collectors.toList());

            reportContent.append("Type: ").append(type).append("\n");
            reportContent.append(String.format("%-5s %-20s %-65s %-15s %-25s%n", "#", "Item Number", "Item Name",
                    "Quantity", "Creation Date"));

            int count = 1;
            for (InventoryItem item : itemsOfType) {
                String formattedDate = item.getCreationDate().format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
                reportContent.append(String.format("%-5d %-20d %-65s %-15d %-25s%n", count++, item.getItemNumber(),
                        item.getItemName(), item.getQuantity(), formattedDate));
            }
            reportContent.append("\n");
        }

        return reportContent.toString();
    }
}
