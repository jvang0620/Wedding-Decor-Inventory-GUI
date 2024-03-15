import javax.swing.SwingUtilities;

import src.gui_inventory_management.InventoryManagementSystemFrame;

public class WeddingInventoryApp {
    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new InventoryManagementSystemFrame();
            }
        });
    }
}
