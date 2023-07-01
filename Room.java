import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. For each existing exit, the room
 * stores a reference to the neighboring room.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room {
  private String name;
  private String description;
  private String lookDesc;
  private ArrayList<Item> tools;
  private HashMap<String, Room> exits; // stores exits of this room.

  /**
   * Create a room described "description". Initially, it has
   * no exits. "description" is something like "a kitchen" or
   * "an open court yard".
   * 
   * @param description The room's description.
   */
  public Room(String name, String description, String lookDesc) {
    this.name = name;
    this.description = description;
    this.lookDesc = lookDesc;
    tools = new ArrayList<>();
    exits = new HashMap<>();
  }

  /**
   * Create a room described "description". Initially, it has
   * no exits. "description" is something like "a kitchen" or
   * "an open court yard". Has another parameter for ArrayList
   * of Item objects.
   * 
   * @param tools ArrayList of Item objects
   * @param description The room's description.
   */
  public Room(String name, ArrayList<Item> tools, String description, String lookDesc) {
    this.name = name;
    this.tools = tools;
    this.description = description;
    this.lookDesc = lookDesc;
    exits = new HashMap<>();
  }

  /**
   * Define an exit from this room.
   * 
   * @param direction The direction of the exit.
   * @param neighbor  The room to which the exit leads.
   */
  public void setExit(String direction, Room neighbor) {
    exits.put(direction, neighbor);
  }

  /**
   * Removes exit from this room
   * @param direction The direction of the exit.
   * @param neighbor  The room to which the exit leads.
   */
  public void removeExit(String direction, Room neighbor) {
    exits.remove(direction, neighbor);
  }

  /**
   * @return The name of the room
   *         (the one that was defined in the constructor)
   */
  public String getName() {
    return name;
  }

  /**
   * Prints names of items that can be grabbed in this room
   */
  public void printItems() {
    if (tools.size() != 0) {
      System.out.print("Items to grab: " + tools.get(0).getName());
      for (int i = 1; i < tools.size(); i++) {
        System.out.print(", " + tools.get(i).getName());
      }
      System.out.println();
    } else {
      System.out.println("Items to grab: None");
    }
  }

  /**
   * Adds Item object to tools ArrayList
   *
   * @param object Item object to add to ArrayList
   */
  public void addItem(Item object) {
    if (object != null) {
      tools.add(object);
    }
  }

  /**
   * Removes item of the same name in tools ArrayList
   * and returns it
   * 
   * @param itemName name of the item to remove
   * @return object that was removed
   */
  public Item removeItem(String itemName) {
    for (int i = 0; i < tools.size(); i++) {
      if (tools.get(i).getReadName().equals(itemName)) {
        return tools.remove(i);
      }
    }
    return null;
  }

  /**
   * @return The short description of the room
   *         (the one that was defined in the constructor).
   */
  public String getShortDescription() {
    return description;
  }

  /**
   * Return a description of the room in the form:
   * You are in the kitchen.
   * Exits: north west
   * 
   * @return A long description of this room
   */
  public String getLongDescription() {
    return "You " + description + ".\n" + getExitString();
  }

  /**
   * Return a string describing the room's exits, for example
   * "Exits: north west".
   * 
   * @return Details of the room's exits.
   */
  public String getExitString() {
    String returnString = "Exits:";
    Set<String> keys = exits.keySet();
    for (String exit : keys) {
      returnString += " " + exit;
    }
    return returnString;
  }

  /**
   * Return the room that is reached if we go from this room in direction
   * "direction". If there is no room in that direction, return null.
   * 
   * @param direction The exit's direction.
   * @return The room in the given direction.
   */
  public Room getExit(String direction) {
    return exits.get(direction);
  }

  /**
   * @return The description when using method look
   */
  public String getLookDesc() {
    return lookDesc;
  }
}
