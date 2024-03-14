package src.model;

import java.time.LocalDate;

public class InventoryItem {

    // Attributes of an inventory item
    private String itemName; // Name of the item
    private int quantity; // Quantity of the item
    private int itemNumber; // Unique identification number of the item
    private String itemType; // Type or category of the item
    private LocalDate creationDate; // Date when the item was created

    // Default constructor
    public InventoryItem() {
    };

    // Constructor to initialize an InventoryItem object
    public InventoryItem(String itemName, int quantity, int itemNumber, String itemType, LocalDate creationDate) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemNumber = itemNumber;
        this.itemType = itemType;
        this.creationDate = creationDate;
    }

    // Getter method for retrieving the item name
    public String getItemName() {
        return itemName;
    }

    // Setter method for updating the item name
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    // Getter method for retrieving the item quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter method for updating the item quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter method for retrieving the item number
    public int getItemNumber() {
        return itemNumber;
    }

    // Setter method for updating the item number
    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }

    // Getter method for retrieving the item type
    public String getItemType() {
        return itemType;
    }

    // Setter method for updating the item type
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    // Getter method for retrieving the creation date of the item
    public LocalDate getCreationDate() {
        return creationDate;
    }

    // Setter method for updating the creation date of the item
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

}
