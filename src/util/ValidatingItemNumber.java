package src.util;

public class ValidatingItemNumber {

    /**
     * Validdate item number range for the given item type.
     *
     * @param itemType The type of the inventory item (Vases, Table Runners,
     *                 Greeneries).
     * @return The valid item number range as a string.
     */
    public static String validateItemNumberRange(String itemType) {
        switch (itemType) {
            case "Vases":
                return "100 - 199";
            case "Table Runners":
                return "200 - 299";
            case "Greeneries":
                return "300 - 399";
            default:
                return "Unknown"; // Return "Unknown" for unrecognized item types
        }
    }

    /**
     * Checks if the provided item number is valid for the given item type.
     * Valid item numbers are within specific ranges based on the item type:
     * - Vases: 100 - 199
     * - Table Runners: 200 - 299
     * - Greeneries: 300 - 399
     *
     * @param itemType   The type of the inventory item (Vases, Table Runners,
     *                   Greeneries).
     * @param itemNumber The item number to be validated.
     * @return True if the item number is valid for the given item type, false
     *         otherwise.
     */
    public static boolean isValidItemNumber(String itemType, int itemNumber) {
        switch (itemType) {
            case "Vases":
                return itemNumber >= 100 && itemNumber < 200;
            case "Table Runners":
                return itemNumber >= 200 && itemNumber < 300;
            case "Greeneries":
                return itemNumber >= 300 && itemNumber < 400;
            default:
                return false; // Handle unknown item types gracefully
        }
    }
}
