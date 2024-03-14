package src.CSVReaderWriter;

import src.model.InventoryItem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class CSVHandler {

    // Absolute Path to the CSV file for data storage
    private static final String INVENTORY_CSV_FILE_PATH = "C:/ITCS-3112-Design-Implement-Object-Orient-System/++Wedding-Inventory-(Java-Swing)/src/data/inventory.csv";

    // Absolute Path to the Delete Item CSV file for data storage
    private static final String DELETED_ITEMS_CSV_FILE_PATH = "C:/ITCS-3112-Design-Implement-Object-Orient-System/++Wedding-Inventory-(Java-Swing)/src/data/deleted_items.csv";

    /**
     * Method to load inventory data from inventory.csv during startup
     * 
     * This method reads data from a CSV file located at the path specified by
     * CSV_FILE_PATH. If the file exists, it iterates through each line, parses it
     * into individual data parts, and creates InventoryItem objects from them.
     * These objects are then added to the inventoryItems list. Each line of the CSV
     * file represents an InventoryItem, with its attributes separated by commas. If
     * a line does not contain exactly five parts, it is skipped. If an IOException
     * occurs during file reading, the exception is printed to the standard error
     * stream.
     * 
     */
    public static void loadExisitingDataFromInventoryCSVDuringStartUp(List<InventoryItem> inventoryItemsList) {
        File file = new File(INVENTORY_CSV_FILE_PATH); // Create a File object representing the CSV file
        if (file.exists()) { // Check if the file exists
            try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_CSV_FILE_PATH))) {

                // Create a BufferedReader to read from the file
                String line;

                // Read each line from the file
                while ((line = reader.readLine()) != null) {
                    // Split the line into parts using comma as delimiter
                    String[] parts = line.split(",");

                    // Check if the line contains all five parts
                    if (parts.length == 5) {
                        // Extract data parts from the line
                        String itemName = parts[0];
                        int quantity = Integer.parseInt(parts[1]);
                        int itemNumber = Integer.parseInt(parts[2]);
                        String itemType = parts[3];
                        LocalDate creationDate = LocalDate.parse(parts[4]);

                        // Create a new InventoryItem object from the extracted data
                        InventoryItem item = new InventoryItem(itemName, quantity, itemNumber, itemType, creationDate);

                        // Add the InventoryItem object to the list of inventory items
                        inventoryItemsList.add(item);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace(); // Print the stack trace of the IOException
            }
        }
    }

    /**
     * Method to save inventory data to a CSV file
     * 
     * This method writes the inventory data to a CSV file located at the path
     * specified by CSV_FILE_PATH. It iterates through each InventoryItem in the
     * inventoryItems list and writesits attributes to a new line in the CSV file.
     * The attributes are separated by commas. Each line in the CSV file represents
     * an InventoryItem, with its attributes ordered as follows: Item Name,
     * Quantity, Item Number, Item Type, Creation Date. If an IOException occurs
     * during file writing, the exception is printed to the standard error stream.
     */
    public static void writeCreatedItemToInventoryCSV(List<InventoryItem> inventoryItemsList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_CSV_FILE_PATH))) {
            // Create a PrintWriter to write to the CSV file
            for (InventoryItem item : inventoryItemsList) {
                // Iterate through each InventoryItem in the inventoryItems list
                // Write the attributes of the InventoryItem to a new line in the CSV file
                writer.println(item.getItemName() + "," + item.getQuantity() + "," + item.getItemNumber() + ","
                        + item.getItemType() + "," + item.getCreationDate());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace of the IOException
        }
    }

    /**
     * Method to append the details of a restored inventory item to a CSV file.
     * 
     * This method takes an InventoryItem object representing the restored item and
     * appends its details, including item name, quantity, item number, item type,
     * and creation date, to the CSV file specified by INVENTORY_CSV_FILE_PATH.
     * If an IOException occurs during file writing, the stack trace is printed,
     * and an error message is displayed using a JOptionPane.
     * 
     * @param item The InventoryItem object representing the restored item.
     */
    public static void writeRestoredItemToCSV(InventoryItem item) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INVENTORY_CSV_FILE_PATH, true))) {
            // Append the item details to the CSV file
            writer.println(item.getItemName() + "," + item.getQuantity() + "," + item.getItemNumber() + ","
                    + item.getItemType() + "," + item.getCreationDate());
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace of the IOException
            JOptionPane.showMessageDialog(null, "Error occurred while writing the restored item to inventory.csv",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Method to write updated deleted items to a CSV file.
     * 
     * This method takes a list of InventoryItem objects representing the updated
     * list of deleted items. It opens a PrintWriter to the CSV file specified by
     * DELETED_ITEMS_CSV_PATH and writes each item's details to a new line in the
     * file. The details include item name, quantity, item number, item type, and
     * creation date, separated by commas. If an IOException occurs during file
     * writing, it is caught, the stack trace is printed, and an error message is
     * displayed using a JOptionPane.
     * 
     * @param items The list of InventoryItem objects representing the updated
     *              list of deleted items.
     */
    public static void writeItemsToDeletedItemsCSV(List<InventoryItem> items) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DELETED_ITEMS_CSV_FILE_PATH))) {
            // Write each item to the CSV file
            for (InventoryItem item : items) {
                writer.println(item.getItemName() + "," + item.getQuantity() + "," + item.getItemNumber() + ","
                        + item.getItemType() + "," + item.getCreationDate());
            }
        } catch (IOException ex) {
            ex.printStackTrace(); // Handle the IOException appropriately
            JOptionPane.showMessageDialog(null, "Error writing to deleted items CSV file!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Writes deleted item details to the CSV file.
     * 
     * It takes an InventoryItem object representing the deleted item as input.
     * Inside a try-with-resources block, it opens a PrintWriter to append data to
     * the specified CSV file (DELETED_ITEMS_CSV_FILE_PATH).
     * It then appends the details of the deleted item to the CSV file. These
     * details include the item name, quantity, item number, item type, and creation
     * date, all separated by commas.
     * If an IOException occurs during file writing, it catches the exception,
     * prints the stack trace, and displays an error message using a JOptionPane.
     * 
     * @param item The InventoryItem object representing the deleted item.
     */
    public static void writeDeletedItemToCSV(InventoryItem item) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DELETED_ITEMS_CSV_FILE_PATH, true))) {
            // Append the item details to the CSV file, including the creation date
            writer.println(item.getItemName() + "," + item.getQuantity() + "," + item.getItemNumber() + ","
                    + item.getItemType() + "," + item.getCreationDate());
        } catch (IOException ex) {
            ex.printStackTrace(); // Print the stack trace of the IOException
            JOptionPane.showMessageDialog(null, "Error writing to CSV file for deleted items!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Reads from Deleted CSV file and returns a list of InventoryItem
     * objects.
     * 
     * @return A list of InventoryItem objects read from the CSV file.
     */
    public static List<InventoryItem> readItemsFromDeletedCSVFile() {
        List<InventoryItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DELETED_ITEMS_CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line by comma and create an InventoryItem object
                String[] parts = line.split(",");

                // Check if the CSV line contains all required fields (5 fields):
                // If so, extract each field:
                if (parts.length == 5) {
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    int itemNumber = Integer.parseInt(parts[2]);
                    String itemType = parts[3];
                    LocalDate creationDate = LocalDate.parse(parts[4]);

                    // Create a new InventoryItem object using the extracted information and add it
                    // to the list of items.
                    InventoryItem item = new InventoryItem(itemName, quantity, itemNumber, itemType, creationDate);
                    items.add(item);
                }
            }
        } catch (IOException | DateTimeParseException | NumberFormatException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return items;
    }

    /**
     * Reads from Inventory CSV file and returns a list of InventoryItem
     * objects.
     * 
     * @return A list of InventoryItem objects read from the CSV file.
     */
    public static List<InventoryItem> readItemsFromInventoryCSVFile() {
        List<InventoryItem> items = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split the CSV line by comma and create an InventoryItem object
                String[] parts = line.split(",");

                // Check if the CSV line contains all required fields (5 fields):
                // If so, extract each field:
                if (parts.length == 5) {
                    String itemName = parts[0];
                    int quantity = Integer.parseInt(parts[1]);
                    int itemNumber = Integer.parseInt(parts[2]);
                    String itemType = parts[3];
                    LocalDate creationDate = LocalDate.parse(parts[4]);

                    // Create a new InventoryItem object using the extracted information and add it
                    // to the list of items.
                    InventoryItem item = new InventoryItem(itemName, quantity, itemNumber, itemType, creationDate);
                    items.add(item);
                }
            }
        } catch (IOException | DateTimeParseException | NumberFormatException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
        return items;
    }

}