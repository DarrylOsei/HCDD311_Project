import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Inventory {
	
	/*** Inventory class represents the Player's inventory or "backpack." 
     *   Which would start with zero items or something small from the game to at least 
	 * 		initialize the object in the game. 
	 *  **************************
	 *  
	 *  What are the necessary column headings in the item_data_table??
	 * 	-- Category, Name, Description, Quantity, Value
	 * 	-- Is it left up to the columns read from the database??
	 */	
	    
    private String[] columnNames;
    public LinkedList<InventoryItem> backpack; // This replaces itemData
    
    public static Player ply; // since inventory is now the hard code and Item/Inventory GUI is the front end
	 // likely will need a reference to player.

    public Inventory() { //Constructor method
        columnNames = new String[] {"ItemID", "Category", "Name", "Description", "Quantity", "Value", "Image Path"};
        backpack = new LinkedList<InventoryItem>();
    }

    public String[] getColumnNames() {
	    return this.columnNames;
	}
			
	// =================================================================
    // Populate the ITEM inventory (backpack)
    // =================================================================
    public LinkedList<InventoryItem> createItemInventory() {
        // We simply add new Item objects to the list
        backpack.add(new InventoryItem("001", "Health", "sHP", "Small health pack", 1, 10, getItemImage(0)));
        backpack.add(new InventoryItem("102", "Ammo", "rubberChicken", "A rubber chicken", 1, 1, getItemImage(4)));
        backpack.add(new InventoryItem("301", "Armor", "Jacket", "Comfy warm jacket", 1, 25, getItemImage(7)));
        backpack.add(new InventoryItem("002", "Health", "mHP", "Medium health pack", 1, 25, getItemImage(1)));
        backpack.add(new InventoryItem("103", "Ammo", "pistolRounds", "Ammunition for a pistol", 1, 6, getItemImage(3)));
        backpack.add(new InventoryItem("302", "Armor", "bpVest", "Bullet proof vest", 1, 50, getItemImage(8)));
        backpack.add(new InventoryItem("003", "Health", "mHP", "Large health pack", 1, 50, getItemImage(2)));
        backpack.add(new InventoryItem("103", "Ammo", "grenade", "Grenade that goes boom!", 1, 1, getItemImage(5)));
        backpack.add(new InventoryItem("303", "Armor", "zCloak", "Zombie Cloak", 1, 75, getItemImage(9)));
        
        return backpack;
    }
	
	// Example use of an ArrayList<> 
	public String getItemImage(int position) {
		
		ArrayList<String> itemImage = new ArrayList<String>();
		itemImage.add("./Zombie_Game/images/sHP.png");
		itemImage.add("./Zombie_Game/images/mHP.png");
		itemImage.add("./Zombie_Game/images/xHP.png");
		itemImage.add("./Zombie_Game/images/pistolRounds.jpeg");
		itemImage.add("./Zombie_Game/images/rubberChicken.jpg");
		itemImage.add("./Zombie_Game/images/grenade");
		itemImage.add("./Zombie_Game/images/1up.jpg");
		itemImage.add("./Zombie_Game/images/jacket.png");
		itemImage.add("./Zombie_Game/images/bpvest.png");
		itemImage.add("./Zombie_Game/images/cloak1.png");
				
		return itemImage.get(position);
	}
	
	/** Getter Method
	 * Converts the LinkedList backpack into a 2D Object array 
	 * for use in JTables or other array-based UI components.
	 */
	public Object[][] getInventoryArray() {
	    Object[][] data = new Object[backpack.size()][7];
	    
	    for (int i = 0; i < backpack.size(); i++) {
	        InventoryItem item = backpack.get(i);
	        data[i][0] = item.id;
	        data[i][1] = item.category;
	        data[i][2] = item.name;
	        data[i][3] = item.description;
	        data[i][4] = item.quantity;
	        data[i][5] = item.value;
	        data[i][6] = item.imagePath;
	    }
	    
	    return data;
	}
		
    /** 
     * SEARCH: Updated to iterate through the LinkedList 
     */
    public void searchByItemCategory(String category, JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); 

        for (InventoryItem item : backpack) {
            if (item.category.equalsIgnoreCase(category)) {
                // Convert our Item object back to a row for the JTable
                model.addRow(new Object[]{
                    item.id, item.category, item.name, item.description, 
                    item.quantity, item.value, item.imagePath
                });
            }
        }
        
        // Update the UI image if results were found
        if (model.getRowCount() > 0) {
            updateUIPreview(model.getValueAt(0, 6).toString());
        } else {
            InventoryUI.lblItemImage.setIcon(null);
        }
    }

    private void updateUIPreview(String path) {
        ImageIcon icon = new ImageIcon(new ImageIcon(path)
            .getImage()
            .getScaledInstance(200, 200, Image.SCALE_SMOOTH));
        InventoryUI.lblItemImage.setIcon(icon);
    }

    /** 
     * PUSH: Using LinkedList logic (No more manual array copying!)
     */
    public void pushItem(InventoryItem newItem) {
        backpack.addLast(newItem);
        System.out.println(newItem.name + " added. Inventory size: " + backpack.size());
    }
    
    /** 
     * POP: Removes and returns the last item
     */
    public InventoryItem popItem() {
        if (backpack.isEmpty()) {
            System.out.println("Inventory is empty!");
            return null;
        }
        return backpack.removeLast();
    }
	
} //end of Inventory class

/** For the Future **/
/** ******************
 * A Better Way is to use an ArrayList, 
 * if the plan is adding and removing items frequently, 
 * using a raw Object[][] array is inefficient because 
 * we have to rebuild the array every time. 
 * An ArrayList<Object[]>, can handle the resizing.
 */

//import java.util.ArrayList;
//import java.util.Arrays;

// Convert current Object array to a List
//ArrayList<Object[]> itemList = new ArrayList<>(Arrays.asList(level1Items));

// To "Pop" (Remove last item)
//Object[] removed = itemList.remove(itemList.size() - 1);

/***
private final String[] ITEM_IMAGES = {
	    ".\\src\\images\\sHP.png", ".\\src\\images\\mHP.png", 
	    ".\\src\\images\\xHP.png", ".\\src\\images\\pistolRounds.jpeg",
	    ".\\src\\images\\rubberChicken.jpg", ".\\src\\images\\grenade.png",
	    ".\\src\\images\\1up.jpg", ".\\src\\images\\jacket.png",
	    ".\\src\\images\\bpvest.png", ".\\src\\images\\cloak1.png"
	};

	public String getItemImage(int position) {
	    if (position >= 0 && position < ITEM_IMAGES.length) {
	        return ITEM_IMAGES[position];
	    }
	    return ""; // Return empty or a "placeholder.png"
	}
*/
    



