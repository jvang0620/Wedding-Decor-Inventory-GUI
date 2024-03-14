package src.util;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import src.model.InventoryItem;

public class RandomNumberGenerator {

    /**
     * Generates a random item number based on the provided item type.
     * This method initializes a Random object to generate random numbers.
     * The base number for each item type is predefined: Vases (100), Table Runners
     * (200), Greeneries (300).
     * If the provided item type is not recognized, a base number of 0 is used.
     * The method returns a randomly generated item number, which is the sum of the
     * base number and a random integer between 0 and 99 (inclusive).
     *
     * @param itemType The type of the item for which a random number is generated.
     * @return The randomly generated item number.
     */
    public static int generateRandomNumber(String itemType, List<InventoryItem> inventoryItemsList) {
        Random random = new Random(); // Initialize a Random object
        int baseNumber;
        // Determine the base number based on the item type
        switch (itemType) {
            case "Vases":
                baseNumber = 100;
                break;
            case "Table Runners":
                baseNumber = 200;
                break;
            case "Greeneries":
                baseNumber = 300;
                break;
            default:
                baseNumber = 0; // Handle unknown item types gracefully
        }
        // Collects existing item numbers of a specific item type from the inventory
        // items into a Set for efficient lookup.
        Set<Integer> existingItemNumbers = inventoryItemsList.stream()
                .filter(item -> item.getItemType().equals(itemType)) // Filter items of the specified type
                .map(InventoryItem::getItemNumber) // Extract item numbers
                .collect(Collectors.toSet()); // Collect them into a Set for efficient containment checks

        int randomNumber;
        do {
            // Generate a random number between 0 and 99 (inclusive) and add it to the base
            // number
            randomNumber = baseNumber + random.nextInt(100);
        } while (existingItemNumbers.contains(randomNumber)); // Repeat until a unique random number is generated

        return randomNumber;
    }
}
