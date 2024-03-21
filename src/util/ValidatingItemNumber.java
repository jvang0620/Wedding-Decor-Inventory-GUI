package src.util;

public class ValidatingItemNumber {

    /**
     * Validate item number range for the given item type.
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
}
