import java.util.ArrayList;

public class Player {

  private ArrayList<Item> inventory;

  /**
   * Instantiates inventory ArrayList
   */
  public Player() {
    inventory = new ArrayList<Item>();
  }

  /**
   * Adds object to inventory and checks if it exceeds weight
   * If so, it does not add item
   *
   * @param object Item object to add to inventory
   */
  public void addInv(Item object) {
    if (object != null) {
      if (!(object.getWeight() + totalWeight() > 50)) {
        inventory.add(object);
        System.out.println("Picked up item!");
      } else {
        System.out
            .println("You must drop something to make some room. This object you are trying to pick up has a weight of "
                + object.getWeight() + ".");
      }
    } else {
      System.out.println("No item with that name can be grabbed here.");
    }
  }

  /**
   * Removes item from inventory of the same name as parameter
   *
   * @param itemName name of item to remove from inventory
   */
  public Item removeInv(String itemName) {
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i).getReadName().equals(itemName)) {
        System.out.println("Item dropped!");
        return inventory.remove(i);
      }
    }
    System.out.println("Item not found in inventory.");
    return null;
  }

  /**
   * Checks if inventory has the item of the same name in parameter
   *
   * @param itemName name of item to check
   */
  public boolean hasItem(String itemName) {
    for (int i = 0; i < inventory.size(); i++) {
      if (inventory.get(i).getName().equals(itemName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Prints out inventory items names and their description
   */
  public void getInv() {
    for (Item i : inventory) {
      System.out.println(i.getName() + ": " + i.getDesc());
    }
  }

  /**
   * @return sum of all the weight of the items in inventory
   */
  public int totalWeight() {
    int sum = 0;
    for (Item i : inventory) {
      sum += i.getWeight();
    }
    return sum;
  }

}